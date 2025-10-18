package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class UnqualifiedMethodIdentifierTest extends AnyFunSuite with Matchers {

  test("UnqualifiedMethodIdentifier should be created with a name") {
    // Given
    val name = "methodName"

    // When
    val identifier = UnqualifiedMethodIdentifier(name)

    // Then
    identifier.name shouldBe "methodName"
  }

  test("UnqualifiedMethodIdentifier should support equality comparison") {
    // Given
    val identifier1 = UnqualifiedMethodIdentifier("equals")
    val identifier2 = UnqualifiedMethodIdentifier("equals")
    val identifier3 = UnqualifiedMethodIdentifier("hashCode")

    // When & Then
    identifier1 shouldBe identifier2
    identifier1 should not be identifier3
    identifier1.hashCode() shouldBe identifier2.hashCode()
  }

  test("UnqualifiedMethodIdentifier should handle empty string") {
    // Given
    val emptyName = ""

    // When
    val identifier = UnqualifiedMethodIdentifier(emptyName)

    // Then
    identifier.name shouldBe ""
  }

  test("UnqualifiedMethodIdentifier should handle special method names") {
    // Given
    val specialMethods = List("<init>", "<clinit>")

    // When & Then
    specialMethods.foreach { method =>
      val identifier = UnqualifiedMethodIdentifier(method)
      identifier.name shouldBe method
    }
  }

  test("UnqualifiedMethodIdentifier should handle camelCase names") {
    // Given
    val camelCaseName = "calculateTotalAmount"

    // When
    val identifier = UnqualifiedMethodIdentifier(camelCaseName)

    // Then
    identifier.name shouldBe "calculateTotalAmount"
  }

  test("UnqualifiedMethodIdentifier should handle names with underscores") {
    // Given
    val underscoreName = "process_data_batch"

    // When
    val identifier = UnqualifiedMethodIdentifier(underscoreName)

    // Then
    identifier.name shouldBe "process_data_batch"
  }

  test("UnqualifiedMethodIdentifier should handle names with numbers") {
    // Given
    val nameWithNumbers = "method123"

    // When
    val identifier = UnqualifiedMethodIdentifier(nameWithNumbers)

    // Then
    identifier.name shouldBe "method123"
  }

  test("UnqualifiedMethodIdentifier should handle names with dollar signs") {
    // Given
    val nameWithDollar = "lambda$main$0"

    // When
    val identifier = UnqualifiedMethodIdentifier(nameWithDollar)

    // Then
    identifier.name shouldBe "lambda$main$0"
  }

  test("UnqualifiedMethodIdentifier should have proper toString representation") {
    // Given
    val identifier = UnqualifiedMethodIdentifier("testMethod")

    // When
    val stringRepresentation = identifier.toString

    // Then
    stringRepresentation should include("UnqualifiedMethodIdentifier")
    stringRepresentation should include("testMethod")
  }

  test("UnqualifiedMethodIdentifier case class should support pattern matching") {
    // Given
    val identifier = UnqualifiedMethodIdentifier("patternMatch")

    // When & Then
    identifier match {
      case UnqualifiedMethodIdentifier(name) =>
        name shouldBe "patternMatch"
    }
  }

  test("UnqualifiedMethodIdentifier should handle null as name") {
    // Given & When
    val identifier = UnqualifiedMethodIdentifier(null)

    // Then
    identifier.name shouldBe null
  }

  test("UnqualifiedMethodIdentifier should work with copy method") {
    // Given
    val original = UnqualifiedMethodIdentifier("original")

    // When
    val copied = original.copy(name = "modified")

    // Then
    original.name shouldBe "original"
    copied.name shouldBe "modified"
    original should not be copied
  }

  test("UnqualifiedMethodIdentifier should handle single character names") {
    // Given
    val singleChar = "x"

    // When
    val identifier = UnqualifiedMethodIdentifier(singleChar)

    // Then
    identifier.name shouldBe "x"
  }

  test("UnqualifiedMethodIdentifier should handle long method names") {
    // Given
    val longMethodName = "thisIsAVeryLongMethodNameThatSomeoneDecidedToCreateForSomeReason"

    // When
    val identifier = UnqualifiedMethodIdentifier(longMethodName)

    // Then
    identifier.name shouldBe longMethodName
  }
}