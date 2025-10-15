package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ForUpdate
case class StatementExpressionListForUpdate(
    statementExpressionList: StatementExpressionList
) extends ForUpdate
