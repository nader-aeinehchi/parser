package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait RelationalOperation

object RelationalOperation {
  case object LessThan extends RelationalOperation           // <
  case object GreaterThan extends RelationalOperation        // >
  case object LessThanOrEqual extends RelationalOperation    // <=
  case object GreaterThanOrEqual extends RelationalOperation // >=
  case object InstanceOf extends RelationalOperation         // instanceof
}