package org.naderica.parser.sourcecode.java20.standard.grammar

case class MethodInvocation(
    methodName: Option[MethodName],
    typeName: Option[TypeName],
    expressionName: Option[ExpressionName],
    primary: Option[Primary],
    super_ : Option[String], // "super"
    typeArguments: Option[TypeArguments],
    unqualifiedMethodIdentifier: UnqualifiedMethodIdentifier,
    argumentList: Option[ArgumentList]
)
