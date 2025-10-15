package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ForInit
case class LocalVariableForInit(
    localVariableDeclaration: LocalVariableDeclaration
) extends ForInit
case class StatementExpressionListForInit(
    statementExpressionList: StatementExpressionList
) extends ForInit
