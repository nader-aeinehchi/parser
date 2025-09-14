package org.naderica.parser.sourcecode.java.standard

import java.util.ArrayList
import java.util.List
import java.util.Stack

import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.ast.java.standard.Java20ParserBaseVisitor

class SignatureVisitor(val tokens: CommonTokenStream)
    extends Java20ParserBaseVisitor[Unit] {

  private val classStack = new Stack[ClassInfo]()
  private val topLevelClasses = new ArrayList[ClassInfo]()

  override def visitNormalClassDeclaration(
      ctx: NormalClassDeclarationContext
  ): Unit = {
    val classSignature = extractSignature(ctx, _.classBody())
    val currentClass = ClassInfo()
    currentClass.signature = classSignature

    if (classStack.isEmpty) {
      topLevelClasses.add(currentClass)
    } else {
      classStack.peek().innerClasses.add(currentClass)
    }
    classStack.push(currentClass)
    super.visitNormalClassDeclaration(ctx)
  }

  override def visitMethodDeclaration(ctx: MethodDeclarationContext): Unit = {
    val methodSignature = extractSignature(ctx, _.methodBody())
    if (!classStack.isEmpty) {
      classStack.peek().memberSignatures.add(methodSignature)
    }
    ()
  }

  override def visitConstructorDeclaration(
      ctx: ConstructorDeclarationContext
  ): Unit = {
    val constructorSignature = extractSignature(ctx, _.constructorBody())
    if (!classStack.isEmpty) {
      classStack.peek().memberSignatures.add(constructorSignature)
    }
    ()
  }

  def getResult: List[ClassInfo] = topLevelClasses

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

}
