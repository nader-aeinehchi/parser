package org.naderica.parser.sourcecode.java20.standard.grammar

case class RecordComponentList(
    recordComponents: List[RecordComponent],
    variableArityRecordComponent: Option[VariableArityRecordComponent]
)
