package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait LocalVariableType
case class UnannLocalVariableType(unannType: UnannType)
    extends LocalVariableType
case object VarLocalVariableType extends LocalVariableType
