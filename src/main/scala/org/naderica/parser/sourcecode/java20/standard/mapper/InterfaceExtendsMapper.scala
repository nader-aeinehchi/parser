package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceType
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceTypeList
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceExtends

object InterfaceExtendsMapper {
    def toInterfaceExtends(ctx: InterfaceExtendsContext): InterfaceExtends = {
      // InterfaceExtendsContext typically has an interfaceTypeList
      val interfaceTypeList = if (ctx.interfaceTypeList() != null) {
        TypeMapper.toInterfaceTypeList(ctx.interfaceTypeList())
      } else {
        // Fallback: create empty list if no interface type list
        InterfaceTypeList(List.empty)
      }

      InterfaceExtends(interfaceTypeList)
    }
    def toInterfaceType(ctx: InterfaceTypeContext): InterfaceType = {
      // InterfaceType is typically just a wrapper around ClassType
      InterfaceType(TypeMapper.toClassType(ctx.classType()))
    }
  }