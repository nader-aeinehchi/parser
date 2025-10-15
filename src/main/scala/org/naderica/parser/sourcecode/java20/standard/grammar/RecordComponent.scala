package org.naderica.parser.sourcecode.java20.standard.grammar

case class RecordComponent(
    recordComponentModifiers: List[RecordComponentModifier],
    unannType: UnannType,
    variableDeclaratorId: VariableDeclaratorId
)
