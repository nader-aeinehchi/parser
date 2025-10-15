package org.naderica.parser.sourcecode.java20.standard.grammar

// Unannotated types
sealed trait UnannType
case class UnannPrimitiveType(primitiveType: PrimitiveType) extends UnannType
case class UnannReferenceType(
    unannClassOrInterfaceType: Option[UnannClassOrInterfaceType],
    unannTypeVariable: Option[UnannTypeVariable],
    unannArrayType: Option[UnannArrayType]
) extends UnannType
