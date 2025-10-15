// package org.naderica.parser.sourcecode.java20.standard.grammar

// sealed trait ShiftOperation
// case class LeftShift(additiveExpression: AdditiveExpression)
//     extends ShiftOperation
// case class RightShift(additiveExpression: AdditiveExpression)
//     extends ShiftOperation
// case class UnsignedRightShift(additiveExpression: AdditiveExpression)
//     extends ShiftOperation


package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ShiftOperation

object ShiftOperation {
  case object LeftShift extends ShiftOperation           // <<
  case object RightShift extends ShiftOperation          // >>
  case object UnsignedRightShift extends ShiftOperation  // >>>
}