package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.VariableDeclaratorList
import org.naderica.parser.sourcecode.java20.standard.grammar.VariableDeclarator

object VariableMapper {
    def toVariableDeclaratorList(
        ctx: VariableDeclaratorListContext
    ): VariableDeclaratorList = {
      VariableDeclaratorList(
        variableDeclarators = ctx.variableDeclarator().asScala.toList.map(toVariableDeclarator)
      )
    }
    
    def toVariableDeclarator(ctx: VariableDeclaratorContext): VariableDeclarator = {
      VariableDeclarator(
        variableDeclaratorId = ExpressionMapper.toVariableDeclaratorId(ctx.variableDeclaratorId()),
        variableInitializer = Option(ctx.variableInitializer()).map(ExpressionMapper.toVariableInitializer)
      )
    }
  }