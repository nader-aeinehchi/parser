package org.naderica.parser.sourcecode.java20.standard.grammar

case class TypeName(
    typeIdentifier: TypeIdentifier,
    packageOrTypeName: Option[PackageOrTypeName]
)
