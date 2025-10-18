package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AmbiguousNameTest extends AnyFunSuite with Matchers {

  test("AmbiguousName should be created with identifier and no nested name") {
    // Given
    val identifier = Identifier("variable")
    val ambiguousName = None

    // When
    val ambiguous = AmbiguousName(identifier, ambiguousName)

    // Then
    ambiguous.identifier shouldBe identifier
    ambiguous.ambiguousName shouldBe None
    ambiguous.identifier.name shouldBe "variable"
  }

  test("AmbiguousName should be created with nested ambiguous name") {
    // Given
    val innerIdentifier = Identifier("field")
    val innerAmbiguous = AmbiguousName(innerIdentifier, None)
    val outerIdentifier = Identifier("object")
    val outerAmbiguous = AmbiguousName(outerIdentifier, Some(innerAmbiguous))

    // When & Then
    outerAmbiguous.identifier.name shouldBe "object"
    outerAmbiguous.ambiguousName shouldBe Some(innerAmbiguous)
    outerAmbiguous.ambiguousName.get.identifier.name shouldBe "field"
    outerAmbiguous.ambiguousName.get.ambiguousName shouldBe None
  }

  test("AmbiguousName should support equality comparison") {
    // Given
    val identifier1 = Identifier("name")
    val identifier2 = Identifier("name")
    val identifier3 = Identifier("other")
    
    val ambiguous1 = AmbiguousName(identifier1, None)
    val ambiguous2 = AmbiguousName(identifier2, None)
    val ambiguous3 = AmbiguousName(identifier3, None)

    // When & Then
    ambiguous1 shouldBe ambiguous2
    ambiguous1 should not be ambiguous3
    ambiguous1.hashCode() shouldBe ambiguous2.hashCode()
  }

  test("AmbiguousName should handle deep nesting") {
    // Given - Creating a.b.c.d structure
    val dId = Identifier("d")
    val dAmbiguous = AmbiguousName(dId, None)
    
    val cId = Identifier("c")
    val cAmbiguous = AmbiguousName(cId, Some(dAmbiguous))
    
    val bId = Identifier("b")
    val bAmbiguous = AmbiguousName(bId, Some(cAmbiguous))
    
    val aId = Identifier("a")
    val aAmbiguous = AmbiguousName(aId, Some(bAmbiguous))

    // When & Then
    aAmbiguous.identifier.name shouldBe "a"
    aAmbiguous.ambiguousName.get.identifier.name shouldBe "b"
    aAmbiguous.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "c"
    aAmbiguous.ambiguousName.get.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "d"
    aAmbiguous.ambiguousName.get.ambiguousName.get.ambiguousName.get.ambiguousName shouldBe None
  }

  test("AmbiguousName should handle simple variable names") {
    // Given
    val variableNames = List("x", "count", "result", "data", "index")

    // When & Then
    variableNames.foreach { varName =>
      val ambiguous = AmbiguousName(Identifier(varName), None)
      ambiguous.identifier.name shouldBe varName
      ambiguous.ambiguousName shouldBe None
    }
  }

  test("AmbiguousName should handle qualified field access") {
    // Given - this.field
    val fieldId = Identifier("field")
    val fieldAmbiguous = AmbiguousName(fieldId, None)
    val thisId = Identifier("this")
    val thisAmbiguous = AmbiguousName(thisId, Some(fieldAmbiguous))

    // When & Then
    thisAmbiguous.identifier.name shouldBe "this"
    thisAmbiguous.ambiguousName.get.identifier.name shouldBe "field"
  }

  test("AmbiguousName should have proper toString representation") {
    // Given
    val ambiguous = AmbiguousName(Identifier("testName"), None)

    // When
    val stringRepresentation = ambiguous.toString

    // Then
    stringRepresentation should include("AmbiguousName")
    stringRepresentation should include("testName")
  }

  test("AmbiguousName case class should support pattern matching") {
    // Given
    val innerAmbiguous = AmbiguousName(Identifier("inner"), None)
    val outerAmbiguous = AmbiguousName(Identifier("outer"), Some(innerAmbiguous))

    // When & Then
    outerAmbiguous match {
      case AmbiguousName(Identifier(name), Some(AmbiguousName(Identifier(innerName), None))) =>
        name shouldBe "outer"
        innerName shouldBe "inner"
      case _ =>
        fail("Pattern matching failed")
    }
  }

  test("AmbiguousName should work with copy method") {
    // Given
    val original = AmbiguousName(Identifier("original"), None)
    val newIdentifier = Identifier("modified")
    val nestedAmbiguous = AmbiguousName(Identifier("nested"), None)

    // When
    val copiedIdentifier = original.copy(identifier = newIdentifier)
    val copiedWithNested = original.copy(ambiguousName = Some(nestedAmbiguous))

    // Then
    original.identifier.name shouldBe "original"
    original.ambiguousName shouldBe None

    copiedIdentifier.identifier.name shouldBe "modified"
    copiedIdentifier.ambiguousName shouldBe None

    copiedWithNested.identifier.name shouldBe "original"
    copiedWithNested.ambiguousName.get.identifier.name shouldBe "nested"
  }

  test("AmbiguousName should handle package-like structures") {
    // Given - com.example.MyClass (could be ambiguous)
    val classId = Identifier("MyClass")
    val classAmbiguous = AmbiguousName(classId, None)
    
    val exampleId = Identifier("example")
    val exampleAmbiguous = AmbiguousName(exampleId, Some(classAmbiguous))
    
    val comId = Identifier("com")
    val comAmbiguous = AmbiguousName(comId, Some(exampleAmbiguous))

    // When & Then
    comAmbiguous.identifier.name shouldBe "com"
    comAmbiguous.ambiguousName.get.identifier.name shouldBe "example"
    comAmbiguous.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "MyClass"
  }

  test("AmbiguousName should distinguish between different structures") {
    // Given
    val simple = AmbiguousName(Identifier("variable"), None)
    val qualified = AmbiguousName(Identifier("object"), Some(AmbiguousName(Identifier("field"), None)))

    // When & Then
    simple should not be qualified
    simple.identifier.name shouldBe "variable"
    simple.ambiguousName shouldBe None
    
    qualified.identifier.name shouldBe "object"
    qualified.ambiguousName.get.identifier.name shouldBe "field"
  }

  test("AmbiguousName should handle recursive self-reference patterns") {
    // Given - Testing the recursive Option[AmbiguousName] structure
    val level3 = AmbiguousName(Identifier("level3"), None)
    val level2 = AmbiguousName(Identifier("level2"), Some(level3))
    val level1 = AmbiguousName(Identifier("level1"), Some(level2))

    // When & Then
    level1.identifier.name shouldBe "level1"
    level1.ambiguousName.get.identifier.name shouldBe "level2"
    level1.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "level3"
    level1.ambiguousName.get.ambiguousName.get.ambiguousName shouldBe None
  }
}