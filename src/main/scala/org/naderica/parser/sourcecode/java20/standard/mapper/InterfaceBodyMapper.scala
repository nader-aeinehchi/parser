package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}

import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceBody
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceMemberDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.ConstantDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceMethodDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceClassDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceInterfaceDeclaration

object InterfaceBodyMapper {
  def toInterfaceBody(ctx: InterfaceBodyContext): InterfaceBody = {
    val interfaceMemberDeclarations = ctx
      .interfaceMemberDeclaration()
      .asScala
      .toList
      .map(toInterfaceMemberDeclaration)

    InterfaceBody(interfaceMemberDeclarations)
  }

  def toInterfaceMemberDeclaration(
      ctx: InterfaceMemberDeclarationContext
  ): InterfaceMemberDeclaration = {
    if (ctx.constantDeclaration() != null) {
      toConstantDeclaration(ctx.constantDeclaration())
    } else if (ctx.interfaceMethodDeclaration() != null) {
      toInterfaceMethodDeclaration(ctx.interfaceMethodDeclaration())
    } else if (ctx.classDeclaration() != null) {
      InterfaceClassDeclaration(
        DeclarationMapper.toClassDeclaration(ctx.classDeclaration())
      )
    } else if (ctx.interfaceDeclaration() != null) {
      InterfaceInterfaceDeclaration(
        DeclarationMapper.toInterfaceDeclaration(ctx.interfaceDeclaration())
      )
    } else {
      throw new IllegalArgumentException(
        "Unknown interface member declaration"
      )
    }
  }

  def toConstantDeclaration(
      ctx: ConstantDeclarationContext
  ): ConstantDeclaration = {
    val constantModifiers = ctx
      .constantModifier()
      .asScala
      .toList
      .map(ModifierMapper.toConstantModifier)
    val unannType = TypeMapper.toUnannType(ctx.unannType())
    val variableDeclaratorList =
      VariableMapper.toVariableDeclaratorList(ctx.variableDeclaratorList())

    ConstantDeclaration(
      constantModifiers = constantModifiers,
      unannType = unannType,
      variableDeclaratorList = variableDeclaratorList
    )
  }

  def toInterfaceMethodDeclaration(
      ctx: InterfaceMethodDeclarationContext
  ): InterfaceMethodDeclaration = {
    val interfaceMethodModifiers = ctx
      .interfaceMethodModifier()
      .asScala
      .toList
      .map(ModifierMapper.toInterfaceMethodModifier)
    val methodHeader = MethodMapper.toMethodHeader(ctx.methodHeader())
    val methodBody = MethodMapper.toMethodBody(ctx.methodBody())

    InterfaceMethodDeclaration(
      interfaceMethodModifiers = interfaceMethodModifiers,
      methodHeader = methodHeader,
      methodBody = methodBody
    )
  }
}
