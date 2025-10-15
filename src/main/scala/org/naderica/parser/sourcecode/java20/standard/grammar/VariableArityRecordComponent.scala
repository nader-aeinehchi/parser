package org.naderica.parser.sourcecode.java20.standard.grammar

case class VariableArityRecordComponent(
    recordComponentModifiers: List[RecordComponentModifier],
    unannType: UnannType,
    annotations: List[Annotation],
    identifier: Identifier
)
