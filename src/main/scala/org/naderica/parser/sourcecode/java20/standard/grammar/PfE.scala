package org.naderica.parser.sourcecode.java20.standard.grammar

case class PfE(
    primary: Option[Primary],
    expressionName: Option[ExpressionName],
    postIncrementExpression: Option[PostfixExpression],
    postDecrementExpression: Option[PostfixExpression]
)
