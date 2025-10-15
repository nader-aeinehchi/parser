package org.naderica.parser.sourcecode.java20.standard.grammar

case class FieldAccess(
    primary: Option[Primary],
    super_ : Option[String], // "super"
    typeName: Option[TypeName], // TypeName.super
    identifier: Identifier
)
