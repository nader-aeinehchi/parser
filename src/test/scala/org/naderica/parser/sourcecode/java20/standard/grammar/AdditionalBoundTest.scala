package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AdditionalBoundTest extends AnyFunSuite with Matchers {

  test("AdditionalBound should be created with an InterfaceType") {
    // Given
    val typeIdentifier = TypeIdentifier("Serializable")
    val classType = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType = InterfaceType(classType)

    // When
    val additionalBound = AdditionalBound(interfaceType)

    // Then
    additionalBound.interfaceType shouldBe interfaceType
    additionalBound.interfaceType.classType shouldBe classType
    additionalBound.interfaceType.classType.typeIdentifier shouldBe typeIdentifier
    additionalBound.interfaceType.classType.typeIdentifier.name shouldBe "Serializable"
  }

  test("AdditionalBound should support equality comparison") {
    // Given
    val typeIdentifier1 = TypeIdentifier("Comparable")
    val classType1 = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier1,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType1 = InterfaceType(classType1)
    val additionalBound1 = AdditionalBound(interfaceType1)

    val typeIdentifier2 = TypeIdentifier("Comparable")
    val classType2 = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier2,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType2 = InterfaceType(classType2)
    val additionalBound2 = AdditionalBound(interfaceType2)

    // When & Then
    additionalBound1 shouldBe additionalBound2
    additionalBound1.hashCode() shouldBe additionalBound2.hashCode()
  }

  test("AdditionalBound should support different interface types") {
    // Given
    val typeIdentifier1 = TypeIdentifier("Serializable")
    val classType1 = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier1,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType1 = InterfaceType(classType1)
    val additionalBound1 = AdditionalBound(interfaceType1)

    val typeIdentifier2 = TypeIdentifier("Comparable")
    val classType2 = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier2,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType2 = InterfaceType(classType2)
    val additionalBound2 = AdditionalBound(interfaceType2)

    // When & Then
    additionalBound1 should not be additionalBound2
    additionalBound1.interfaceType.classType.typeIdentifier.name shouldBe "Serializable"
    additionalBound2.interfaceType.classType.typeIdentifier.name shouldBe "Comparable"
  }

  test("AdditionalBound should have proper toString representation") {
    // Given
    val typeIdentifier = TypeIdentifier("AutoCloseable")
    val classType = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType = InterfaceType(classType)
    val additionalBound = AdditionalBound(interfaceType)

    // When
    val stringRepresentation = additionalBound.toString

    // Then
    stringRepresentation should include("AdditionalBound")
    stringRepresentation should include("AutoCloseable")
  }

  test("AdditionalBound should be constructible with complex InterfaceType") {
    // Given - Testing with annotations and type arguments (if TypeArguments exists)
    val typeIdentifier = TypeIdentifier("List")
    val classType = ClassType(
      annotations = List.empty, // Could be populated with actual annotations
      typeIdentifier = typeIdentifier,
      typeArguments = None, // Could be Some(TypeArguments(...)) if available
      nestedClassType = None
    )
    val interfaceType = InterfaceType(classType)

    // When
    val additionalBound = AdditionalBound(interfaceType)

    // Then
    additionalBound.interfaceType shouldBe interfaceType
    additionalBound.interfaceType.classType.annotations shouldBe empty
    additionalBound.interfaceType.classType.typeArguments shouldBe None
    additionalBound.interfaceType.classType.nestedClassType shouldBe None
  }

  test("AdditionalBound case class should support pattern matching") {
    // Given
    val typeIdentifier = TypeIdentifier("Cloneable")
    val classType = ClassType(
      annotations = List.empty,
      typeIdentifier = typeIdentifier,
      typeArguments = None,
      nestedClassType = None
    )
    val interfaceType = InterfaceType(classType)
    val additionalBound = AdditionalBound(interfaceType)

    // When & Then
    additionalBound match {
      case AdditionalBound(InterfaceType(ClassType(_, TypeIdentifier(name), _, _))) =>
        name shouldBe "Cloneable"
      case _ =>
        fail("Pattern matching failed")
    }
  }
}