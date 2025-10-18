package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class IntegralTypeTest extends AnyFunSuite with Matchers {

  test("IntegralType should be created with a type name") {
    // Given
    val typeName = "int"

    // When
    val integralType = IntegralType(typeName)

    // Then
    integralType.typeName shouldBe "int"
  }

  test("IntegralType should support all Java integral types") {
    // Given
    val integralTypes = List("byte", "short", "int", "long", "char")

    // When & Then
    integralTypes.foreach { intType =>
      val integralType = IntegralType(intType)
      integralType.typeName shouldBe intType
    }
  }

  test("IntegralType should support equality comparison") {
    // Given
    val integralType1 = IntegralType("long")
    val integralType2 = IntegralType("long")
    val integralType3 = IntegralType("int")

    // When & Then
    integralType1 shouldBe integralType2
    integralType1 should not be integralType3
    integralType1.hashCode() shouldBe integralType2.hashCode()
  }

  test("IntegralType should handle byte type") {
    // Given
    val byteType = IntegralType("byte")

    // When & Then
    byteType.typeName shouldBe "byte"
  }

  test("IntegralType should handle short type") {
    // Given
    val shortType = IntegralType("short")

    // When & Then
    shortType.typeName shouldBe "short"
  }

  test("IntegralType should handle int type") {
    // Given
    val intType = IntegralType("int")

    // When & Then
    intType.typeName shouldBe "int"
  }

  test("IntegralType should handle long type") {
    // Given
    val longType = IntegralType("long")

    // When & Then
    longType.typeName shouldBe "long"
  }

  test("IntegralType should handle char type") {
    // Given - char is an integral type in Java
    val charType = IntegralType("char")

    // When & Then
    charType.typeName shouldBe "char"
  }

  test("IntegralType should have proper toString representation") {
    // Given
    val integralType = IntegralType("long")

    // When
    val stringRepresentation = integralType.toString

    // Then
    stringRepresentation should include("IntegralType")
    stringRepresentation should include("long")
  }

  test("IntegralType case class should support pattern matching") {
    // Given
    val integralType = IntegralType("byte")

    // When & Then
    integralType match {
      case IntegralType(typeName) =>
        typeName shouldBe "byte"
    }
  }

  test("IntegralType should work with copy method") {
    // Given
    val original = IntegralType("int")

    // When
    val copied = original.copy(typeName = "long")

    // Then
    original.typeName shouldBe "int"
    copied.typeName shouldBe "long"
    original should not be copied
  }

  test("IntegralType should handle empty string") {
    // Given
    val emptyType = IntegralType("")

    // When & Then
    emptyType.typeName shouldBe ""
  }

  test("IntegralType should handle null type name") {
    // Given & When
    val nullType = IntegralType(null)

    // Then
    nullType.typeName shouldBe null
  }

  test("IntegralType should distinguish between all integral types") {
    // Given
    val byteType = IntegralType("byte")
    val shortType = IntegralType("short")
    val intType = IntegralType("int")
    val longType = IntegralType("long")
    val charType = IntegralType("char")

    // When & Then
    val allTypes = List(byteType, shortType, intType, longType, charType)
    allTypes.foreach { type1 =>
      allTypes.foreach { type2 =>
        if (type1.typeName == type2.typeName) {
          type1 shouldBe type2
        } else {
          type1 should not be type2
        }
      }
    }
  }

  test("IntegralType should handle case sensitivity") {
    // Given
    val lowerCaseType = IntegralType("int")
    val upperCaseType = IntegralType("INT")

    // When & Then
    lowerCaseType should not be upperCaseType
    lowerCaseType.typeName shouldBe "int"
    upperCaseType.typeName shouldBe "INT"
  }
}