package org.naderica.parser.sourcecode.java20.standard.grammar

case class BasicForStatementNoShortIf(
    forInit: Option[ForInit],
    expression: Option[Expression],
    forUpdate: Option[ForUpdate],
    statementNoShortIf: StatementNoShortIf
)
