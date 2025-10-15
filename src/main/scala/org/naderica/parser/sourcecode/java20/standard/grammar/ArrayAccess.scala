package org.naderica.parser.sourcecode.java20.standard.grammar

case class ArrayAccess(
    expressionName: Option[ExpressionName],
    primaryNoNewArray: Option[PrimaryNoNewArray],
    arrayAccess: Option[ArrayAccess], // recursive
    expression: Expression
)
