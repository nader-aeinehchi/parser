package org.naderica.parser.sourcecode.java20.standard.grammar

case class CatchFormalParameter(
    variableModifiers: List[VariableModifier],
    catchType: CatchType,
    identifier: Identifier
)
