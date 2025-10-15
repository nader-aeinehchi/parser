package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassBodyDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassBody
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassMemberDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InstanceInitializer
import org.naderica.parser.sourcecode.java20.standard.grammar.StaticInitializer
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceDeclaration

// Additional mapper objects would continue here...
// ClassBodyMapper, MethodMapper, VariableMapper, etc.
// I'll provide a few more key ones:

object ClassBodyMapper {
  def toClassBody(ctx: ClassBodyContext): ClassBody = {
    ClassBody(
      ctx.classBodyDeclaration().asScala.toList.map(toClassBodyDeclaration)
    )
  }

  def toClassBodyDeclaration(
      ctx: ClassBodyDeclarationContext
  ): ClassBodyDeclaration = {
    if (ctx.classMemberDeclaration() != null) {
      toClassMemberDeclaration(ctx.classMemberDeclaration())
    } else if (ctx.instanceInitializer() != null) {
      InstanceInitializer(
        BlockMapper.toBlock(ctx.instanceInitializer().block())
      )
    } else if (ctx.staticInitializer() != null) {
      StaticInitializer(BlockMapper.toBlock(ctx.staticInitializer().block()))
    } else if (ctx.constructorDeclaration() != null) {
      DeclarationMapper.toConstructorDeclaration(ctx.constructorDeclaration())
    } else {
      throw new IllegalArgumentException("Unknown class body declaration")
    }
  }

  def toClassMemberDeclaration(
      ctx: ClassMemberDeclarationContext
  ): ClassMemberDeclaration = {
    ClassMemberDeclaration(
      fieldDeclaration = Option(ctx.fieldDeclaration())
        .map(DeclarationMapper.toFieldDeclaration),
      methodDeclaration = Option(ctx.methodDeclaration())
        .map(DeclarationMapper.toMethodDeclaration),
      classDeclaration = Option(ctx.classDeclaration())
        .map(DeclarationMapper.toClassDeclaration),
      interfaceDeclaration = Option(ctx.interfaceDeclaration())
        .map(DeclarationMapper.toInterfaceDeclaration)
    )
  }
}
