package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ImportDeclaration
case class SingleTypeImportDeclaration(typeName: TypeName)
    extends ImportDeclaration
case class TypeImportOnDemandDeclaration(packageOrTypeName: PackageOrTypeName)
    extends ImportDeclaration
case class SingleStaticImportDeclaration(
    typeName: TypeName,
    identifier: Identifier
) extends ImportDeclaration
case class StaticImportOnDemandDeclaration(typeName: TypeName)
    extends ImportDeclaration
