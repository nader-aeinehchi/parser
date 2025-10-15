package org.naderica.parser.sourcecode.java20.standard.grammar

case class TypeBound(
    typeVariable: Option[TypeVariable],
    classOrInterfaceType: Option[ClassOrInterfaceType],
    additionalBounds: List[AdditionalBound]
)
