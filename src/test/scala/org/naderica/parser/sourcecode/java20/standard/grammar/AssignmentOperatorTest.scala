package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AssignmentOperatorTest extends AnyFunSuite with Matchers {

  test("AssignmentOperator should have all assignment operators") {
    // Given & When
    val operators = AssignmentOperator.values.toList

    // Then
    operators should contain(AssignmentOperator.Assign)
    operators should contain(AssignmentOperator.PlusAssign)
    operators should contain(AssignmentOperator.MinusAssign)
    operators should contain(AssignmentOperator.MultiplyAssign)
    operators should contain(AssignmentOperator.DivideAssign)
    operators should contain(AssignmentOperator.ModuloAssign)
    operators should contain(AssignmentOperator.LeftShiftAssign)
    operators should contain(AssignmentOperator.RightShiftAssign)
    operators should contain(AssignmentOperator.UnsignedRightShiftAssign)
    operators should contain(AssignmentOperator.BitwiseAndAssign)
    operators should contain(AssignmentOperator.BitwiseXorAssign)
    operators should contain(AssignmentOperator.BitwiseOrAssign)
  }

  test("AssignmentOperator should have exactly 12 operators") {
    // Given & When
    val operators = AssignmentOperator.values

    // Then
    operators should have size 12
  }

  test("AssignmentOperator should support equality comparison") {
    // Given
    val assign1 = AssignmentOperator.Assign
    val assign2 = AssignmentOperator.Assign
    val plusAssign = AssignmentOperator.PlusAssign

    // When & Then
    assign1 shouldBe assign2
    assign1 should not be plusAssign
    assign1.hashCode() shouldBe assign2.hashCode()
  }

  test("AssignmentOperator should have proper toString representation") {
    // Given
    val operators = List(
      AssignmentOperator.Assign,
      AssignmentOperator.PlusAssign,
      AssignmentOperator.MinusAssign,
      AssignmentOperator.MultiplyAssign,
      AssignmentOperator.DivideAssign,
      AssignmentOperator.ModuloAssign
    )

    // When & Then
    operators.foreach { operator =>
      val operatorString = operator.toString
      operatorString should not be empty
      operatorString should include(operator.productPrefix)
    }
  }

  test("AssignmentOperator should support pattern matching") {
    // Given
    val operators = AssignmentOperator.values.toList

    // When & Then
    operators.foreach { operator =>
      val result = operator match {
        case AssignmentOperator.Assign => "="
        case AssignmentOperator.PlusAssign => "+="
        case AssignmentOperator.MinusAssign => "-="
        case AssignmentOperator.MultiplyAssign => "*="
        case AssignmentOperator.DivideAssign => "/="
        case AssignmentOperator.ModuloAssign => "%="
        case AssignmentOperator.LeftShiftAssign => "<<="
        case AssignmentOperator.RightShiftAssign => ">>="
        case AssignmentOperator.UnsignedRightShiftAssign => ">>>="
        case AssignmentOperator.BitwiseAndAssign => "&="
        case AssignmentOperator.BitwiseXorAssign => "^="
        case AssignmentOperator.BitwiseOrAssign => "|="
      }
      result should not be empty
    }
  }

  test("AssignmentOperator should handle simple assignment operators") {
    // Given
    val basicOperators = List(
      AssignmentOperator.Assign,
      AssignmentOperator.PlusAssign,
      AssignmentOperator.MinusAssign,
      AssignmentOperator.MultiplyAssign,
      AssignmentOperator.DivideAssign,
      AssignmentOperator.ModuloAssign
    )

    // When & Then
    basicOperators should have size 6
    basicOperators should contain(AssignmentOperator.Assign)
    basicOperators should contain(AssignmentOperator.PlusAssign)
    basicOperators should contain(AssignmentOperator.MinusAssign)
  }

  test("AssignmentOperator should handle shift assignment operators") {
    // Given
    val shiftOperators = List(
      AssignmentOperator.LeftShiftAssign,
      AssignmentOperator.RightShiftAssign,
      AssignmentOperator.UnsignedRightShiftAssign
    )

    // When & Then
    shiftOperators should have size 3
    shiftOperators should contain(AssignmentOperator.LeftShiftAssign)
    shiftOperators should contain(AssignmentOperator.RightShiftAssign)
    shiftOperators should contain(AssignmentOperator.UnsignedRightShiftAssign)
  }

  test("AssignmentOperator should handle bitwise assignment operators") {
    // Given
    val bitwiseOperators = List(
      AssignmentOperator.BitwiseAndAssign,
      AssignmentOperator.BitwiseXorAssign,
      AssignmentOperator.BitwiseOrAssign
    )

    // When & Then
    bitwiseOperators should have size 3
    bitwiseOperators should contain(AssignmentOperator.BitwiseAndAssign)
    bitwiseOperators should contain(AssignmentOperator.BitwiseXorAssign)
    bitwiseOperators should contain(AssignmentOperator.BitwiseOrAssign)
  }

  test("AssignmentOperator should support type checking") {
    // Given
    val operator = AssignmentOperator.Assign

    // When & Then
    operator.isInstanceOf[AssignmentOperator] shouldBe true
    operator shouldBe a[AssignmentOperator]
  }

  test("AssignmentOperator should handle collections") {
    // Given
    val operators = List(
      AssignmentOperator.Assign,
      AssignmentOperator.PlusAssign,
      AssignmentOperator.Assign,
      AssignmentOperator.MinusAssign
    )

    // When
    val assignCount = operators.count(_ == AssignmentOperator.Assign)
    val uniqueOperators = operators.toSet

    // Then
    assignCount shouldBe 2
    uniqueOperators should have size 3
  }

  test("AssignmentOperator should represent Java assignment operators") {
    // Given - Java assignment operators: =, +=, -=, *=, /=, %=, <<=, >>=, >>>=, &=, ^=, |=
    val javaOperatorSymbols = Map(
      AssignmentOperator.Assign -> "=",
      AssignmentOperator.PlusAssign -> "+=",
      AssignmentOperator.MinusAssign -> "-=",
      AssignmentOperator.MultiplyAssign -> "*=",
      AssignmentOperator.DivideAssign -> "/=",
      AssignmentOperator.ModuloAssign -> "%=",
      AssignmentOperator.LeftShiftAssign -> "<<=",
      AssignmentOperator.RightShiftAssign -> ">>=",
      AssignmentOperator.UnsignedRightShiftAssign -> ">>>=",
      AssignmentOperator.BitwiseAndAssign -> "&=",
      AssignmentOperator.BitwiseXorAssign -> "^=",
      AssignmentOperator.BitwiseOrAssign -> "|="
    )

    // When & Then
    javaOperatorSymbols should have size 12
    javaOperatorSymbols.keys should contain allElementsOf AssignmentOperator.values
    
    // Verify each operator maps to expected symbol
    javaOperatorSymbols(AssignmentOperator.Assign) shouldBe "="
    javaOperatorSymbols(AssignmentOperator.PlusAssign) shouldBe "+="
    javaOperatorSymbols(AssignmentOperator.UnsignedRightShiftAssign) shouldBe ">>>="
  }

  test("AssignmentOperator should handle ordinal values") {
    // Given
    val operators = AssignmentOperator.values

    // When & Then
    operators.zipWithIndex.foreach { case (operator, index) =>
      operator.ordinal shouldBe index
    }
  }

  test("AssignmentOperator should support filter operations") {
    // Given
    val allOperators = AssignmentOperator.values.toList

    // When
    val arithmeticOperators = allOperators.filter { op =>
      op == AssignmentOperator.PlusAssign ||
      op == AssignmentOperator.MinusAssign ||
      op == AssignmentOperator.MultiplyAssign ||
      op == AssignmentOperator.DivideAssign ||
      op == AssignmentOperator.ModuloAssign
    }

    // Then
    arithmeticOperators should have size 5
    arithmeticOperators should contain(AssignmentOperator.PlusAssign)
    arithmeticOperators should contain(AssignmentOperator.ModuloAssign)
  }
}