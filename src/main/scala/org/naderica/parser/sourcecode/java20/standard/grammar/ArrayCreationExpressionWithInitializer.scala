package org.naderica.parser.sourcecode.java20.standard.grammar

case class ArrayCreationExpressionWithInitializer(
    primitiveType: Option[PrimitiveType],
    classOrInterfaceType: Option[ClassOrInterfaceType],
    dims: Dims,
    arrayInitializer: ArrayInitializer
)
