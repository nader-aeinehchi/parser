package org.naderica.parser.sourcecode.java20.standard.grammar

case class ShiftExpression(
    additiveExpression: AdditiveExpression,
    shifts: List[ShiftOperation]
)
