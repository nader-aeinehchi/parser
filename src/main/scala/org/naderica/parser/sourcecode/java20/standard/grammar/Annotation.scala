package org.naderica.parser.sourcecode.java20.standard.grammar

// Annotations
sealed trait Annotation
case class NormalAnnotation(
    typeName: TypeName,
    elementValuePairList: Option[ElementValuePairList]
) extends Annotation

case class MarkerAnnotation(typeName: TypeName) extends Annotation

case class SingleElementAnnotation(
    typeName: TypeName,
    elementValue: ElementValue
) extends Annotation
