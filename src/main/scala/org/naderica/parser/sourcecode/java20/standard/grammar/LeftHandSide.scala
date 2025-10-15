package org.naderica.parser.sourcecode.java20.standard.grammar

case class LeftHandSide(
    expressionName: Option[ExpressionName],
    fieldAccess: Option[FieldAccess],
    arrayAccess: Option[ArrayAccess]
)
