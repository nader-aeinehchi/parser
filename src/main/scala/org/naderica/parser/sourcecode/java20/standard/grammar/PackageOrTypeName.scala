package org.naderica.parser.sourcecode.java20.standard.grammar

case class PackageOrTypeName(
    identifier: Identifier,
    packageOrTypeName: Option[PackageOrTypeName]
)
