package org.naderica.parser.sourcecode.java20.standard.grammar

case class EnhancedForStatement(
    variableModifiers: List[VariableModifier],
    localVariableType: LocalVariableType,
    identifier: Identifier,
    expression: Expression,
    statement: Statement
)
