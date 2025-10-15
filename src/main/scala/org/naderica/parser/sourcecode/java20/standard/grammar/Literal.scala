package org.naderica.parser.sourcecode.java20.standard.grammar

// Literals
sealed trait Literal
case class IntegerLiteral(value: String) extends Literal
case class FloatingPointLiteral(value: String) extends Literal
case class BooleanLiteral(value: Boolean) extends Literal
case class CharacterLiteral(value: String) extends Literal
case class StringLiteral(value: String) extends Literal
case class TextBlock(value: String) extends Literal
case object NullLiteral extends Literal
