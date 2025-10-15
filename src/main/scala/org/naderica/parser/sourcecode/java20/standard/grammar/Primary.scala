package org.naderica.parser.sourcecode.java20.standard.grammar

// Primary expressions
sealed trait Primary
case class ArrayCreationExpression(
    arrayCreationExpressionWithoutInitializer: Option[
      ArrayCreationExpressionWithoutInitializer
    ],
    arrayCreationExpressionWithInitializer: Option[
      ArrayCreationExpressionWithInitializer
    ]
) extends Primary

case class PrimaryNoNewArray(pnna: PNNA) extends Primary
