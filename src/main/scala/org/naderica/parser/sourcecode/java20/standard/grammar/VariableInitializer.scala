package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait VariableInitializer
case class ExpressionVariableInitializer(expression: Expression)
    extends VariableInitializer
case class ArrayInitializer(
    variableInitializerList: Option[VariableInitializerList]
) extends VariableInitializer

case class VariableInitializerList(
    variableInitializers: List[VariableInitializer]
)
