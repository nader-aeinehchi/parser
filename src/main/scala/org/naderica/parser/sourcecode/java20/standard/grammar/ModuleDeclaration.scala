package org.naderica.parser.sourcecode.java20.standard.grammar

// Module system
case class ModuleDeclaration(
    moduleModifiers: List[Annotation],
    open: Option[String], // "open" keyword if present
    moduleName: ModuleName,
    moduleDirectives: List[ModuleDirective]
)
