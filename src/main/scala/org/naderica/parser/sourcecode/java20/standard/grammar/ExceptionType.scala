package org.naderica.parser.sourcecode.java20.standard.grammar

case class ExceptionType(
    classType: Option[ClassType],
    typeVariable: Option[TypeVariable]
)
