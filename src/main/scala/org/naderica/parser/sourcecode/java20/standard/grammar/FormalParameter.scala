package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait FormalParameter
case class RegularFormalParameter(
    variableModifiers: List[VariableModifier],
    unannType: UnannType,
    variableDeclaratorId: VariableDeclaratorId
) extends FormalParameter

case class VariableArityParameter(
    variableModifiers: List[VariableModifier],
    unannType: UnannType,
    annotations: List[Annotation],
    identifier: Identifier
)
