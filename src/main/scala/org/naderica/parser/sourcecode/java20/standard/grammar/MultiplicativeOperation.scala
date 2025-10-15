package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait MultiplicativeOperation
case class Multiplication(unaryExpression: UnaryExpression)
    extends MultiplicativeOperation
case class Division(unaryExpression: UnaryExpression)
    extends MultiplicativeOperation
case class Modulo(unaryExpression: UnaryExpression)
    extends MultiplicativeOperation
