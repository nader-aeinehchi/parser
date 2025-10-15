package org.naderica.parser.sourcecode.java20.standard.grammar

case class VariableAccess(
    expressionName: Option[ExpressionName],
    fieldAccess: Option[FieldAccess]
)
