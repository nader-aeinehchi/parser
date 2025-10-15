package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait Resource
case class LocalVariableResource(
    variableModifiers: List[VariableModifier],
    localVariableType: LocalVariableType,
    identifier: Identifier,
    expression: Expression
) extends Resource

case class VariableAccessResource(variableAccess: VariableAccess)
    extends Resource
