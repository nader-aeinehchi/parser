package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypeNameListTest extends AnyFunSuite with Matchers {

  test("TypeNameList should be created with a list of TypeNames") {
    // Given
    val typeName1 = TypeName(TypeIdentifier("String"), None)
    val typeName2 = TypeName(TypeIdentifier("Integer"), None)
    val typeNames = List(typeName1, typeName2)

    // When
    val typeNameList = TypeNameList(typeNames)

    // Then
    typeNameList.typeNames shouldBe typeNames
    typeNameList.typeNames should have size 2
    typeNameList.typeNames.head.typeIdentifier.name shouldBe "String"
    typeNameList.typeNames.last.typeIdentifier.name shouldBe "Integer"
  }

  test("TypeNameList should handle empty list") {
    // Given
    val emptyTypeNames = List.empty[TypeName]

    // When
    val typeNameList = TypeNameList(emptyTypeNames)

    // Then
    typeNameList.typeNames shouldBe empty
  }

  test("TypeNameList should handle single TypeName") {
    // Given
    val singleTypeName = List(TypeName(TypeIdentifier("Boolean"), None))

    // When
    val typeNameList = TypeNameList(singleTypeName)

    // Then
    typeNameList.typeNames should have size 1
    typeNameList.typeNames.head.typeIdentifier.name shouldBe "Boolean"
  }

  test("TypeNameList should support equality comparison") {
    // Given
    val typeName1 = TypeName(TypeIdentifier("List"), None)
    val typeName2 = TypeName(TypeIdentifier("Map"), None)
    
    val typeNameList1 = TypeNameList(List(typeName1, typeName2))
    val typeNameList2 = TypeNameList(List(typeName1, typeName2))
    val typeNameList3 = TypeNameList(List(typeName1))

    // When & Then
    typeNameList1 shouldBe typeNameList2
    typeNameList1 should not be typeNameList3
    typeNameList1.hashCode() shouldBe typeNameList2.hashCode()
  }

  test("TypeNameList should handle primitive types") {
    // Given
    val primitiveTypeNames = List(
      TypeName(TypeIdentifier("int"), None),
      TypeName(TypeIdentifier("double"), None),
      TypeName(TypeIdentifier("boolean"), None)
    )

    // When
    val typeNameList = TypeNameList(primitiveTypeNames)

    // Then
    typeNameList.typeNames should have size 3
    typeNameList.typeNames.map(_.typeIdentifier.name) shouldBe List("int", "double", "boolean")
  }

  test("TypeNameList should handle reference types") {
    // Given
    val referenceTypeNames = List(
      TypeName(TypeIdentifier("String"), None),
      TypeName(TypeIdentifier("Object"), None),
      TypeName(TypeIdentifier("Thread"), None)
    )

    // When
    val typeNameList = TypeNameList(referenceTypeNames)

    // Then
    typeNameList.typeNames should have size 3
    typeNameList.typeNames.map(_.typeIdentifier.name) shouldBe List("String", "Object", "Thread")
  }

  test("TypeNameList should handle qualified type names") {
    // Given
    val javaLangString = TypeName(TypeIdentifier("String"), 
      Some(PackageOrTypeName(Identifier("lang"), Some(PackageOrTypeName(Identifier("java"), None)))))
    val javaUtilList = TypeName(TypeIdentifier("List"), 
      Some(PackageOrTypeName(Identifier("util"), Some(PackageOrTypeName(Identifier("java"), None)))))
    
    val qualifiedTypeNames = List(javaLangString, javaUtilList)

    // When
    val typeNameList = TypeNameList(qualifiedTypeNames)

    // Then
    typeNameList.typeNames should have size 2
    typeNameList.typeNames.head.typeIdentifier.name shouldBe "String"
    typeNameList.typeNames.head.packageOrTypeName.get.identifier.name shouldBe "lang"
    typeNameList.typeNames.last.typeIdentifier.name shouldBe "List"
    typeNameList.typeNames.last.packageOrTypeName.get.identifier.name shouldBe "util"
  }

  test("TypeNameList should have proper toString representation") {
    // Given
    val typeNames = List(
      TypeName(TypeIdentifier("ArrayList"), None),
      TypeName(TypeIdentifier("HashMap"), None)
    )
    val typeNameList = TypeNameList(typeNames)

    // When
    val stringRepresentation = typeNameList.toString

    // Then
    stringRepresentation should include("TypeNameList")
    stringRepresentation should include("ArrayList")
    stringRepresentation should include("HashMap")
  }

  test("TypeNameList case class should support pattern matching") {
    // Given
    val typeNames = List(
      TypeName(TypeIdentifier("Set"), None),
      TypeName(TypeIdentifier("Queue"), None)
    )
    val typeNameList = TypeNameList(typeNames)

    // When & Then
    typeNameList match {
      case TypeNameList(names) =>
        names should have size 2
        names.head.typeIdentifier.name shouldBe "Set"
        names.last.typeIdentifier.name shouldBe "Queue"
    }
  }

  test("TypeNameList should work with copy method") {
    // Given
    val original = TypeNameList(List(TypeName(TypeIdentifier("Original"), None)))
    val newTypeNames = List(
      TypeName(TypeIdentifier("Modified1"), None),
      TypeName(TypeIdentifier("Modified2"), None)
    )

    // When
    val copied = original.copy(typeNames = newTypeNames)

    // Then
    original.typeNames should have size 1
    copied.typeNames should have size 2
    original.typeNames.head.typeIdentifier.name shouldBe "Original"
    copied.typeNames.head.typeIdentifier.name shouldBe "Modified1"
    copied.typeNames.last.typeIdentifier.name shouldBe "Modified2"
    original should not be copied
  }

  test("TypeNameList should handle mixed qualified and unqualified types") {
    // Given
    val unqualifiedType = TypeName(TypeIdentifier("String"), None)
    val qualifiedType = TypeName(TypeIdentifier("List"), 
      Some(PackageOrTypeName(Identifier("util"), None)))
    val mixedTypeNames = List(unqualifiedType, qualifiedType)

    // When
    val typeNameList = TypeNameList(mixedTypeNames)

    // Then
    typeNameList.typeNames should have size 2
    typeNameList.typeNames.head.packageOrTypeName shouldBe None
    typeNameList.typeNames.last.packageOrTypeName shouldBe defined
  }

  test("TypeNameList should handle large lists") {
    // Given
    val largeList = (1 to 100).map(i => TypeName(TypeIdentifier(s"Type$i"), None)).toList

    // When
    val typeNameList = TypeNameList(largeList)

    // Then
    typeNameList.typeNames should have size 100
    typeNameList.typeNames.head.typeIdentifier.name shouldBe "Type1"
    typeNameList.typeNames.last.typeIdentifier.name shouldBe "Type100"
  }

  test("TypeNameList should preserve order of type names") {
    // Given
    val orderedTypeNames = List(
      TypeName(TypeIdentifier("First"), None),
      TypeName(TypeIdentifier("Second"), None),
      TypeName(TypeIdentifier("Third"), None)
    )

    // When
    val typeNameList = TypeNameList(orderedTypeNames)

    // Then
    typeNameList.typeNames.map(_.typeIdentifier.name) shouldBe List("First", "Second", "Third")
  }
}