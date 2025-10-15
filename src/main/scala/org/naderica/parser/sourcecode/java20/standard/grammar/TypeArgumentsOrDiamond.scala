package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait TypeArgumentsOrDiamond
case class TypeArgumentsOrDiamondWithArgs(typeArguments: TypeArguments)
    extends TypeArgumentsOrDiamond
case object DiamondOperator extends TypeArgumentsOrDiamond
