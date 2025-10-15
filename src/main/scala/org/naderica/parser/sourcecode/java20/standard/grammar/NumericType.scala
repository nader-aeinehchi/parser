package org.naderica.parser.sourcecode.java20.standard.grammar

// Primitive types
case class NumericType(
    integralType: Option[String],
    floatingPointType: Option[String]
)
