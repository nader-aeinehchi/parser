package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassPermits

object ClassPermitsMapper {
  def toClassPermits(ctx: ClassPermitsContext): ClassPermits = {
    // ClassPermitsContext likely has direct access to typeName() methods
    val typeNames =
      ctx.typeName().asScala.toList.map(TypeNameMapper.toTypeName)
    ClassPermits(typeNames)
  }
}
