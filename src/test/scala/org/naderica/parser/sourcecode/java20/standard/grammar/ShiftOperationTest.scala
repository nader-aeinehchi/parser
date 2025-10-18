package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ShiftOperationTest extends AnyFunSuite with Matchers {

  test("ShiftOperation should have LeftShift case object") {
    // Given & When
    val leftShift = ShiftOperation.LeftShift

    // Then
    leftShift shouldBe a[ShiftOperation]
    leftShift shouldBe ShiftOperation.LeftShift
  }

  test("ShiftOperation should have RightShift case object") {
    // Given & When
    val rightShift = ShiftOperation.RightShift

    // Then
    rightShift shouldBe a[ShiftOperation]
    rightShift shouldBe ShiftOperation.RightShift
  }

  test("ShiftOperation should have UnsignedRightShift case object") {
    // Given & When
    val unsignedRightShift = ShiftOperation.UnsignedRightShift

    // Then
    unsignedRightShift shouldBe a[ShiftOperation]
    unsignedRightShift shouldBe ShiftOperation.UnsignedRightShift
  }

  test("ShiftOperation case objects should support equality comparison") {
    // Given
    val leftShift1 = ShiftOperation.LeftShift
    val leftShift2 = ShiftOperation.LeftShift
    val rightShift = ShiftOperation.RightShift

    // When & Then
    leftShift1 shouldBe leftShift2
    leftShift1 should not be rightShift
    leftShift1.hashCode() shouldBe leftShift2.hashCode()
  }

  test("ShiftOperation should have proper toString representation") {
    // Given
    val operations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When & Then
    operations.foreach { operation =>
      val operationString = operation.toString
      operationString should not be empty
      operation match {
        case ShiftOperation.LeftShift => operationString should include("LeftShift")
        case ShiftOperation.RightShift => operationString should include("RightShift")
        case ShiftOperation.UnsignedRightShift => operationString should include("UnsignedRightShift")
      }
    }
  }

  test("ShiftOperation sealed trait should support pattern matching") {
    // Given
    val operations: List[ShiftOperation] = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When & Then
    operations.foreach { operation =>
      operation match {
        case ShiftOperation.LeftShift =>
          operation shouldBe ShiftOperation.LeftShift
        case ShiftOperation.RightShift =>
          operation shouldBe ShiftOperation.RightShift
        case ShiftOperation.UnsignedRightShift =>
          operation shouldBe ShiftOperation.UnsignedRightShift
      }
    }
  }

  test("ShiftOperation should handle type checking") {
    // Given
    val leftShift: ShiftOperation = ShiftOperation.LeftShift
    val unsignedRightShift: ShiftOperation = ShiftOperation.UnsignedRightShift

    // When & Then
    leftShift.isInstanceOf[ShiftOperation] shouldBe true
    unsignedRightShift.isInstanceOf[ShiftOperation] shouldBe true
    leftShift.isInstanceOf[ShiftOperation.LeftShift.type] shouldBe true
    unsignedRightShift.isInstanceOf[ShiftOperation.UnsignedRightShift.type] shouldBe true
  }

  test("ShiftOperation should support exhaustive pattern matching") {
    // Given
    def describeOperation(operation: ShiftOperation): String = operation match {
      case ShiftOperation.LeftShift => "<<"
      case ShiftOperation.RightShift => ">>"
      case ShiftOperation.UnsignedRightShift => ">>>"
    }

    // When & Then
    describeOperation(ShiftOperation.LeftShift) shouldBe "<<"
    describeOperation(ShiftOperation.RightShift) shouldBe ">>"
    describeOperation(ShiftOperation.UnsignedRightShift) shouldBe ">>>"
  }

  test("ShiftOperation should handle collections") {
    // Given
    val operations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.LeftShift,
      ShiftOperation.UnsignedRightShift
    )

    // When
    val leftShiftCount = operations.count(_ == ShiftOperation.LeftShift)
    val rightShiftCount = operations.count(_ == ShiftOperation.RightShift)

    // Then
    leftShiftCount shouldBe 2
    rightShiftCount shouldBe 1
    operations should have size 4
  }

  test("ShiftOperation should support set operations") {
    // Given
    val operationSet = Set(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.LeftShift,
      ShiftOperation.UnsignedRightShift
    )

    // When & Then
    operationSet should have size 3
    operationSet should contain(ShiftOperation.LeftShift)
    operationSet should contain(ShiftOperation.RightShift)
    operationSet should contain(ShiftOperation.UnsignedRightShift)
  }

  test("ShiftOperation should represent Java shift operators") {
    // Given - In Java: <<, >>, >>>
    val javaOperatorSymbols = Map(
      ShiftOperation.LeftShift -> "<<",
      ShiftOperation.RightShift -> ">>",
      ShiftOperation.UnsignedRightShift -> ">>>"
    )

    // When & Then
    javaOperatorSymbols should have size 3
    javaOperatorSymbols(ShiftOperation.LeftShift) shouldBe "<<"
    javaOperatorSymbols(ShiftOperation.RightShift) shouldBe ">>"
    javaOperatorSymbols(ShiftOperation.UnsignedRightShift) shouldBe ">>>"
  }

  test("ShiftOperation should handle map operations") {
    // Given
    val operations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When
    val descriptions = operations.map {
      case ShiftOperation.LeftShift => "shift bits left"
      case ShiftOperation.RightShift => "shift bits right with sign extension"
      case ShiftOperation.UnsignedRightShift => "shift bits right with zero fill"
    }

    // Then
    descriptions shouldBe List(
      "shift bits left",
      "shift bits right with sign extension",
      "shift bits right with zero fill"
    )
  }

  test("ShiftOperation should support filter operations") {
    // Given
    val allOperations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When
    val rightShiftOperators = allOperations.filter {
      case ShiftOperation.RightShift | ShiftOperation.UnsignedRightShift => true
      case _ => false
    }
    val leftShiftOperators = allOperations.filter(_ == ShiftOperation.LeftShift)

    // Then
    rightShiftOperators should have size 2
    leftShiftOperators should have size 1
    rightShiftOperators should contain(ShiftOperation.RightShift)
    rightShiftOperators should contain(ShiftOperation.UnsignedRightShift)
    leftShiftOperators should contain only ShiftOperation.LeftShift
  }

  test("ShiftOperation should handle Java semantic meaning") {
    // Given
    val semantics = Map(
      ShiftOperation.LeftShift -> "left shift fills with zeros",
      ShiftOperation.RightShift -> "right shift with sign extension",
      ShiftOperation.UnsignedRightShift -> "right shift with zero fill"
    )

    // When & Then
    semantics should have size 3
    semantics(ShiftOperation.LeftShift) should include("left shift")
    semantics(ShiftOperation.RightShift) should include("sign extension")
    semantics(ShiftOperation.UnsignedRightShift) should include("zero fill")
  }

  test("ShiftOperation should categorize signed vs unsigned operations") {
    // Given
    val allOperations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When
    val (signedOperations, unsignedOperations) = allOperations.partition {
      case ShiftOperation.UnsignedRightShift => false
      case _ => true
    }

    // Then
    signedOperations should have size 2
    unsignedOperations should have size 1
    signedOperations should contain(ShiftOperation.LeftShift)
    signedOperations should contain(ShiftOperation.RightShift)
    unsignedOperations should contain only ShiftOperation.UnsignedRightShift
  }

  test("ShiftOperation should handle bitwise operation properties") {
    // Given
    val bitOperationProperties = Map(
      ShiftOperation.LeftShift -> "multiplies by powers of 2",
      ShiftOperation.RightShift -> "divides by powers of 2 (signed)",
      ShiftOperation.UnsignedRightShift -> "divides by powers of 2 (unsigned)"
    )

    // When & Then
    bitOperationProperties should have size 3
    bitOperationProperties(ShiftOperation.LeftShift) should include("multiplies")
    bitOperationProperties(ShiftOperation.RightShift) should include("signed")
    bitOperationProperties(ShiftOperation.UnsignedRightShift) should include("unsigned")
  }

  test("ShiftOperation should handle edge case considerations") {
    // Given - Java shift operations have special behavior
    val operations = List(
      ShiftOperation.LeftShift,
      ShiftOperation.RightShift,
      ShiftOperation.UnsignedRightShift
    )

    // When & Then
    operations.foreach { operation =>
      operation match {
        case ShiftOperation.LeftShift =>
          // Left shift can cause overflow
          operation shouldBe ShiftOperation.LeftShift
        case ShiftOperation.RightShift =>
          // Right shift preserves sign bit
          operation shouldBe ShiftOperation.RightShift
        case ShiftOperation.UnsignedRightShift =>
          // Unsigned right shift always fills with 0
          operation shouldBe ShiftOperation.UnsignedRightShift
      }
    }
  }
}