package org.naderica.parser.sourcecode.java20.standard.grammar

// Type system
sealed trait Type
case class PrimitiveType(primitiveTypeName: String) extends Type
case class ReferenceType(
    classOrInterfaceType: Option[ClassOrInterfaceType],
    typeVariable: Option[TypeVariable],
    arrayType: Option[ArrayType]
) extends Type
