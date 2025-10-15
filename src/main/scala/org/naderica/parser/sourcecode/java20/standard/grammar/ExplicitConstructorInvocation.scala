package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ExplicitConstructorInvocation
case class ThisConstructorInvocation(
    typeArguments: Option[TypeArguments],
    argumentList: Option[ArgumentList]
) extends ExplicitConstructorInvocation

case class SuperConstructorInvocation(
    expressionName: Option[ExpressionName],
    typeArguments: Option[TypeArguments],
    argumentList: Option[ArgumentList]
) extends ExplicitConstructorInvocation

case class QualifiedSuperConstructorInvocation(
    primary: Primary,
    typeArguments: Option[TypeArguments],
    argumentList: Option[ArgumentList]
) extends ExplicitConstructorInvocation
