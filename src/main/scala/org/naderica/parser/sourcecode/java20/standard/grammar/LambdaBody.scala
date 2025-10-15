package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait LambdaBody
case class ExpressionLambdaBody(expression: Expression) extends LambdaBody
case class BlockLambdaBody(block: Block) extends LambdaBody
