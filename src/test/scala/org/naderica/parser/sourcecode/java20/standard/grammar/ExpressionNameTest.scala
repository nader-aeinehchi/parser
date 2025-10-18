package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ExpressionNameTest extends AnyFunSuite with Matchers {

  test("ExpressionName should be created with identifier and no ambiguous name") {
    // Given
    val identifier = Identifier("variable")
    val ambiguousName = None

    // When
    val expressionName = ExpressionName(identifier, ambiguousName)

    // Then
    expressionName.identifier shouldBe identifier
    expressionName.ambiguousName shouldBe None
    expressionName.identifier.name shouldBe "variable"
  }

  test("ExpressionName should be created with identifier and ambiguous name") {
    // Given
    val identifier = Identifier("field")
    val ambiguousName = Some(AmbiguousName(Identifier("object"), None))

    // When
    val expressionName = ExpressionName(identifier, ambiguousName)

    // Then
    expressionName.identifier.name shouldBe "field"
    expressionName.ambiguousName shouldBe Some(ambiguousName.get)
    expressionName.ambiguousName.get.identifier.name shouldBe "object"
  }

  test("ExpressionName should support equality comparison") {
    // Given
    val identifier1 = Identifier("name")
    val identifier2 = Identifier("name")
    val identifier3 = Identifier("other")
    
    val expressionName1 = ExpressionName(identifier1, None)
    val expressionName2 = ExpressionName(identifier2, None)
    val expressionName3 = ExpressionName(identifier3, None)

    // When & Then
    expressionName1 shouldBe expressionName2
    expressionName1 should not be expressionName3
    expressionName1.hashCode() shouldBe expressionName2.hashCode()
  }

  test("ExpressionName should handle simple variable references") {
    // Given
    val variableNames = List("x", "y", "count", "result", "data", "index")

    // When & Then
    variableNames.foreach { varName =>
      val expressionName = ExpressionName(Identifier(varName), None)
      expressionName.identifier.name shouldBe varName
      expressionName.ambiguousName shouldBe None
    }
  }

  test("ExpressionName should handle qualified field access") {
    // Given - object.field
    val fieldIdentifier = Identifier("field")
    val objectAmbiguous = AmbiguousName(Identifier("object"), None)
    val expressionName = ExpressionName(fieldIdentifier, Some(objectAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "field"
    expressionName.ambiguousName.get.identifier.name shouldBe "object"
  }

  test("ExpressionName should handle this.field access") {
    // Given - this.field
    val fieldIdentifier = Identifier("field")
    val thisAmbiguous = AmbiguousName(Identifier("this"), None)
    val expressionName = ExpressionName(fieldIdentifier, Some(thisAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "field"
    expressionName.ambiguousName.get.identifier.name shouldBe "this"
  }

  test("ExpressionName should handle deeply nested access") {
    // Given - a.b.c.field
    val fieldIdentifier = Identifier("field")
    val cAmbiguous = AmbiguousName(Identifier("c"), None)
    val bAmbiguous = AmbiguousName(Identifier("b"), Some(cAmbiguous))
    val aAmbiguous = AmbiguousName(Identifier("a"), Some(bAmbiguous))
    val expressionName = ExpressionName(fieldIdentifier, Some(aAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "field"
    expressionName.ambiguousName.get.identifier.name shouldBe "a"
    expressionName.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "b"
    expressionName.ambiguousName.get.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "c"
  }

  test("ExpressionName should have proper toString representation") {
    // Given
    val expressionName = ExpressionName(Identifier("testVar"), None)

    // When
    val stringRepresentation = expressionName.toString

    // Then
    stringRepresentation should include("ExpressionName")
    stringRepresentation should include("testVar")
  }

  test("ExpressionName case class should support pattern matching") {
    // Given
    val ambiguous = AmbiguousName(Identifier("obj"), None)
    val expressionName = ExpressionName(Identifier("prop"), Some(ambiguous))

    // When & Then
    expressionName match {
      case ExpressionName(Identifier(name), Some(AmbiguousName(Identifier(objName), None))) =>
        name shouldBe "prop"
        objName shouldBe "obj"
      case _ =>
        fail("Pattern matching failed")
    }
  }

  test("ExpressionName should work with copy method") {
    // Given
    val original = ExpressionName(Identifier("original"), None)
    val newIdentifier = Identifier("modified")
    val newAmbiguous = Some(AmbiguousName(Identifier("prefix"), None))

    // When
    val copiedIdentifier = original.copy(identifier = newIdentifier)
    val copiedWithAmbiguous = original.copy(ambiguousName = newAmbiguous)

    // Then
    original.identifier.name shouldBe "original"
    original.ambiguousName shouldBe None

    copiedIdentifier.identifier.name shouldBe "modified"
    copiedIdentifier.ambiguousName shouldBe None

    copiedWithAmbiguous.identifier.name shouldBe "original"
    copiedWithAmbiguous.ambiguousName.get.identifier.name shouldBe "prefix"
  }

  test("ExpressionName should handle package-qualified class access") {
    // Given - java.lang.String (treated as expression)
    val stringIdentifier = Identifier("String")
    val langAmbiguous = AmbiguousName(Identifier("lang"), None)
    val javaAmbiguous = AmbiguousName(Identifier("java"), Some(langAmbiguous))
    val expressionName = ExpressionName(stringIdentifier, Some(javaAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "String"
    expressionName.ambiguousName.get.identifier.name shouldBe "java"
    expressionName.ambiguousName.get.ambiguousName.get.identifier.name shouldBe "lang"
  }

  test("ExpressionName should distinguish qualified vs unqualified") {
    // Given
    val unqualified = ExpressionName(Identifier("variable"), None)
    val qualified = ExpressionName(Identifier("variable"), 
      Some(AmbiguousName(Identifier("object"), None)))

    // When & Then
    unqualified should not be qualified
    unqualified.identifier.name shouldBe "variable"
    unqualified.ambiguousName shouldBe None
    
    qualified.identifier.name shouldBe "variable"
    qualified.ambiguousName.get.identifier.name shouldBe "object"
  }

  test("ExpressionName should handle static field access patterns") {
    // Given - ClassName.CONSTANT
    val constantIdentifier = Identifier("CONSTANT")
    val classAmbiguous = AmbiguousName(Identifier("ClassName"), None)
    val expressionName = ExpressionName(constantIdentifier, Some(classAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "CONSTANT"
    expressionName.ambiguousName.get.identifier.name shouldBe "ClassName"
  }

  test("ExpressionName should handle array length access") {
    // Given - array.length
    val lengthIdentifier = Identifier("length")
    val arrayAmbiguous = AmbiguousName(Identifier("array"), None)
    val expressionName = ExpressionName(lengthIdentifier, Some(arrayAmbiguous))

    // When & Then
    expressionName.identifier.name shouldBe "length"
    expressionName.ambiguousName.get.identifier.name shouldBe "array"
  }
}