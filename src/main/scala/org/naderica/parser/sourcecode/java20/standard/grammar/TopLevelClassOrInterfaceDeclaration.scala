package org.naderica.parser.sourcecode.java20.standard.grammar

// Top level declarations
  case class TopLevelClassOrInterfaceDeclaration(
      classDeclaration: Option[ClassDeclaration],
      interfaceDeclaration: Option[InterfaceDeclaration]
  )