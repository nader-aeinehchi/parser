package org.naderica.parser.sourcecode.java20.standard.grammar

enum AssignmentOperator:
  case Assign, PlusAssign, MinusAssign, MultiplyAssign, DivideAssign,
    ModuloAssign,
    LeftShiftAssign, RightShiftAssign, UnsignedRightShiftAssign,
    BitwiseAndAssign, BitwiseXorAssign, BitwiseOrAssign
