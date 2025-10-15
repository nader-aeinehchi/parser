package org.naderica.parser.sourcecode.java20.standard.grammar

// Statement variations with NoShortIf
sealed trait StatementNoShortIf
case class LabeledStatementNoShortIf(
    identifier: Identifier,
    statementNoShortIf: StatementNoShortIf
) extends StatementNoShortIf
case class IfThenElseStatementNoShortIf(
    expression: Expression,
    statementNoShortIf1: StatementNoShortIf,
    statementNoShortIf2: StatementNoShortIf
) extends StatementNoShortIf
case class WhileStatementNoShortIf(
    expression: Expression,
    statementNoShortIf: StatementNoShortIf
) extends StatementNoShortIf
case class ForStatementNoShortIf(
    basicForStatementNoShortIf: Option[BasicForStatementNoShortIf],
    enhancedForStatementNoShortIf: Option[EnhancedForStatementNoShortIf]
) extends StatementNoShortIf

case class StatementWithoutTrailingSubstatement(
    block: Option[Block],
    emptyStatement: Option[EmptyStatement_.type],
    expressionStatement: Option[ExpressionStatement],
    assertStatement: Option[AssertStatement],
    switchStatement: Option[SwitchStatement],
    doStatement: Option[DoStatement],
    breakStatement: Option[BreakStatement],
    continueStatement: Option[ContinueStatement],
    returnStatement: Option[ReturnStatement],
    synchronizedStatement: Option[SynchronizedStatement],
    throwStatement: Option[ThrowStatement],
    tryStatement: Option[TryStatement],
    yieldStatement: Option[YieldStatement]
) extends StatementNoShortIf
