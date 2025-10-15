package org.naderica.parser.sourcecode.java20.standard.grammar

// Expressions - following the exact precedence structure from parser
sealed trait Expression
case class LambdaExpression(
    lambdaParameters: LambdaParameters,
    lambdaBody: LambdaBody
) extends Expression

case class AssignmentExpression(
    conditionalExpression: Option[ConditionalExpression],
    assignment: Option[Assignment]
) extends Expression

case class Assignment(
    leftHandSide: LeftHandSide,
    assignmentOperator: AssignmentOperator,
    expression: Expression
)

case class ConditionalExpression(
    conditionalOrExpression: ConditionalOrExpression,
    trueExpression: Option[Expression],
    falseExpression: Option[ConditionalExpression]
)

//   case class ConditionalExpression(text: String) // Simplified for now

case class ConditionalOrExpression(
    conditionalAndExpression: ConditionalAndExpression,
    additionalExpressions: List[ConditionalAndExpression]
)

case class ConditionalAndExpression(
    inclusiveOrExpression: InclusiveOrExpression,
    additionalExpressions: List[InclusiveOrExpression]
)

case class InclusiveOrExpression(
    exclusiveOrExpression: ExclusiveOrExpression,
    additionalExpressions: List[ExclusiveOrExpression]
)

case class ExclusiveOrExpression(
    andExpression: AndExpression,
    additionalExpressions: List[AndExpression]
)

case class AndExpression(
    equalityExpression: EqualityExpression,
    additionalExpressions: List[EqualityExpression]
)

case class EqualityExpression(
    relationalExpression: RelationalExpression,
    additionalComparisons: List[
      (String, RelationalExpression)
    ] // "==" or "!="
)

case class RelationalExpression(
    shiftExpression: ShiftExpression,
    additionalComparisons: List[RelationalComparison]
)
