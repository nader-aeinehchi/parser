package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ModuleDirective
case class RequiresDirective(
    requiresModifiers: List[RequiresModifier],
    moduleName: ModuleName
) extends ModuleDirective
case class ExportsDirective(
    packageName: PackageName,
    moduleNames: Option[List[ModuleName]]
) extends ModuleDirective
case class OpensDirective(
    packageName: PackageName,
    moduleNames: Option[List[ModuleName]]
) extends ModuleDirective
case class UsesDirective(typeName: TypeName) extends ModuleDirective
case class ProvidesDirective(
    typeName: TypeName,
    withTypeNames: List[TypeName]
) extends ModuleDirective
