package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PackageNameTest extends AnyFunSuite with Matchers {

  test("PackageName should be created with a list of identifiers") {
    // Given
    val identifiers = List(Identifier("com"), Identifier("example"), Identifier("myapp"))

    // When
    val packageName = PackageName(identifiers)

    // Then
    packageName.identifiers shouldBe identifiers
    packageName.identifiers should have size 3
    packageName.identifiers.head.name shouldBe "com"
    packageName.identifiers(1).name shouldBe "example"
    packageName.identifiers.last.name shouldBe "myapp"
  }

  test("PackageName should handle empty list of identifiers") {
    // Given
    val emptyIdentifiers = List.empty[Identifier]

    // When
    val packageName = PackageName(emptyIdentifiers)

    // Then
    packageName.identifiers shouldBe empty
  }

  test("PackageName should handle single identifier") {
    // Given
    val singleIdentifier = List(Identifier("java"))

    // When
    val packageName = PackageName(singleIdentifier)

    // Then
    packageName.identifiers should have size 1
    packageName.identifiers.head.name shouldBe "java"
  }

  test("PackageName should support equality comparison") {
    // Given
    val identifiers1 = List(Identifier("org"), Identifier("example"))
    val identifiers2 = List(Identifier("org"), Identifier("example"))
    val identifiers3 = List(Identifier("com"), Identifier("example"))
    
    val packageName1 = PackageName(identifiers1)
    val packageName2 = PackageName(identifiers2)
    val packageName3 = PackageName(identifiers3)

    // When & Then
    packageName1 shouldBe packageName2
    packageName1 should not be packageName3
    packageName1.hashCode() shouldBe packageName2.hashCode()
  }

  test("PackageName should handle deep package hierarchy") {
    // Given
    val deepHierarchy = List(
      Identifier("com"),
      Identifier("company"),
      Identifier("department"),
      Identifier("team"),
      Identifier("project"),
      Identifier("module")
    )

    // When
    val packageName = PackageName(deepHierarchy)

    // Then
    packageName.identifiers should have size 6
    packageName.identifiers.map(_.name) shouldBe List("com", "company", "department", "team", "project", "module")
  }

  test("PackageName should have proper toString representation") {
    // Given
    val identifiers = List(Identifier("java"), Identifier("util"))
    val packageName = PackageName(identifiers)

    // When
    val stringRepresentation = packageName.toString

    // Then
    stringRepresentation should include("PackageName")
    stringRepresentation should include("java")
    stringRepresentation should include("util")
  }

  test("PackageName case class should support pattern matching") {
    // Given
    val identifiers = List(Identifier("scala"), Identifier("collection"))
    val packageName = PackageName(identifiers)

    // When & Then
    packageName match {
      case PackageName(ids) =>
        ids should have size 2
        ids.head.name shouldBe "scala"
        ids.last.name shouldBe "collection"
    }
  }

  test("PackageName should work with copy method") {
    // Given
    val original = PackageName(List(Identifier("original")))
    val newIdentifiers = List(Identifier("modified"), Identifier("package"))

    // When
    val copied = original.copy(identifiers = newIdentifiers)

    // Then
    original.identifiers should have size 1
    copied.identifiers should have size 2
    original.identifiers.head.name shouldBe "original"
    copied.identifiers.head.name shouldBe "modified"
    original should not be copied
  }

  test("PackageName should handle identifiers with special characters") {
    // Given
    val specialIdentifiers = List(Identifier("_internal"), Identifier("$special"))

    // When
    val packageName = PackageName(specialIdentifiers)

    // Then
    packageName.identifiers.head.name shouldBe "_internal"
    packageName.identifiers.last.name shouldBe "$special"
  }
}