package org.naderica.parser.sourcecode.java20.standard.grammar

// Enum specific
case class EnumBody(
    enumConstantList: Option[EnumConstantList],
    enumBodyDeclarations: Option[EnumBodyDeclarations]
)
