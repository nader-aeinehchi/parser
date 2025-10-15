package org.naderica.parser.sourcecode.java20.standard.grammar

case class VariableDeclarator(
    variableDeclaratorId: VariableDeclaratorId,
    variableInitializer: Option[VariableInitializer]
)
