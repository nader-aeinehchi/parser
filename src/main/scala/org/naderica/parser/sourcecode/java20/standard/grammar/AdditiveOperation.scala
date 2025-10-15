package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait AdditiveOperation
case class Addition(multiplicativeExpression: MultiplicativeExpression)
    extends AdditiveOperation
case class Subtraction(multiplicativeExpression: MultiplicativeExpression)
    extends AdditiveOperation

case class MultiplicativeExpression(
    unaryExpression: UnaryExpression,
    operations: List[MultiplicativeOperation]
)
