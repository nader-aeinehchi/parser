package org.naderica.parser.sourcecode.java20.standard.grammar

// Root compilation unit - matches the generated parser structure
sealed trait CompilationUnit

case class OrdinaryCompilationUnit(
    packageDeclaration: Option[PackageDeclaration],
    importDeclarations: List[ImportDeclaration],
    topLevelClassOrInterfaceDeclarations: List[
      TopLevelClassOrInterfaceDeclaration
    ]
) extends CompilationUnit

case class ModularCompilationUnit(
    importDeclarations: List[ImportDeclaration],
    moduleDeclaration: ModuleDeclaration
) extends CompilationUnit
