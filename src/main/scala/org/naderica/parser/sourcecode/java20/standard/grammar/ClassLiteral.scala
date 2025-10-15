package org.naderica.parser.sourcecode.java20.standard.grammar

case class ClassLiteral(
    typeName: Option[TypeName],
    numericType: Option[NumericType],
    boolean: Option[String], // "boolean"
    void: Option[String], // "void"
    dims: Option[Dims]
)
