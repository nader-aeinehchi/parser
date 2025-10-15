package org.naderica.parser.sourcecode.java20.standard.grammar

case class AmbiguousName(
    identifier: Identifier,
    ambiguousName: Option[AmbiguousName]
)
