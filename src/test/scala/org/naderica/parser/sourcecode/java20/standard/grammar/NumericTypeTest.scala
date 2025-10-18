package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class NumericTypeTest extends AnyFunSuite with Matchers {

  test("NumericType should be created with integral type") {
    // Given
    val integralType = Some("int")
    val floatingPointType = None

    // When
    val numericType = NumericType(integralType, floatingPointType)

    // Then
    numericType.integralType shouldBe Some("int")
    numericType.floatingPointType shouldBe None
  }

  test("NumericType should be created with floating point type") {
    // Given
    val integralType = None
    val floatingPointType = Some("double")

    // When
    val numericType = NumericType(integralType, floatingPointType)

    // Then
    numericType.integralType shouldBe None
    numericType.floatingPointType shouldBe Some("double")
  }

  test("NumericType should support all integral types") {
    // Given
    val integralTypes = List("byte", "short", "int", "long", "char")

    // When & Then
    integralTypes.foreach { intType =>
      val numericType = NumericType(Some(intType), None)
      numericType.integralType shouldBe Some(intType)
      numericType.floatingPointType shouldBe None
    }
  }

  test("NumericType should support all floating point types") {
    // Given
    val floatingTypes = List("float", "double")

    // When & Then
    floatingTypes.foreach { floatType =>
      val numericType = NumericType(None, Some(floatType))
      numericType.integralType shouldBe None
      numericType.floatingPointType shouldBe Some(floatType)
    }
  }

  test("NumericType should handle both types as None") {
    // Given
    val integralType = None
    val floatingPointType = None

    // When
    val numericType = NumericType(integralType, floatingPointType)

    // Then
    numericType.integralType shouldBe None
    numericType.floatingPointType shouldBe None
  }

  test("NumericType should support equality comparison") {
    // Given
    val numericType1 = NumericType(Some("int"), None)
    val numericType2 = NumericType(Some("int"), None)
    val numericType3 = NumericType(Some("long"), None)
    val numericType4 = NumericType(None, Some("double"))

    // When & Then
    numericType1 shouldBe numericType2
    numericType1 should not be numericType3
    numericType1 should not be numericType4
    numericType1.hashCode() shouldBe numericType2.hashCode()
  }

  test("NumericType should have proper toString representation") {
    // Given
    val intType = NumericType(Some("int"), None)
    val doubleType = NumericType(None, Some("double"))

    // When
    val intStringRepresentation = intType.toString
    val doubleStringRepresentation = doubleType.toString

    // Then
    intStringRepresentation should include("NumericType")
    intStringRepresentation should include("int")
    doubleStringRepresentation should include("NumericType")
    doubleStringRepresentation should include("double")
  }

  test("NumericType case class should support pattern matching") {
    // Given
    val intType = NumericType(Some("long"), None)
    val floatType = NumericType(None, Some("float"))

    // When & Then
    intType match {
      case NumericType(Some(integral), None) =>
        integral shouldBe "long"
      case _ =>
        fail("Pattern matching failed for integral type")
    }

    floatType match {
      case NumericType(None, Some(floating)) =>
        floating shouldBe "float"
      case _ =>
        fail("Pattern matching failed for floating type")
    }
  }

  test("NumericType should work with copy method") {
    // Given
    val original = NumericType(Some("byte"), None)

    // When
    val modifiedToFloat = original.copy(integralType = None, floatingPointType = Some("float"))
    val modifiedToLong = original.copy(integralType = Some("long"))

    // Then
    original.integralType shouldBe Some("byte")
    original.floatingPointType shouldBe None

    modifiedToFloat.integralType shouldBe None
    modifiedToFloat.floatingPointType shouldBe Some("float")

    modifiedToLong.integralType shouldBe Some("long")
    modifiedToLong.floatingPointType shouldBe None
  }

  test("NumericType should handle char as integral type") {
    // Given - char is technically an integral type in Java
    val charType = NumericType(Some("char"), None)

    // When & Then
    charType.integralType shouldBe Some("char")
    charType.floatingPointType shouldBe None
  }

  test("NumericType should distinguish between different numeric types") {
    // Given
    val byteType = NumericType(Some("byte"), None)
    val shortType = NumericType(Some("short"), None)
    val intType = NumericType(Some("int"), None)
    val longType = NumericType(Some("long"), None)
    val floatType = NumericType(None, Some("float"))
    val doubleType = NumericType(None, Some("double"))

    // When & Then
    val allTypes = List(byteType, shortType, intType, longType, floatType, doubleType)
    allTypes.foreach { numType1 =>
      allTypes.foreach { numType2 =>
        if (numType1 == numType2) {
          numType1 shouldBe numType2
        } else {
          numType1 should not be numType2
        }
      }
    }
  }

  test("NumericType should handle invalid discriminated union case") {
    // Given - This represents an invalid state where both are Some
    val invalidType = NumericType(Some("int"), Some("double"))

    // When & Then - This should be allowed by the type system but semantically invalid
    invalidType.integralType shouldBe Some("int")
    invalidType.floatingPointType shouldBe Some("double")
  }
}