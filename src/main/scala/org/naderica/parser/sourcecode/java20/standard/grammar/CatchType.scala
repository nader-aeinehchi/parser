package org.naderica.parser.sourcecode.java20.standard.grammar

case class CatchType(
    unannClassType: UnannClassType,
    additionalTypes: List[UnannClassType]
)
