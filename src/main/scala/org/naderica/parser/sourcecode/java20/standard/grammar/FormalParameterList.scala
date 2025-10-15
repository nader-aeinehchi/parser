package org.naderica.parser.sourcecode.java20.standard.grammar

case class FormalParameterList(
      formalParameters: List[FormalParameter],
      variableArityParameter: Option[VariableArityParameter]
  )