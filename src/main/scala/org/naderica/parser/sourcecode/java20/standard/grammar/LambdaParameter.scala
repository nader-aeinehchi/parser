package org.naderica.parser.sourcecode.java20.standard.grammar

case class LambdaParameter(
    variableModifiers: List[VariableModifier],
    lambdaParameterType: LambdaParameterType,
    variableDeclaratorId: VariableDeclaratorId
)
