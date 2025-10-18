package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SimpleTypeNameTest extends AnyFunSuite with Matchers {

  test("SimpleTypeName should be created with a TypeIdentifier") {
    // Given
    val typeIdentifier = TypeIdentifier("String")

    // When
    val simpleTypeName = SimpleTypeName(typeIdentifier)

    // Then
    simpleTypeName.typeIdentifier shouldBe typeIdentifier
    simpleTypeName.typeIdentifier.name shouldBe "String"
  }

  test("SimpleTypeName should support equality comparison") {
    // Given
    val typeId1 = TypeIdentifier("Integer")
    val typeId2 = TypeIdentifier("Integer")
    val typeId3 = TypeIdentifier("Boolean")
    
    val simpleTypeName1 = SimpleTypeName(typeId1)
    val simpleTypeName2 = SimpleTypeName(typeId2)
    val simpleTypeName3 = SimpleTypeName(typeId3)

    // When & Then
    simpleTypeName1 shouldBe simpleTypeName2
    simpleTypeName1 should not be simpleTypeName3
    simpleTypeName1.hashCode() shouldBe simpleTypeName2.hashCode()
  }

  test("SimpleTypeName should handle primitive types") {
    // Given
    val primitiveTypes = List("int", "double", "boolean", "char", "byte", "short", "long", "float")

    // When & Then
    primitiveTypes.foreach { primitiveType =>
      val typeIdentifier = TypeIdentifier(primitiveType)
      val simpleTypeName = SimpleTypeName(typeIdentifier)
      simpleTypeName.typeIdentifier.name shouldBe primitiveType
    }
  }

  test("SimpleTypeName should handle reference types") {
    // Given
    val referenceTypes = List("String", "Object", "List", "Map", "Set")

    // When & Then
    referenceTypes.foreach { refType =>
      val typeIdentifier = TypeIdentifier(refType)
      val simpleTypeName = SimpleTypeName(typeIdentifier)
      simpleTypeName.typeIdentifier.name shouldBe refType
    }
  }

  test("SimpleTypeName should handle generic type names") {
    // Given
    val genericTypeName = "T"
    val typeIdentifier = TypeIdentifier(genericTypeName)

    // When
    val simpleTypeName = SimpleTypeName(typeIdentifier)

    // Then
    simpleTypeName.typeIdentifier.name shouldBe "T"
  }

  test("SimpleTypeName should handle custom class names") {
    // Given
    val customClassName = "MyCustomClass"
    val typeIdentifier = TypeIdentifier(customClassName)

    // When
    val simpleTypeName = SimpleTypeName(typeIdentifier)

    // Then
    simpleTypeName.typeIdentifier.name shouldBe "MyCustomClass"
  }

  test("SimpleTypeName should have proper toString representation") {
    // Given
    val typeIdentifier = TypeIdentifier("LocalDateTime")
    val simpleTypeName = SimpleTypeName(typeIdentifier)

    // When
    val stringRepresentation = simpleTypeName.toString

    // Then
    stringRepresentation should include("SimpleTypeName")
    stringRepresentation should include("LocalDateTime")
  }

  test("SimpleTypeName case class should support pattern matching") {
    // Given
    val typeIdentifier = TypeIdentifier("Pattern")
    val simpleTypeName = SimpleTypeName(typeIdentifier)

    // When & Then
    simpleTypeName match {
      case SimpleTypeName(TypeIdentifier(name)) =>
        name shouldBe "Pattern"
    }
  }

  test("SimpleTypeName should work with copy method") {
    // Given
    val original = SimpleTypeName(TypeIdentifier("Original"))
    val newTypeIdentifier = TypeIdentifier("Modified")

    // When
    val copied = original.copy(typeIdentifier = newTypeIdentifier)

    // Then
    original.typeIdentifier.name shouldBe "Original"
    copied.typeIdentifier.name shouldBe "Modified"
    original should not be copied
  }

  test("SimpleTypeName should handle void type") {
    // Given
    val voidTypeIdentifier = TypeIdentifier("void")

    // When
    val simpleTypeName = SimpleTypeName(voidTypeIdentifier)

    // Then
    simpleTypeName.typeIdentifier.name shouldBe "void"
  }
}