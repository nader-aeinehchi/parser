package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait UnaryExpression
case class PreIncrementExpression(unaryExpression: UnaryExpression)
    extends UnaryExpression
case class PreDecrementExpression(unaryExpression: UnaryExpression)
    extends UnaryExpression
case class UnaryPlusExpression(unaryExpression: UnaryExpression)
    extends UnaryExpression
case class UnaryMinusExpression(unaryExpression: UnaryExpression)
    extends UnaryExpression
case class UnaryExpressionNotPlusMinus(
    postfixExpression: Option[PostfixExpression],
    bitwiseComplementExpression: Option[UnaryExpression], // ~
    logicalComplementExpression: Option[UnaryExpression], // !
    castExpression: Option[CastExpression]
) extends UnaryExpression

