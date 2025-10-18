package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FloatingPointTypeTest extends AnyFunSuite with Matchers {

  test("FloatingPointType should be created with a type name") {
    // Given
    val typeName = "double"

    // When
    val floatingPointType = FloatingPointType(typeName)

    // Then
    floatingPointType.typeName shouldBe "double"
  }

  test("FloatingPointType should support both floating point types") {
    // Given
    val floatingTypes = List("float", "double")

    // When & Then
    floatingTypes.foreach { floatType =>
      val floatingPointType = FloatingPointType(floatType)
      floatingPointType.typeName shouldBe floatType
    }
  }

  test("FloatingPointType should support equality comparison") {
    // Given
    val floatingPointType1 = FloatingPointType("float")
    val floatingPointType2 = FloatingPointType("float")
    val floatingPointType3 = FloatingPointType("double")

    // When & Then
    floatingPointType1 shouldBe floatingPointType2
    floatingPointType1 should not be floatingPointType3
    floatingPointType1.hashCode() shouldBe floatingPointType2.hashCode()
  }

  test("FloatingPointType should handle float type") {
    // Given
    val floatType = FloatingPointType("float")

    // When & Then
    floatType.typeName shouldBe "float"
  }

  test("FloatingPointType should handle double type") {
    // Given
    val doubleType = FloatingPointType("double")

    // When & Then
    doubleType.typeName shouldBe "double"
  }

  test("FloatingPointType should have proper toString representation") {
    // Given
    val floatingPointType = FloatingPointType("double")

    // When
    val stringRepresentation = floatingPointType.toString

    // Then
    stringRepresentation should include("FloatingPointType")
    stringRepresentation should include("double")
  }

  test("FloatingPointType case class should support pattern matching") {
    // Given
    val floatingPointType = FloatingPointType("float")

    // When & Then
    floatingPointType match {
      case FloatingPointType(typeName) =>
        typeName shouldBe "float"
    }
  }

  test("FloatingPointType should work with copy method") {
    // Given
    val original = FloatingPointType("float")

    // When
    val copied = original.copy(typeName = "double")

    // Then
    original.typeName shouldBe "float"
    copied.typeName shouldBe "double"
    original should not be copied
  }

  test("FloatingPointType should handle empty string") {
    // Given
    val emptyType = FloatingPointType("")

    // When & Then
    emptyType.typeName shouldBe ""
  }

  test("FloatingPointType should handle null type name") {
    // Given & When
    val nullType = FloatingPointType(null)

    // Then
    nullType.typeName shouldBe null
  }

  test("FloatingPointType should distinguish between float and double") {
    // Given
    val floatType = FloatingPointType("float")
    val doubleType = FloatingPointType("double")

    // When & Then
    floatType should not be doubleType
    floatType.typeName shouldBe "float"
    doubleType.typeName shouldBe "double"
  }

  test("FloatingPointType should handle case sensitivity") {
    // Given
    val lowerCaseType = FloatingPointType("double")
    val upperCaseType = FloatingPointType("DOUBLE")

    // When & Then
    lowerCaseType should not be upperCaseType
    lowerCaseType.typeName shouldBe "double"
    upperCaseType.typeName shouldBe "DOUBLE"
  }

  test("FloatingPointType should handle precision differences") {
    // Given - float is 32-bit, double is 64-bit
    val floatType = FloatingPointType("float")
    val doubleType = FloatingPointType("double")

    // When & Then
    floatType.typeName shouldBe "float" // 32-bit precision
    doubleType.typeName shouldBe "double" // 64-bit precision
    floatType should not be doubleType
  }
}