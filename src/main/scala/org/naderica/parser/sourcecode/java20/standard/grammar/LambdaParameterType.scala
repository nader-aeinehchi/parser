package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait LambdaParameterType
case class UnannLambdaParameterType(unannType: UnannType)
    extends LambdaParameterType
case object VarLambdaParameterType extends LambdaParameterType
