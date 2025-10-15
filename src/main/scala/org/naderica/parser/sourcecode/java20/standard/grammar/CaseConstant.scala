package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait CaseConstant
case class ExpressionCaseConstant(
    conditionalExpression: ConditionalExpression
) extends CaseConstant
case class PatternCaseConstant(pattern: Pattern) extends CaseConstant
