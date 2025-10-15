package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait TypeArgument
case class ReferenceTypeArgument(referenceType: ReferenceType)
    extends TypeArgument
case class WildcardArgument(wildcard: Wildcard) extends TypeArgument

case class Wildcard(
    annotations: List[Annotation],
    wildcardBounds: Option[WildcardBounds]
)
