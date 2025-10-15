package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait EqualityOperation

object EqualityOperation {
  case object Equal extends EqualityOperation // ==
  case object NotEqual extends EqualityOperation // !=
}
