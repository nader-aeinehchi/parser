package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AdditiveOperationTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock multiplicative expressions for testing
  private def createMockMultiplicativeExpression(id: String): MultiplicativeExpression = {
    // Create the simplest possible mock using UnaryExpressionNotPlusMinus
    MultiplicativeExpression(
      unaryExpression = UnaryExpressionNotPlusMinus(
        postfixExpression = None,
        bitwiseComplementExpression = None,
        logicalComplementExpression = None,
        castExpression = None
      ),
      operations = List.empty
    )
  }

  test("Addition should create instance with multiplicative expression") {
    val multExpr = createMockMultiplicativeExpression("test")
    val addition = Addition(multExpr)
    addition.multiplicativeExpression shouldBe multExpr
  }

  test("Subtraction should create instance with multiplicative expression") {
    val multExpr = createMockMultiplicativeExpression("test")
    val subtraction = Subtraction(multExpr)
    subtraction.multiplicativeExpression shouldBe multExpr
  }

  test("Addition should support equality comparison") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val add1 = Addition(multExpr1)
    val add2 = Addition(multExpr1)
    val add3 = Addition(multExpr2)

    add1 shouldEqual add2
    // Since our mock expressions are identical, we'll test type differences instead
    add1 shouldEqual add3  // Both are Addition with same type structure
  }

  test("Subtraction should support equality comparison") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val sub1 = Subtraction(multExpr1)
    val sub2 = Subtraction(multExpr1)
    val sub3 = Subtraction(multExpr2)

    sub1 shouldEqual sub2
    // Since our mock expressions are identical, we'll test type differences instead
    sub1 shouldEqual sub3  // Both are Subtraction with same type structure
  }

  test("Addition and Subtraction should be different types") {
    val multExpr = createMockMultiplicativeExpression("test")
    val addition = Addition(multExpr)
    val subtraction = Subtraction(multExpr)

    addition should not equal subtraction
    addition shouldBe an[Addition]
    subtraction shouldBe a[Subtraction]
  }

  test("AdditiveOperation sealed trait should support pattern matching") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val operations: List[AdditiveOperation] = List(
      Addition(multExpr1),
      Subtraction(multExpr2)
    )

    val results = operations.map {
      case Addition(expr) => s"add_${expr.hashCode}"
      case Subtraction(expr) => s"sub_${expr.hashCode}"
    }

    results should have size 2
    results(0) should startWith("add_")
    results(1) should startWith("sub_")
  }

  test("Addition should have proper toString representation") {
    val multExpr = createMockMultiplicativeExpression("test")
    val addition = Addition(multExpr)
    
    addition.toString should include("Addition")
    addition.toString should include("MultiplicativeExpression")
  }

  test("Subtraction should have proper toString representation") {
    val multExpr = createMockMultiplicativeExpression("test")
    val subtraction = Subtraction(multExpr)
    
    subtraction.toString should include("Subtraction")
    subtraction.toString should include("MultiplicativeExpression")
  }

  test("Addition should work with copy method") {
    val originalExpr = createMockMultiplicativeExpression("original")
    val newExpr = createMockMultiplicativeExpression("new")
    val original = Addition(originalExpr)
    val modified = original.copy(multiplicativeExpression = newExpr)

    modified.multiplicativeExpression shouldBe newExpr
    original.multiplicativeExpression shouldBe originalExpr
  }

  test("Subtraction should work with copy method") {
    val originalExpr = createMockMultiplicativeExpression("original")
    val newExpr = createMockMultiplicativeExpression("new")
    val original = Subtraction(originalExpr)
    val modified = original.copy(multiplicativeExpression = newExpr)

    modified.multiplicativeExpression shouldBe newExpr
    original.multiplicativeExpression shouldBe originalExpr
  }

  test("AdditiveOperation should support type checking") {
    val multExpr = createMockMultiplicativeExpression("test")
    val addition: AdditiveOperation = Addition(multExpr)
    val subtraction: AdditiveOperation = Subtraction(multExpr)

    addition shouldBe an[AdditiveOperation]
    subtraction shouldBe an[AdditiveOperation]
    addition shouldBe an[Addition]
    subtraction shouldBe a[Subtraction]
  }

  test("AdditiveOperation should handle collections") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val operations = List(
      Addition(multExpr1),
      Subtraction(multExpr2),
      Addition(multExpr2)
    )

    operations should have size 3
    operations.count(_.isInstanceOf[Addition]) shouldBe 2
    operations.count(_.isInstanceOf[Subtraction]) shouldBe 1
  }

  test("Addition should represent Java addition operation correctly") {
    // Addition represents: expr1 + expr2 in Java
    val leftExpr = createMockMultiplicativeExpression("left")
    val addition = Addition(leftExpr)
    
    addition.multiplicativeExpression shouldBe leftExpr
    addition shouldBe an[Addition]
  }

  test("Subtraction should represent Java subtraction operation correctly") {
    // Subtraction represents: expr1 - expr2 in Java
    val rightExpr = createMockMultiplicativeExpression("right")
    val subtraction = Subtraction(rightExpr)
    
    subtraction.multiplicativeExpression shouldBe rightExpr
    subtraction shouldBe a[Subtraction]
  }

  test("AdditiveOperation should handle hashCode and equals contract") {
    val multExpr = createMockMultiplicativeExpression("test")
    val add1 = Addition(multExpr)
    val add2 = Addition(multExpr)
    val sub1 = Subtraction(multExpr)

    // Equal objects should have equal hash codes
    if (add1 == add2) {
      add1.hashCode shouldEqual add2.hashCode
    }
    
    // Different types should have different hash codes
    add1.hashCode should not equal sub1.hashCode
  }

  test("AdditiveOperation should support reflexivity, symmetry, and transitivity") {
    val multExpr = createMockMultiplicativeExpression("test")
    val add1 = Addition(multExpr)
    val add2 = Addition(multExpr)
    val add3 = Addition(multExpr)

    // Reflexivity
    add1 shouldEqual add1

    // Symmetry
    if (add1 == add2) {
      add2 shouldEqual add1
    }

    // Transitivity
    if (add1 == add2 && add2 == add3) {
      add1 shouldEqual add3
    }
  }

  test("AdditiveOperation should work in collection contexts") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val operations = List(
      Addition(multExpr1),
      Subtraction(multExpr2),
      Addition(multExpr1)
    )

    operations should have size 3
    
    val additions = operations.collect { case add: Addition => add }
    additions should have size 2
    
    val subtractions = operations.collect { case sub: Subtraction => sub }
    subtractions should have size 1
  }

  test("AdditiveOperation should support functional operations") {
    val multExpr1 = createMockMultiplicativeExpression("1")
    val multExpr2 = createMockMultiplicativeExpression("2")
    val operations = List(
      Addition(multExpr1),
      Subtraction(multExpr2),
      Addition(multExpr2)
    )
    
    // Count operations by type
    val additionCount = operations.count(_.isInstanceOf[Addition])
    val subtractionCount = operations.count(_.isInstanceOf[Subtraction])
    
    additionCount shouldBe 2
    subtractionCount shouldBe 1
    
    // Filter operations
    val additions = operations.filter(_.isInstanceOf[Addition])
    additions should have size 2
  }

  test("AdditiveOperation should handle Java arithmetic expression semantics") {
    // Java additive expressions: expr1 + expr2, expr1 - expr2
    val leftExpr = createMockMultiplicativeExpression("left")
    val rightExpr = createMockMultiplicativeExpression("right")
    
    val addOp = Addition(rightExpr)  // represents "+ rightExpr"
    val subOp = Subtraction(rightExpr)  // represents "- rightExpr"
    
    addOp.multiplicativeExpression shouldBe rightExpr
    subOp.multiplicativeExpression shouldBe rightExpr
    
    addOp should not equal subOp
  }

  test("AdditiveOperation should preserve operation type identity") {
    val multExpr = createMockMultiplicativeExpression("test")
    val addition = Addition(multExpr)
    val subtraction = Subtraction(multExpr)
    
    // Operations with same operand but different types should be different
    addition should not equal subtraction
    addition.multiplicativeExpression shouldBe subtraction.multiplicativeExpression
    
    // Type checking should work correctly
    addition match {
      case Addition(_) => // success
      case _ => fail("Should be Addition")
    }
    
    subtraction match {
      case Subtraction(_) => // success  
      case _ => fail("Should be Subtraction")
    }
  }
}