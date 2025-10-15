package org.naderica.parser.sourcecode.java20.standard.grammar

case class ExpressionName(
    identifier: Identifier,
    ambiguousName: Option[AmbiguousName]
)
