package org.naderica.parser.sourcecode.java.standard

import java.util.{ArrayList, List, Stack}
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.ParserRuleContext
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.ast.java.standard.Java20ParserBaseVisitor

enum JavaAccessModifier {
  case PUBLIC, PROTECTED, PRIVATE, PACKAGE_PRIVATE

  def implied: Set[JavaAccessModifier] = this match {
    case PRIVATE         => Set(PRIVATE, PACKAGE_PRIVATE, PROTECTED, PUBLIC)
    case PACKAGE_PRIVATE => Set(PACKAGE_PRIVATE, PROTECTED, PUBLIC)
    case PROTECTED       => Set(PROTECTED, PUBLIC)
    case PUBLIC          => Set(PUBLIC)
  }
}

class SignatureVisitor(
    val tokens: CommonTokenStream,
    val accessModifier: JavaAccessModifier = JavaAccessModifier.PUBLIC
) extends Java20ParserBaseVisitor[Unit] {

  private val classStack = new Stack[ClassInfo]()
  private val topLevelClasses = new ArrayList[ClassInfo]()

  def getResult: List[ClassInfo] = topLevelClasses

  override def visitNormalClassDeclaration(
      ctx: NormalClassDeclarationContext
  ): Unit = {

    handleTopLevelConstruct(
      ctx,
      _.classBody(),
      extractAccessModifier(ctx.classModifier())
    )
    super.visitNormalClassDeclaration(ctx)
  }

  override def visitNormalInterfaceDeclaration(
      ctx: NormalInterfaceDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.interfaceBody(),
      extractAccessModifier(ctx.interfaceModifier())
    )
    super.visitNormalInterfaceDeclaration(ctx)
  }

  override def visitEnumDeclaration(
      ctx: EnumDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.enumBody(),
      extractAccessModifier(ctx.classModifier())
    )
    super.visitEnumDeclaration(ctx)
  }

  override def visitRecordDeclaration(
      ctx: RecordDeclarationContext
  ): Unit = {
    handleTopLevelConstruct(
      ctx,
      _.recordBody(),
      extractAccessModifier(ctx.classModifier())
    )
    super.visitRecordDeclaration(ctx)
  }

  override def visitMethodDeclaration(ctx: MethodDeclarationContext): Unit = {
    val modifier = extractAccessModifier(ctx.methodModifier())
    if (accessModifier.implied.contains(modifier)) {
      extractAndAddMemberSignature(ctx, _.methodBody())
    }
  }

// @Override public T visitInterfaceMethodDeclaration(Java20Parser.InterfaceMethodDeclarationContext ctx) { return visitChildren(ctx); }

  override def visitInterfaceMethodDeclaration(
      ctx: InterfaceMethodDeclarationContext
  ): Unit = {
    val modifier = extractAccessModifier(ctx.interfaceMethodModifier())
    if (accessModifier.implied.contains(modifier)) {
      extractAndAddMemberSignature(ctx, _.methodBody())
    }
  }

  override def visitConstructorDeclaration(
      ctx: ConstructorDeclarationContext
  ): Unit = {
    val modifier = extractAccessModifier(ctx.constructorModifier())
    if (accessModifier.implied.contains(modifier)) {
      extractAndAddMemberSignature(ctx, _.constructorBody())
    }
  }

  /** Helper to handle top-level constructs (class, interface, enum, record) */
  private def handleTopLevelConstruct[C](
      ctx: C,
      getBody: C => org.antlr.v4.runtime.ParserRuleContext,
      modifier: JavaAccessModifier
  ): Unit = {
    if (accessModifier.implied.contains(modifier)) {
      val signature = extractSignature(ctx, getBody)
      val currentClass = ClassInfo()
      currentClass.signature = signature

      if (classStack.isEmpty) {
        topLevelClasses.add(currentClass)
      } else {
        classStack.peek().innerClasses.add(currentClass)
      }
      classStack.push(currentClass)
    }
  }

  /** Helper to extract and add member signature */
  private def extractAndAddMemberSignature[C](
      ctx: C,
      getBody: C => org.antlr.v4.runtime.ParserRuleContext
  ): Unit = {
    val signature = extractSignature(ctx, getBody)
    if (!classStack.isEmpty) {
      classStack.peek().memberSignatures.add(signature)
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
  private def extractAccessModifier(
      modifiers: java.util.List[?]
  ): JavaAccessModifier = {
    import scala.jdk.CollectionConverters._

    val mods = modifiers.asScala.map {
      case ctx: ParserRuleContext => ctx.getText
      case other                  => other.toString
    }

    if (mods.contains("private")) JavaAccessModifier.PRIVATE
    else if (mods.contains("protected")) JavaAccessModifier.PROTECTED
    else if (mods.contains("public")) JavaAccessModifier.PUBLIC
    else JavaAccessModifier.PACKAGE_PRIVATE
  }
}
