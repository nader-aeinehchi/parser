package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class IdentifierTest extends AnyFunSuite with Matchers {

  test("Identifier should be created with a name") {
    // Given
    val name = "myVariable"

    // When
    val identifier = Identifier(name)

    // Then
    identifier.name shouldBe "myVariable"
  }

  test("Identifier should support equality comparison") {
    // Given
    val identifier1 = Identifier("sameValue")
    val identifier2 = Identifier("sameValue")
    val identifier3 = Identifier("differentValue")

    // When & Then
    identifier1 shouldBe identifier2
    identifier1 should not be identifier3
    identifier1.hashCode() shouldBe identifier2.hashCode()
  }

  test("Identifier should handle empty string") {
    // Given
    val emptyName = ""

    // When
    val identifier = Identifier(emptyName)

    // Then
    identifier.name shouldBe ""
  }

  test("Identifier should handle special characters") {
    // Given
    val specialName = "_$validIdentifier123"

    // When
    val identifier = Identifier(specialName)

    // Then
    identifier.name shouldBe "_$validIdentifier123"
  }

  test("Identifier should handle unicode characters") {
    // Given
    val unicodeName = "변수명"

    // When
    val identifier = Identifier(unicodeName)

    // Then
    identifier.name shouldBe "변수명"
  }

  test("Identifier should have proper toString representation") {
    // Given
    val identifier = Identifier("testName")

    // When
    val stringRepresentation = identifier.toString

    // Then
    stringRepresentation should include("Identifier")
    stringRepresentation should include("testName")
  }

  test("Identifier case class should support pattern matching") {
    // Given
    val identifier = Identifier("patternMatch")

    // When & Then
    identifier match {
      case Identifier(name) =>
        name shouldBe "patternMatch"
      case _ =>
        fail("Pattern matching failed")
    }
  }

  test("Identifier should handle null as name") {
    // Given & When
    val identifier = Identifier(null)

    // Then
    identifier.name shouldBe null
  }

  test("Identifier should work with copy method") {
    // Given
    val original = Identifier("original")

    // When
    val copied = original.copy(name = "modified")

    // Then
    original.name shouldBe "original"
    copied.name shouldBe "modified"
    original should not be copied
  }
}