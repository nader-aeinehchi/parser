package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Success, Failure}

import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.MethodBody
import org.naderica.parser.sourcecode.java20.standard.grammar.MethodHeader
import org.naderica.parser.sourcecode.java20.standard.grammar.BlockMethodBody
import org.naderica.parser.sourcecode.java20.standard.grammar.EmptyMethodBody
import org.naderica.parser.sourcecode.java20.standard.grammar.Result
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeResult
import org.naderica.parser.sourcecode.java20.standard.grammar.VoidResult
import org.naderica.parser.sourcecode.java20.standard.grammar.MethodDeclarator
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeParameters
import org.naderica.parser.sourcecode.java20.standard.grammar.ThrowsT
import org.naderica.parser.sourcecode.java20.standard.grammar.FormalParameterList
import org.naderica.parser.sourcecode.java20.standard.grammar.FormalParameter
import org.naderica.parser.sourcecode.java20.standard.grammar.RegularFormalParameter
import org.naderica.parser.sourcecode.java20.standard.grammar.VariableArityParameter
import org.naderica.parser.sourcecode.java20.standard.grammar.UnqualifiedMethodIdentifier
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier
import org.naderica.parser.sourcecode.java20.standard.grammar.ReceiverParameter


object MethodMapper {
  def toMethodHeader(ctx: MethodHeaderContext): MethodHeader = {
    val typeParameters = Option(ctx.typeParameters()).map(TypeMapper.toTypeParameters)
    val result = toResult(ctx.result())
    val methodDeclarator = toMethodDeclarator(ctx.methodDeclarator())
    val throwsClause = Option(ctx.throwsT()).map(ThrowsMapper.toThrowsT)

    MethodHeader(
      typeParameters = typeParameters,
      result = result,
      methodDeclarator = methodDeclarator,
      throwsClause = throwsClause
    )
  }

  def toMethodBody(ctx: MethodBodyContext): MethodBody = {
    if (ctx.block() != null) {
      BlockMethodBody(ExpressionMapper.toBlock(ctx.block()))
    } else {
      // Method body is just a semicolon (abstract method or interface method)
      EmptyMethodBody
    }
  }

  def toResult(ctx: ResultContext): Result = {
    if (ctx.unannType() != null) {
      TypeResult(TypeMapper.toUnannType(ctx.unannType()))
    } else {
      // void result
      VoidResult
    }
  }

  def toMethodDeclarator(ctx: MethodDeclaratorContext): MethodDeclarator = {
    val unqualifiedMethodIdentifier = UnqualifiedMethodIdentifier(ctx.identifier().getText)
    val receiverParameter = None  // Optional for now
    val formalParameterList = Option(ctx.formalParameterList()).map(toFormalParameterList)
    val dims = Option(ctx.dims()).map(TypeMapper.toDims)

    MethodDeclarator(
      unqualifiedMethodIdentifier = unqualifiedMethodIdentifier,
      receiverParameter = receiverParameter,
      formalParameterList = formalParameterList,
      dims = dims
    )
  }

  def toFormalParameterList(ctx: FormalParameterListContext): FormalParameterList = {
    import scala.jdk.CollectionConverters._
    FormalParameterList(
      formalParameters = ctx.formalParameter().asScala.map(toFormalParameter).toList,
      variableArityParameter = None  // Handle this later if needed
    )
  }

  def toFormalParameter(ctx: FormalParameterContext): FormalParameter = {
    import scala.jdk.CollectionConverters._
    RegularFormalParameter(
      variableModifiers = Option(ctx.variableModifier()).map(_.asScala.map(ModifierMapper.toVariableModifier).toList).getOrElse(List.empty),
      unannType = TypeMapper.toUnannType(ctx.unannType()),
      variableDeclaratorId = ExpressionMapper.toVariableDeclaratorId(ctx.variableDeclaratorId())
    )
  }
}