package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationInterfaceBody
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationInterfaceElementDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationInterfaceMemberDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationClassDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationInterfaceDeclaration2
import org.naderica.parser.sourcecode.java20.standard.grammar.DefaultValue
import org.naderica.parser.sourcecode.java20.standard.mapper.{
  ModifierMapper,
  TypeMapper,
  DeclarationMapper,
  ElementValueMapper
}

object AnnotationInterfaceBodyMapper {
  def toAnnotationInterfaceBody(
      ctx: AnnotationInterfaceBodyContext
  ): AnnotationInterfaceBody = {
    val annotationInterfaceElementDeclarations = ctx
      .annotationInterfaceMemberDeclaration() // Fixed: use correct method name
      .asScala
      .toList
      .map(
        toAnnotationInterfaceMemberDeclaration
      ) // Fixed: use correct method name

    AnnotationInterfaceBody(annotationInterfaceElementDeclarations)
  }

  def toAnnotationInterfaceElementDeclaration(
      ctx: AnnotationInterfaceElementDeclarationContext
  ): AnnotationInterfaceElementDeclaration = {
    val annotationInterfaceElementModifiers = ctx
      .annotationInterfaceElementModifier()
      .asScala
      .toList
      .map(ModifierMapper.toAnnotationInterfaceElementModifier)
    val unannType = TypeMapper.toUnannType(ctx.unannType())
    val identifier =
      org.naderica.parser.sourcecode.java20.standard.grammar
        .Identifier(ctx.identifier().getText)
    val dims = Option(ctx.dims()).map(TypeMapper.toDims)
    val defaultValue = Option(ctx.defaultValue()).map(toDefaultValue)

    AnnotationInterfaceElementDeclaration(
      annotationInterfaceElementModifiers = annotationInterfaceElementModifiers,
      unannType = unannType,
      identifier = identifier,
      dims = dims,
      defaultValue = defaultValue
    )
  }
  def toAnnotationInterfaceMemberDeclaration(
      ctx: AnnotationInterfaceMemberDeclarationContext
  ): AnnotationInterfaceMemberDeclaration = {
    if (ctx.annotationInterfaceElementDeclaration() != null) {
      toAnnotationInterfaceElementDeclaration(
        ctx.annotationInterfaceElementDeclaration()
      )
    } else if (ctx.classDeclaration() != null) {
      AnnotationClassDeclaration(
        DeclarationMapper.toClassDeclaration(ctx.classDeclaration())
      )
    } else if (ctx.interfaceDeclaration() != null) {
      AnnotationInterfaceDeclaration2(
        DeclarationMapper.toInterfaceDeclaration(ctx.interfaceDeclaration())
      )
    } else {
      throw new IllegalArgumentException(
        "Unknown annotation interface member declaration"
      )
    }
  }
  // def toAnnotationInterfaceMethodDeclaration(
  //     ctx: AnnotationInterfaceElementDeclarationContext
  // ): AnnotationInterfaceElementDeclaration = {
  //   val annotationInterfaceElementModifiers = ctx
  //     .annotationInterfaceElementModifier()
  //     .asScala
  //     .toList
  //     .map(ModifierMapper.toAnnotationInterfaceElementModifier)
  //   val unannType = TypeMapper.toUnannType(ctx.unannType())
  //   val identifier = Identifier(ctx.identifier().getText)
  //   val defaultValue = Option(ctx.defaultValue()).map(toDefaultValue)

  //   AnnotationInterfaceMethodDeclaration(
  //     annotationInterfaceElementModifiers =
  //       annotationInterfaceElementModifiers,
  //     unannType = unannType,
  //     identifier = identifier,
  //     defaultValue = defaultValue
  //   )
  // }

  def toDefaultValue(ctx: DefaultValueContext): DefaultValue = {
    DefaultValue(
      elementValue = ElementValueMapper.toElementValue(ctx.elementValue())
    )
  }
}
