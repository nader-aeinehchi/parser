package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypeIdentifierTest extends AnyFunSuite with Matchers {

  test("TypeIdentifier should be created with a name") {
    // Given
    val name = "String"

    // When
    val typeIdentifier = TypeIdentifier(name)

    // Then
    typeIdentifier.name shouldBe "String"
  }

  test("TypeIdentifier should support equality comparison") {
    // Given
    val typeIdentifier1 = TypeIdentifier("Integer")
    val typeIdentifier2 = TypeIdentifier("Integer")
    val typeIdentifier3 = TypeIdentifier("Boolean")

    // When & Then
    typeIdentifier1 shouldBe typeIdentifier2
    typeIdentifier1 should not be typeIdentifier3
    typeIdentifier1.hashCode() shouldBe typeIdentifier2.hashCode()
  }

  test("TypeIdentifier should handle primitive type names") {
    // Given
    val primitiveTypes = List("int", "double", "boolean", "char", "byte", "short", "long", "float")

    // When & Then
    primitiveTypes.foreach { primitiveType =>
      val typeIdentifier = TypeIdentifier(primitiveType)
      typeIdentifier.name shouldBe primitiveType
    }
  }

  test("TypeIdentifier should handle generic type names") {
    // Given
    val genericTypeName = "List"

    // When
    val typeIdentifier = TypeIdentifier(genericTypeName)

    // Then
    typeIdentifier.name shouldBe "List"
  }

  test("TypeIdentifier should handle nested class names") {
    // Given
    val nestedClassName = "OuterClass$InnerClass"

    // When
    val typeIdentifier = TypeIdentifier(nestedClassName)

    // Then
    typeIdentifier.name shouldBe "OuterClass$InnerClass"
  }

  test("TypeIdentifier should handle array type indicators") {
    // Given
    val arrayTypeName = "String[]"

    // When
    val typeIdentifier = TypeIdentifier(arrayTypeName)

    // Then
    typeIdentifier.name shouldBe "String[]"
  }

  test("TypeIdentifier should handle empty string") {
    // Given
    val emptyName = ""

    // When
    val typeIdentifier = TypeIdentifier(emptyName)

    // Then
    typeIdentifier.name shouldBe ""
  }

  test("TypeIdentifier should have proper toString representation") {
    // Given
    val typeIdentifier = TypeIdentifier("MyCustomType")

    // When
    val stringRepresentation = typeIdentifier.toString

    // Then
    stringRepresentation should include("TypeIdentifier")
    stringRepresentation should include("MyCustomType")
  }

  test("TypeIdentifier case class should support pattern matching") {
    // Given
    val typeIdentifier = TypeIdentifier("PatternType")

    // When & Then
    typeIdentifier match {
      case TypeIdentifier(name) =>
        name shouldBe "PatternType"
    }
  }

  test("TypeIdentifier should handle null as name") {
    // Given & When
    val typeIdentifier = TypeIdentifier(null)

    // Then
    typeIdentifier.name shouldBe null
  }

  test("TypeIdentifier should work with copy method") {
    // Given
    val original = TypeIdentifier("OriginalType")

    // When
    val copied = original.copy(name = "ModifiedType")

    // Then
    original.name shouldBe "OriginalType"
    copied.name shouldBe "ModifiedType"
    original should not be copied
  }
}