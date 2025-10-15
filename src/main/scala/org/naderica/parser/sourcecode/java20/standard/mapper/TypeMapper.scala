package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.*

object TypeMapper {
  def toUnannType(ctx: UnannTypeContext): UnannType = {
    if (ctx.unannPrimitiveType() != null) {
      UnannPrimitiveType(
        toPrimitiveType(ctx.unannPrimitiveType())
      )
    } else if (ctx.unannReferenceType() != null) {
      toUnannReferenceType(ctx.unannReferenceType())
    } else {
      throw new IllegalArgumentException("Unknown unann type")
    }
  }

  def toUnannReferenceType(
      ctx: UnannReferenceTypeContext
  ): UnannReferenceType = {
    UnannReferenceType(
      unannClassOrInterfaceType = Option(ctx.unannClassOrInterfaceType())
        .map(toUnannClassOrInterfaceType),
      unannTypeVariable =
        Option(ctx.unannTypeVariable()).map(toUnannTypeVariable),
      unannArrayType = Option(ctx.unannArrayType()).map(toUnannArrayType)
    )
  }

  def toUnannClassOrInterfaceType(
      ctx: UnannClassOrInterfaceTypeContext
  ): UnannClassOrInterfaceType = {
    if (ctx.uCOIT() != null) {
      // If there's a nested UCOIT context, delegate to it
      UnannClassOrInterfaceType(toUCOIT(ctx.uCOIT()))
    } else {
      // Simple case - create UCOIT from the direct type identifier
      UnannClassOrInterfaceType(
        UCOIT(
          unannClassType = Some(
            UnannClassType(
              typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
              typeArguments = Option(ctx.typeArguments()).map(toTypeArguments),
              nestedUnannClassType = None
            )
          ),
          unannInterfaceType = None
        )
      )
    }
  }

  def toUCOIT(ctx: UCOITContext): UCOIT = {
    // Note: Without additional context from the grammar about whether this is specifically
    // a class or interface, we'll default to treating it as a class type.
    // In a more complete implementation, this might need semantic analysis.
    UCOIT(
      unannClassType = Some(
        UnannClassType(
          typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
          typeArguments = Option(ctx.typeArguments()).map(toTypeArguments),
          nestedUnannClassType = Option(ctx.uCOIT())
            .map(toUCOIT)
            .map(ucoit =>
              // Extract the class type from the nested UCOIT
              ucoit.unannClassType.getOrElse(
                throw new IllegalArgumentException(
                  "Nested UCOIT must contain a class type"
                )
              )
            )
        )
      ),
      unannInterfaceType =
        None // Would need grammar context to determine if this should be an interface
    )
  }

  def toUnannClassType(ctx: UnannClassTypeContext): UnannClassType = {
    UnannClassType(
      typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
      typeArguments = Option(ctx.typeArguments()).map(toTypeArguments),
      nestedUnannClassType =
        Option(ctx.unannClassOrInterfaceType()).map(ucoit =>
          // If there's a nested unannClassOrInterfaceType, extract the class type from it
          toUnannClassOrInterfaceType(ucoit) match {
            case UnannClassOrInterfaceType(UCOIT(Some(classType), _)) =>
              classType
            case _ =>
              throw new IllegalArgumentException(
                "Nested type must be a class type"
              )
          }
        )
    )
  }

  def toUnannInterfaceType(
      ctx: UnannInterfaceTypeContext
  ): UnannInterfaceType = {
    UnannInterfaceType(toUnannClassType(ctx.unannClassType()))
  }

  def toUnannTypeVariable(
      ctx: UnannTypeVariableContext
  ): UnannTypeVariable = {
    UnannTypeVariable(TypeIdentifier(ctx.typeIdentifier().getText))
  }

  def toUnannArrayType(ctx: UnannArrayTypeContext): UnannArrayType = {
    UnannArrayType(
      unannPrimitiveType = Option(ctx.unannPrimitiveType()).map(pt =>
        // Fix: UnannPrimitiveTypeContext doesn't have primitiveType() method
        // It directly represents the primitive type
        UnannPrimitiveType(PrimitiveType(pt.getText))
      ),
      unannClassOrInterfaceType = Option(ctx.unannClassOrInterfaceType())
        .map(toUnannClassOrInterfaceType),
      unannTypeVariable =
        Option(ctx.unannTypeVariable()).map(toUnannTypeVariable),
      dims = toDims(ctx.dims())
    )
  }
  def toPrimitiveType(ctx: UnannPrimitiveTypeContext): PrimitiveType = {
    PrimitiveType(ctx.getText)
  }
// Overloaded method for PrimitiveTypeContext
  def toPrimitiveType(ctx: PrimitiveTypeContext): PrimitiveType = {
    PrimitiveType(ctx.getText)
  }
  def toTypeParameters(ctx: TypeParametersContext): TypeParameters = {
    TypeParameters(toTypeParameterList(ctx.typeParameterList()))
  }

  def toTypeParameterList(
      ctx: TypeParameterListContext
  ): TypeParameterList = {
    TypeParameterList(ctx.typeParameter().asScala.toList.map(toTypeParameter))
  }

  def toTypeParameter(ctx: TypeParameterContext): TypeParameter = {
    val typeParameterModifiers = ctx
      .typeParameterModifier()
      .asScala
      .toList
      .map(ModifierMapper.toTypeParameterModifier)
    val typeBound = Option(ctx.typeBound()).map(toTypeBound)

    TypeParameter(
      typeParameterModifiers = typeParameterModifiers,
      typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
      typeBound = typeBound
    )
  }

  def toTypeBound(ctx: TypeBoundContext): TypeBound = {
    TypeBound(
      typeVariable = Option(ctx.typeVariable()).map(toTypeVariable),
      classOrInterfaceType =
        Option(ctx.classOrInterfaceType()).map(toClassOrInterfaceType),
      additionalBounds =
        ctx.additionalBound().asScala.toList.map(toAdditionalBound)
    )
  }

  def toTypeVariable(ctx: TypeVariableContext): TypeVariable = {
    TypeVariable(
      annotations =
        ctx.annotation().asScala.toList.map(AnnotationMapper.toAnnotation),
      typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText)
    )
  }

  def toTypeIdentifier(ctx: TypeIdentifierContext): TypeIdentifier = {
    TypeIdentifier(ctx.getText)
  }

  def toClassOrInterfaceType(
      ctx: ClassOrInterfaceTypeContext
  ): ClassOrInterfaceType = {
    // Note: The ANTLR grammar cannot distinguish between class and interface types
    // at parse time - this is a semantic distinction that requires symbol resolution.
    // For now, we default to treating all as class types. A complete implementation
    // would defer this decision to a semantic analysis phase.

    val classType = Some(
      ClassType(
        annotations =
          ctx.annotation().asScala.toList.map(AnnotationMapper.toAnnotation),
        typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
        typeArguments = Option(ctx.typeArguments()).map(toTypeArguments),
        nestedClassType = None // Handle nested types if needed
      )
    )

    ClassOrInterfaceType(
      classType = classType,
      interfaceType =
        None // Semantic analysis would determine if this should be an interface
    )
  }

  def toClassType(ctx: ClassTypeContext): ClassType = {
    ClassType(
      annotations =
        ctx.annotation().asScala.toList.map(AnnotationMapper.toAnnotation),
      typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
      typeArguments = Option(ctx.typeArguments()).map(toTypeArguments),
      nestedClassType = None
    )
  }

  def toClassExtends(ctx: ClassExtendsContext): ClassExtends = {
    val classType = toClassType(ctx.classType())
    ClassExtends(classType)
  }

  def toInterfaceTypeList(
      ctx: InterfaceTypeListContext
  ): InterfaceTypeList = {
    val interfaceTypes =
      ctx.interfaceType().asScala.toList.map(toInterfaceType)
    InterfaceTypeList(interfaceTypes)
  }
  def toInterfaceType(ctx: InterfaceTypeContext): InterfaceType = {
    InterfaceType(toClassType(ctx.classType()))
  }

  def toAdditionalBound(ctx: AdditionalBoundContext): AdditionalBound = {
    AdditionalBound(toInterfaceType(ctx.interfaceType()))
  }

  def toTypeArguments(ctx: TypeArgumentsContext): TypeArguments = {
    TypeArguments(toTypeArgumentList(ctx.typeArgumentList()))
  }

  def toTypeArgumentList(ctx: TypeArgumentListContext): TypeArgumentList = {
    TypeArgumentList(ctx.typeArgument().asScala.toList.map(toTypeArgument))
  }

  def toTypeArgument(ctx: TypeArgumentContext): TypeArgument = {
    if (ctx.referenceType() != null) {
      ReferenceTypeArgument(toReferenceType(ctx.referenceType()))
    } else if (ctx.wildcard() != null) {
      WildcardArgument(toWildcard(ctx.wildcard()))
    } else {
      throw new IllegalArgumentException("Unknown type argument")
    }
  }

  def toReferenceType(ctx: ReferenceTypeContext): ReferenceType = {
    ReferenceType(
      classOrInterfaceType =
        Option(ctx.classOrInterfaceType()).map(toClassOrInterfaceType),
      typeVariable = Option(ctx.typeVariable()).map(toTypeVariable),
      arrayType = Option(ctx.arrayType()).map(toArrayType)
    )
  }

  def toArrayType(ctx: ArrayTypeContext): ArrayType = {
    ArrayType(
      primitiveType = Option(ctx.primitiveType()).map(toPrimitiveType),
      classOrInterfaceType = Option(ctx.classType()).map(ct =>
        // Convert ClassType to ClassOrInterfaceType
        // Note: Treating as class type since ctx.classType() specifically indicates a class
        ClassOrInterfaceType(
          classType = Some(toClassType(ct)),
          interfaceType =
            None // Correctly None since ctx.classType() is specifically a class
        )
      ),
      typeVariable = Option(ctx.typeVariable()).map(toTypeVariable),
      dims = toDims(ctx.dims())
    )
  }

  def toWildcard(ctx: WildcardContext): Wildcard = {
    Wildcard(
      annotations =
        ctx.annotation().asScala.toList.map(AnnotationMapper.toAnnotation),
      wildcardBounds = Option(ctx.wildcardBounds()).map(toWildcardBounds)
    )
  }

  def toWildcardBounds(ctx: WildcardBoundsContext): WildcardBounds = {
    if (ctx.EXTENDS() != null) {
      ExtendsWildcardBounds(toReferenceType(ctx.referenceType()))
    } else if (ctx.SUPER() != null) {
      SuperWildcardBounds(toReferenceType(ctx.referenceType()))
    } else {
      throw new IllegalArgumentException("Unknown wildcard bounds")
    }
  }

  def toDims(ctx: DimsContext): Dims = {
    val dimensionCount = ctx.LBRACK().size()
    val allAnnotations =
      ctx.annotation().asScala.toList.map(AnnotationMapper.toAnnotation)

    // Create annotation lists for each dimension
    // For simplicity, put all annotations on the first dimension, empty lists for others
    val annotationLists = if (dimensionCount > 0) {
      if (allAnnotations.nonEmpty) {
        allAnnotations :: List.fill(dimensionCount - 1)(
          List.empty[Annotation]
        )
      } else {
        List.fill(dimensionCount)(List.empty[Annotation])
      }
    } else {
      List.empty[List[Annotation]]
    }

    Dims(annotationLists, dimensionCount)
  }
}
