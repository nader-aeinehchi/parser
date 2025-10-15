package org.naderica.parser.sourcecode.java20.standard.grammar

case class SwitchRule(
    switchLabel: SwitchLabel,
    expression: Option[Expression],
    block: Option[Block],
    throwStatement: Option[ThrowStatement]
)
