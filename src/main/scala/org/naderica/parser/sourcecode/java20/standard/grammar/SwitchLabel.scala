package org.naderica.parser.sourcecode.java20.standard.grammar

case class SwitchLabel(
    caseConstants: Option[List[CaseConstant]],
    defaultLabel: Option[String] // "default"
)
