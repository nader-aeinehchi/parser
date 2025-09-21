package org.naderica.parser.sourcecode.java.standard

import java.util.{ArrayList, List, Stack}
import scala.jdk.CollectionConverters._

import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.ParserRuleContext
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.ast.java.standard.Java20ParserBaseVisitor

class SignatureVisitor(
    val tokens: CommonTokenStream,
    val jModifier: JModifier = JModifier.PUBLIC
) extends Java20ParserBaseVisitor[Unit] {

  private val classStack = new Stack[ClassSignature]()
  private val topLevelClasses = new ArrayList[ClassSignature]()
  private var currentPackageSignature: String = ""
  // private var currentPackageName: String = ""

  def classSignatures() = topLevelClasses

  override def visitPackageDeclaration(ctx: PackageDeclarationContext): Unit = {
    import scala.jdk.CollectionConverters._

    val modifier = extractJModifier(ctx.packageModifier())
    val packageName = ctx.identifier().asScala.map(_.getText).mkString(".")
    val packageSignature =
      if (modifier == JModifier.PACKAGE_PRIVATE)
        s"package $packageName;"
      else
        s"package ${modifier.toString.toLowerCase} $packageName;"

// Store packageSignature and packageName for later use
    this.currentPackageSignature = packageSignature
    // this.currentPackageName = packageName
    // println(packageSignature) // or store it as needed

    super.visitPackageDeclaration(ctx)
  }

  override def visitNormalClassDeclaration(
      ctx: NormalClassDeclarationContext
  ): Unit = {

    handleTopLevelConstruct(
      ctx,
      _.classBody(),
      extractJModifier(ctx.classModifier())
    )
    super.visitNormalClassDeclaration(ctx)
  }

  override def visitNormalInterfaceDeclaration(
      ctx: NormalInterfaceDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.interfaceBody(),
      extractJModifier(ctx.interfaceModifier())
    )
    super.visitNormalInterfaceDeclaration(ctx)
  }

  override def visitEnumDeclaration(
      ctx: EnumDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.enumBody(),
      extractJModifier(ctx.classModifier())
    )
    super.visitEnumDeclaration(ctx)
  }

  override def visitRecordDeclaration(
      ctx: RecordDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.recordBody(),
      extractJModifier(ctx.classModifier())
    )
    super.visitRecordDeclaration(ctx)
  }

  override def visitMethodDeclaration(ctx: MethodDeclarationContext): Unit = {
    val modifier = extractJModifier(ctx.methodModifier())
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      extractAndAddMethodSignature(ctx, _.methodBody())
    }
  }

// @Override public T visitInterfaceMethodDeclaration(Java20Parser.InterfaceMethodDeclarationContext ctx) { return visitChildren(ctx); }

  override def visitInterfaceMethodDeclaration(
      ctx: InterfaceMethodDeclarationContext
  ): Unit = {
    val modifier = extractJModifier(ctx.interfaceMethodModifier())
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      extractAndAddMethodSignature(ctx, _.methodBody())
    }
  }

  override def visitConstructorDeclaration(
      ctx: ConstructorDeclarationContext
  ): Unit = {
    val modifier = extractJModifier(ctx.constructorModifier())
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      extractAndAddMethodSignature(ctx, _.constructorBody())
    }
  }

  override def visitConstantDeclaration(
      ctx: ConstantDeclarationContext
  ): Unit = {
    extractAndAddFieldOrConstant(
      extractJModifier(ctx.constantModifier()),
      ctx.unannType().getText + " ",
      ctx.variableDeclaratorList()
    )
  }

  override def visitFieldDeclaration(
      ctx: FieldDeclarationContext
  ): Unit = {
    extractAndAddFieldOrConstant(
      extractJModifier(ctx.fieldModifier()),
      ctx.unannType().getText + " ",
      ctx.variableDeclaratorList()
    )
  }

  override def visitEnumConstant(ctx: EnumConstantContext): Unit = {
    val modifier = extractJModifier(ctx.enumConstantModifier())
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      val name = ctx.identifier().getText
      if (!classStack.isEmpty) {
        classStack.peek().methodSignatures.append(name)
      }
    }
  }

  /** Helper to handle top-level constructs (class, interface, enum, record) */
  private def handleTopLevelConstruct[C](
      ctx: C,
      getBody: C => org.antlr.v4.runtime.ParserRuleContext,
      modifier: JModifier
  ): Unit = {
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      val signature = extractSignature(ctx, getBody)
      val currentClass = ClassSignature()
      currentClass.signature = signature
      currentClass.packageSignature = currentPackageSignature
      // currentClass.packageName = currentPackageName

      if (classStack.isEmpty) {
        topLevelClasses.add(currentClass)
      } else {
        classStack.peek().innerClasses.append(currentClass)
      }
      classStack.push(currentClass)
    }
  }

  /** Helper to extract and add method signature */
  private def extractAndAddMethodSignature[C](
      ctx: C,
      getBody: C => org.antlr.v4.runtime.ParserRuleContext
  ): Unit = {
    val signature = extractSignature(ctx, getBody)
    if (!classStack.isEmpty) {
      classStack.peek().methodSignatures.append(signature)
    }
    ()
  }

  /** Helper to extract the signature text from a context up to the start of its
    * body.
    */
  private def extractSignature[C](
      ctx: C,
      getBody: C => org.antlr.v4.runtime.ParserRuleContext
  ): String = {
    tokens
      .getText(
        new Interval(
          ctx
            .asInstanceOf[org.antlr.v4.runtime.ParserRuleContext]
            .start
            .getTokenIndex,
          getBody(ctx).start.getTokenIndex - 1
        )
      )
      .trim
  }

  /** Extracts the access modifier from a list of modifier contexts. You may
    * need to adapt this to your grammar.
    */
  private def extractJModifier(
      modifiers: java.util.List[?]
  ): JModifier = {
    import scala.jdk.CollectionConverters._

    val mods = modifiers.asScala.map {
      case ctx: ParserRuleContext => ctx.getText
      case other                  => other.toString
    }

    if (mods.contains("private")) JModifier.PRIVATE
    else if (mods.contains("protected")) JModifier.PROTECTED
    else if (mods.contains("public")) JModifier.PUBLIC
    else JModifier.PACKAGE_PRIVATE
  }

// Helper for both constant and field declarations
  private def extractAndAddFieldOrConstant(
      modifier: JModifier,
      typeText: String,
      varList: VariableDeclaratorListContext
  ): Unit = {
    if (jModifier.isMoreRestrictiveThan(modifier)) {
      import scala.jdk.CollectionConverters._
      for (vd <- varList.variableDeclarator().asScala) {
        val name = vd.variableDeclaratorId().getText
        val value =
          if (vd.variableInitializer() != null)
            " = " + vd.variableInitializer().getText
          else ""
        if (!classStack.isEmpty) {
          classStack
            .peek()
            .fieldSignatures
            .append(s"${modifier.toString} $typeText$name$value")
        }
      }
    }
  }

}
