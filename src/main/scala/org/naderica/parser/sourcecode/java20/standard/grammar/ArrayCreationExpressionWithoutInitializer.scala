package org.naderica.parser.sourcecode.java20.standard.grammar

case class ArrayCreationExpressionWithoutInitializer(
    primitiveType: Option[PrimitiveType],
    classOrInterfaceType: Option[ClassOrInterfaceType],
    dimExprs: DimExprs,
    dims: Option[Dims]
)
