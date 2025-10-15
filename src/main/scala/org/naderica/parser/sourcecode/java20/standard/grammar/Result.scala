package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait Result
case class TypeResult(unannType: UnannType) extends Result
case object VoidResult extends Result
