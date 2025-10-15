package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ThrowsT
import org.naderica.parser.sourcecode.java20.standard.grammar.ExceptionTypeList
import org.naderica.parser.sourcecode.java20.standard.grammar.ExceptionType

object ThrowsMapper {
  def toThrowsT(ctx: ThrowsTContext): ThrowsT = {
    val exceptionTypeList = toExceptionTypeList(ctx.exceptionTypeList())
    
    ThrowsT(exceptionTypeList = exceptionTypeList)
  }

  def toExceptionTypeList(ctx: ExceptionTypeListContext): ExceptionTypeList = {
    import scala.jdk.CollectionConverters._
    val exceptionTypes = ctx.exceptionType().asScala.map(toExceptionType).toList
    
    ExceptionTypeList(exceptionTypes = exceptionTypes)
  }

  def toExceptionType(ctx: ExceptionTypeContext): ExceptionType = {
    if (ctx.classType() != null) {
      ExceptionType(
        classType = Some(TypeMapper.toClassType(ctx.classType())),
        typeVariable = None
      )
    } else if (ctx.typeVariable() != null) {
      ExceptionType(
        classType = None,
        typeVariable = Some(TypeMapper.toTypeVariable(ctx.typeVariable()))
      )
    } else {
      throw new IllegalArgumentException("ExceptionType must have either classType or typeVariable")
    }
  }
}