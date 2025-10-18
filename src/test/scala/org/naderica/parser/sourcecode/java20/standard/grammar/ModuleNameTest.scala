package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ModuleNameTest extends AnyFunSuite with Matchers {

  test("ModuleName should be created with a list of identifiers") {
    // Given
    val identifiers = List(Identifier("com"), Identifier("example"), Identifier("mymodule"))

    // When
    val moduleName = ModuleName(identifiers)

    // Then
    moduleName.identifiers shouldBe identifiers
    moduleName.identifiers should have size 3
    moduleName.identifiers.head.name shouldBe "com"
    moduleName.identifiers(1).name shouldBe "example"
    moduleName.identifiers.last.name shouldBe "mymodule"
  }

  test("ModuleName should handle empty list of identifiers") {
    // Given
    val emptyIdentifiers = List.empty[Identifier]

    // When
    val moduleName = ModuleName(emptyIdentifiers)

    // Then
    moduleName.identifiers shouldBe empty
  }

  test("ModuleName should handle single identifier") {
    // Given
    val singleIdentifier = List(Identifier("base"))

    // When
    val moduleName = ModuleName(singleIdentifier)

    // Then
    moduleName.identifiers should have size 1
    moduleName.identifiers.head.name shouldBe "base"
  }

  test("ModuleName should support equality comparison") {
    // Given
    val identifiers1 = List(Identifier("java"), Identifier("base"))
    val identifiers2 = List(Identifier("java"), Identifier("base"))
    val identifiers3 = List(Identifier("java"), Identifier("desktop"))
    
    val moduleName1 = ModuleName(identifiers1)
    val moduleName2 = ModuleName(identifiers2)
    val moduleName3 = ModuleName(identifiers3)

    // When & Then
    moduleName1 shouldBe moduleName2
    moduleName1 should not be moduleName3
    moduleName1.hashCode() shouldBe moduleName2.hashCode()
  }

  test("ModuleName should handle standard Java module names") {
    // Given
    val javaBaseModule = List(Identifier("java"), Identifier("base"))
    val javaDesktopModule = List(Identifier("java"), Identifier("desktop"))
    val javaLoggingModule = List(Identifier("java"), Identifier("logging"))

    // When
    val baseModule = ModuleName(javaBaseModule)
    val desktopModule = ModuleName(javaDesktopModule)
    val loggingModule = ModuleName(javaLoggingModule)

    // Then
    baseModule.identifiers.map(_.name) shouldBe List("java", "base")
    desktopModule.identifiers.map(_.name) shouldBe List("java", "desktop")
    loggingModule.identifiers.map(_.name) shouldBe List("java", "logging")
  }

  test("ModuleName should handle complex module hierarchy") {
    // Given
    val complexHierarchy = List(
      Identifier("com"),
      Identifier("company"),
      Identifier("department"),
      Identifier("team"),
      Identifier("project"),
      Identifier("module")
    )

    // When
    val moduleName = ModuleName(complexHierarchy)

    // Then
    moduleName.identifiers should have size 6
    moduleName.identifiers.map(_.name) shouldBe List("com", "company", "department", "team", "project", "module")
  }

  test("ModuleName should have proper toString representation") {
    // Given
    val identifiers = List(Identifier("java"), Identifier("sql"))
    val moduleName = ModuleName(identifiers)

    // When
    val stringRepresentation = moduleName.toString

    // Then
    stringRepresentation should include("ModuleName")
    stringRepresentation should include("java")
    stringRepresentation should include("sql")
  }

  test("ModuleName case class should support pattern matching") {
    // Given
    val identifiers = List(Identifier("javafx"), Identifier("controls"))
    val moduleName = ModuleName(identifiers)

    // When & Then
    moduleName match {
      case ModuleName(ids) =>
        ids should have size 2
        ids.head.name shouldBe "javafx"
        ids.last.name shouldBe "controls"
    }
  }

  test("ModuleName should work with copy method") {
    // Given
    val original = ModuleName(List(Identifier("original")))
    val newIdentifiers = List(Identifier("modified"), Identifier("module"))

    // When
    val copied = original.copy(identifiers = newIdentifiers)

    // Then
    original.identifiers should have size 1
    copied.identifiers should have size 2
    original.identifiers.head.name shouldBe "original"
    copied.identifiers.head.name shouldBe "modified"
    original should not be copied
  }

  test("ModuleName should handle module names with special characters") {
    // Given
    val specialIdentifiers = List(Identifier("my_module"), Identifier("version$2"))

    // When
    val moduleName = ModuleName(specialIdentifiers)

    // Then
    moduleName.identifiers.head.name shouldBe "my_module"
    moduleName.identifiers.last.name shouldBe "version$2"
  }

  test("ModuleName should handle automatic module names") {
    // Given - Automatic modules often have single names derived from JAR names
    val automaticModuleName = List(Identifier("commons-lang3"))

    // When
    val moduleName = ModuleName(automaticModuleName)

    // Then
    moduleName.identifiers should have size 1
    moduleName.identifiers.head.name shouldBe "commons-lang3"
  }
}