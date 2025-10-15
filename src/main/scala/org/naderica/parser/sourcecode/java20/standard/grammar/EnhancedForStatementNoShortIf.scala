package org.naderica.parser.sourcecode.java20.standard.grammar

case class EnhancedForStatementNoShortIf(
    variableModifiers: List[VariableModifier],
    localVariableType: LocalVariableType,
    identifier: Identifier,
    expression: Expression,
    statementNoShortIf: StatementNoShortIf
)
