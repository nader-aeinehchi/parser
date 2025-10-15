package org.naderica.parser.sourcecode.java20.standard.grammar

// Field declaration
  case class FieldDeclaration(
      fieldModifiers: List[FieldModifier],
      unannType: UnannType,
      variableDeclaratorList: VariableDeclaratorList
  )