package org.naderica.parser.sourcecode.java20.standard.grammar

// Switch constructs
case class SwitchBlock(
    switchRules: List[SwitchRule],
    switchBlockStatementGroups: List[SwitchBlockStatementGroup]
)
