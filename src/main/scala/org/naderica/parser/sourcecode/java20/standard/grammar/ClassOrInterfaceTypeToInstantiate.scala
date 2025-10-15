package org.naderica.parser.sourcecode.java20.standard.grammar

case class ClassOrInterfaceTypeToInstantiate(
    annotations: List[Annotation],
    typeIdentifier: TypeIdentifier,
    typeArgumentsOrDiamond: Option[TypeArgumentsOrDiamond],
    nestedType: Option[ClassOrInterfaceTypeToInstantiate]
)
