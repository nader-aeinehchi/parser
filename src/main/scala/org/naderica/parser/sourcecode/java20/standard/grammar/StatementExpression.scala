package org.naderica.parser.sourcecode.java20.standard.grammar

case class StatementExpression(
    assignment: Option[Assignment],
    preIncrementExpression: Option[PreIncrementExpression],
    postIncrementExpression: Option[PostIncrementExpression],
    preDecrementExpression: Option[PreDecrementExpression],
    postDecrementExpression: Option[PostDecrementExpression],
    methodInvocation: Option[MethodInvocation],
    classInstanceCreationExpression: Option[ClassInstanceCreationExpression]
)
