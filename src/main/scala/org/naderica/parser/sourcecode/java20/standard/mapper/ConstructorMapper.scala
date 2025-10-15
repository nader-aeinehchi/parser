package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ConstructorDeclarator
import org.naderica.parser.sourcecode.java20.standard.grammar.ConstructorBody
import org.naderica.parser.sourcecode.java20.standard.grammar.ConstructorDeclaration


object ConstructorMapper {
    def toConstructorDeclarator(
        ctx: ConstructorDeclaratorContext
    ): ConstructorDeclarator = ???
    def toConstructorBody(ctx: ConstructorBodyContext): ConstructorBody = ???
  }