package org.naderica.parser.sourcecode.java20.standard.grammar

// Statements - following the exact structure from the parser
sealed trait Statement
case class LabeledStatement(identifier: Identifier, statement: Statement)
    extends Statement
case class ExpressionStatement(statementExpression: StatementExpression)
    extends Statement
case class IfThenStatement(expression: Expression, statement: Statement)
    extends Statement
case class IfThenElseStatement(
    expression: Expression,
    thenStatement: Statement,
    elseStatement: Statement
) extends Statement
case class AssertStatement(
    expression: Expression,
    assertMessage: Option[Expression]
) extends Statement
case class SwitchStatement(expression: Expression, switchBlock: SwitchBlock)
    extends Statement
case class WhileStatement(expression: Expression, statement: Statement)
    extends Statement
case class DoStatement(statement: Statement, expression: Expression)
    extends Statement
case class ForStatement(
    basicForStatement: Option[BasicForStatement],
    enhancedForStatement: Option[EnhancedForStatement]
) extends Statement
case class BreakStatement(identifier: Option[Identifier]) extends Statement
case class ContinueStatement(identifier: Option[Identifier]) extends Statement
case class ReturnStatement(expression: Option[Expression]) extends Statement
case class ThrowStatement(expression: Expression) extends Statement
case class SynchronizedStatement(expression: Expression, block: Block)
    extends Statement
case class TryStatement(
    block: Block,
    catches: Option[Catches],
    finallyBlock: Option[FinallyBlock],
    tryWithResourcesStatement: Option[TryWithResourcesStatement]
) extends Statement
case class YieldStatement(expression: Expression) extends Statement
case object EmptyStatement_ extends Statement
