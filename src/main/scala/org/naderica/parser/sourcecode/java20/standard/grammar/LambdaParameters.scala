package org.naderica.parser.sourcecode.java20.standard.grammar

// Lambda expressions
case class LambdaParameters(
    identifier: Option[Identifier],
    formalParameterList: Option[FormalParameterList],
    lambdaParameterList: Option[LambdaParameterList]
)
