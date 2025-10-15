package org.naderica.parser.sourcecode.java20.standard.grammar

case class CastExpression(
    primitiveType: Option[PrimitiveType],
    referenceType: Option[ReferenceType],
    additionalBounds: List[AdditionalBound],
    unaryExpression: UnaryExpression
)
