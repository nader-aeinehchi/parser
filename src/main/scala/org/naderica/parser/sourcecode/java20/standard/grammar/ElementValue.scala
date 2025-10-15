package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ElementValue
case class ConditionalElementValue(
    conditionalExpression: ConditionalExpression
) extends ElementValue
case class ElementValueArrayInitializer(
    elementValueList: Option[ElementValueList]
) extends ElementValue
case class AnnotationElementValue(annotation: Annotation) extends ElementValue
case class ElementValueList(elementValues: List[ElementValue])
case class ConditionalExpressionElementValue(
    conditionalExpression: ConditionalExpression
) extends ElementValue
