package org.naderica.parser.sourcecode.java20.standard.grammar

case class ConstructorBody(
    explicitConstructorInvocation: Option[ExplicitConstructorInvocation],
    blockStatements: Option[BlockStatements]
)
