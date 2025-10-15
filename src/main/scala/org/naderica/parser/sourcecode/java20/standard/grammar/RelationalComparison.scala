package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait RelationalComparison
case class LessThanComparison(shiftExpression: ShiftExpression)
    extends RelationalComparison
case class GreaterThanComparison(shiftExpression: ShiftExpression)
    extends RelationalComparison
case class LessEqualComparison(shiftExpression: ShiftExpression)
    extends RelationalComparison
case class GreaterEqualComparison(shiftExpression: ShiftExpression)
    extends RelationalComparison
case class InstanceOfComparison(referenceType: ReferenceType)
    extends RelationalComparison
