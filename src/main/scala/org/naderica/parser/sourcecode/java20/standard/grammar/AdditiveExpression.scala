package org.naderica.parser.sourcecode.java20.standard.grammar

case class AdditiveExpression(
    multiplicativeExpression: MultiplicativeExpression,
    operations: List[AdditiveOperation]
)
