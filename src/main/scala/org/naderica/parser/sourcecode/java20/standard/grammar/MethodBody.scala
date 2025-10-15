package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait MethodBody
case class BlockMethodBody(block: Block) extends MethodBody
case object EmptyMethodBody extends MethodBody
