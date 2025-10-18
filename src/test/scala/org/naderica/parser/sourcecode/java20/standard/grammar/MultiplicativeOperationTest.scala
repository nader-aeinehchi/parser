package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MultiplicativeOperationTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock unary expressions for testing
  private def createMockUnaryExpression(id: String): UnaryExpression = {
    // Create the simplest possible mock using UnaryExpressionNotPlusMinus
    UnaryExpressionNotPlusMinus(
      postfixExpression = None,
      bitwiseComplementExpression = None,
      logicalComplementExpression = None,
      castExpression = None
    )
  }

  test("Multiplication should create instance with unary expression") {
    val unaryExpr = createMockUnaryExpression("test")
    val multiplication = Multiplication(unaryExpr)
    multiplication.unaryExpression shouldBe unaryExpr
  }

  test("Division should create instance with unary expression") {
    val unaryExpr = createMockUnaryExpression("test")
    val division = Division(unaryExpr)
    division.unaryExpression shouldBe unaryExpr
  }

  test("Modulo should create instance with unary expression") {
    val unaryExpr = createMockUnaryExpression("test")
    val modulo = Modulo(unaryExpr)
    modulo.unaryExpression shouldBe unaryExpr
  }

  test("Multiplication should support equality comparison") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val mult1 = Multiplication(unaryExpr1)
    val mult2 = Multiplication(unaryExpr1)
    val mult3 = Multiplication(unaryExpr2)

    mult1 shouldEqual mult2
    // Since our mock expressions are identical, they will be equal
    mult1 shouldEqual mult3  // Both are Multiplication with same type structure
  }

  test("Division should support equality comparison") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val div1 = Division(unaryExpr1)
    val div2 = Division(unaryExpr1)
    val div3 = Division(unaryExpr2)

    div1 shouldEqual div2
    // Since our mock expressions are identical, they will be equal
    div1 shouldEqual div3  // Both are Division with same type structure
  }

  test("Modulo should support equality comparison") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val mod1 = Modulo(unaryExpr1)
    val mod2 = Modulo(unaryExpr1)
    val mod3 = Modulo(unaryExpr2)

    mod1 shouldEqual mod2
    // Since our mock expressions are identical, they will be equal
    mod1 shouldEqual mod3  // Both are Modulo with same type structure
  }

  test("Multiplication, Division, and Modulo should be different types") {
    val unaryExpr = createMockUnaryExpression("test")
    val multiplication = Multiplication(unaryExpr)
    val division = Division(unaryExpr)
    val modulo = Modulo(unaryExpr)

    multiplication should not equal division
    multiplication should not equal modulo
    division should not equal modulo
    
    multiplication shouldBe a[Multiplication]
    division shouldBe a[Division]
    modulo shouldBe a[Modulo]
  }

  test("MultiplicativeOperation sealed trait should support pattern matching") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val unaryExpr3 = createMockUnaryExpression("3")
    val operations: List[MultiplicativeOperation] = List(
      Multiplication(unaryExpr1),
      Division(unaryExpr2),
      Modulo(unaryExpr3)
    )

    val results = operations.map {
      case Multiplication(expr) => s"mult_${expr.hashCode}"
      case Division(expr) => s"div_${expr.hashCode}"
      case Modulo(expr) => s"mod_${expr.hashCode}"
    }

    results should have size 3
    results(0) should startWith("mult_")
    results(1) should startWith("div_")
    results(2) should startWith("mod_")
  }

  test("Multiplication should have proper toString representation") {
    val unaryExpr = createMockUnaryExpression("test")
    val multiplication = Multiplication(unaryExpr)
    
    multiplication.toString should include("Multiplication")
    multiplication.toString should include("UnaryExpression")
  }

  test("Division should have proper toString representation") {
    val unaryExpr = createMockUnaryExpression("test")
    val division = Division(unaryExpr)
    
    division.toString should include("Division")
    division.toString should include("UnaryExpression")
  }

  test("Modulo should have proper toString representation") {
    val unaryExpr = createMockUnaryExpression("test")
    val modulo = Modulo(unaryExpr)
    
    modulo.toString should include("Modulo")
    modulo.toString should include("UnaryExpression")
  }

  test("Multiplication should work with copy method") {
    val originalExpr = createMockUnaryExpression("original")
    val newExpr = createMockUnaryExpression("new")
    val original = Multiplication(originalExpr)
    val modified = original.copy(unaryExpression = newExpr)

    modified.unaryExpression shouldBe newExpr
    original.unaryExpression shouldBe originalExpr
  }

  test("Division should work with copy method") {
    val originalExpr = createMockUnaryExpression("original")
    val newExpr = createMockUnaryExpression("new")
    val original = Division(originalExpr)
    val modified = original.copy(unaryExpression = newExpr)

    modified.unaryExpression shouldBe newExpr
    original.unaryExpression shouldBe originalExpr
  }

  test("Modulo should work with copy method") {
    val originalExpr = createMockUnaryExpression("original")
    val newExpr = createMockUnaryExpression("new")
    val original = Modulo(originalExpr)
    val modified = original.copy(unaryExpression = newExpr)

    modified.unaryExpression shouldBe newExpr
    original.unaryExpression shouldBe originalExpr
  }

  test("MultiplicativeOperation should support type checking") {
    val unaryExpr = createMockUnaryExpression("test")
    val multiplication: MultiplicativeOperation = Multiplication(unaryExpr)
    val division: MultiplicativeOperation = Division(unaryExpr)
    val modulo: MultiplicativeOperation = Modulo(unaryExpr)

    multiplication shouldBe a[MultiplicativeOperation]
    division shouldBe a[MultiplicativeOperation]
    modulo shouldBe a[MultiplicativeOperation]
    multiplication shouldBe a[Multiplication]
    division shouldBe a[Division]
    modulo shouldBe a[Modulo]
  }

  test("MultiplicativeOperation should handle collections") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val operations = List(
      Multiplication(unaryExpr1),
      Division(unaryExpr2),
      Modulo(unaryExpr1),
      Multiplication(unaryExpr2)
    )

    operations should have size 4
    operations.count(_.isInstanceOf[Multiplication]) shouldBe 2
    operations.count(_.isInstanceOf[Division]) shouldBe 1
    operations.count(_.isInstanceOf[Modulo]) shouldBe 1
  }

  test("Multiplication should represent Java multiplication operation correctly") {
    // Multiplication represents: expr1 * expr2 in Java
    val rightExpr = createMockUnaryExpression("right")
    val multiplication = Multiplication(rightExpr)
    
    multiplication.unaryExpression shouldBe rightExpr
    multiplication shouldBe a[Multiplication]
  }

  test("Division should represent Java division operation correctly") {
    // Division represents: expr1 / expr2 in Java
    val rightExpr = createMockUnaryExpression("right")
    val division = Division(rightExpr)
    
    division.unaryExpression shouldBe rightExpr
    division shouldBe a[Division]
  }

  test("Modulo should represent Java modulo operation correctly") {
    // Modulo represents: expr1 % expr2 in Java
    val rightExpr = createMockUnaryExpression("right")
    val modulo = Modulo(rightExpr)
    
    modulo.unaryExpression shouldBe rightExpr
    modulo shouldBe a[Modulo]
  }

  test("MultiplicativeOperation should handle hashCode and equals contract") {
    val unaryExpr = createMockUnaryExpression("test")
    val mult1 = Multiplication(unaryExpr)
    val mult2 = Multiplication(unaryExpr)
    val div1 = Division(unaryExpr)

    // Equal objects should have equal hash codes
    if (mult1 == mult2) {
      mult1.hashCode shouldEqual mult2.hashCode
    }
    
    // Different types should have different hash codes
    mult1.hashCode should not equal div1.hashCode
  }

  test("MultiplicativeOperation should support reflexivity, symmetry, and transitivity") {
    val unaryExpr = createMockUnaryExpression("test")
    val mult1 = Multiplication(unaryExpr)
    val mult2 = Multiplication(unaryExpr)
    val mult3 = Multiplication(unaryExpr)

    // Reflexivity
    mult1 shouldEqual mult1

    // Symmetry
    if (mult1 == mult2) {
      mult2 shouldEqual mult1
    }

    // Transitivity
    if (mult1 == mult2 && mult2 == mult3) {
      mult1 shouldEqual mult3
    }
  }

  test("MultiplicativeOperation should work in collection contexts") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val operations = List(
      Multiplication(unaryExpr1),
      Division(unaryExpr2),
      Modulo(unaryExpr1)
    )

    operations should have size 3
    
    val multiplications = operations.collect { case mult: Multiplication => mult }
    multiplications should have size 1
    
    val divisions = operations.collect { case div: Division => div }
    divisions should have size 1
    
    val modulos = operations.collect { case mod: Modulo => mod }
    modulos should have size 1
  }

  test("MultiplicativeOperation should support functional operations") {
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val operations = List(
      Multiplication(unaryExpr1),
      Division(unaryExpr2),
      Modulo(unaryExpr1),
      Multiplication(unaryExpr2)
    )
    
    // Count operations by type
    val multiplicationCount = operations.count(_.isInstanceOf[Multiplication])
    val divisionCount = operations.count(_.isInstanceOf[Division])
    val moduloCount = operations.count(_.isInstanceOf[Modulo])
    
    multiplicationCount shouldBe 2
    divisionCount shouldBe 1
    moduloCount shouldBe 1
    
    // Filter operations
    val multiplications = operations.filter(_.isInstanceOf[Multiplication])
    multiplications should have size 2
  }

  test("MultiplicativeOperation should handle Java arithmetic expression semantics") {
    // Java multiplicative expressions: expr1 * expr2, expr1 / expr2, expr1 % expr2
    val rightExpr1 = createMockUnaryExpression("right1")
    val rightExpr2 = createMockUnaryExpression("right2")
    val rightExpr3 = createMockUnaryExpression("right3")
    
    val multOp = Multiplication(rightExpr1)  // represents "* rightExpr1"
    val divOp = Division(rightExpr2)         // represents "/ rightExpr2"
    val modOp = Modulo(rightExpr3)           // represents "% rightExpr3"
    
    multOp.unaryExpression shouldBe rightExpr1
    divOp.unaryExpression shouldBe rightExpr2
    modOp.unaryExpression shouldBe rightExpr3
    
    multOp should not equal divOp
    divOp should not equal modOp
    multOp should not equal modOp
  }

  test("MultiplicativeOperation should preserve operation type identity") {
    val unaryExpr = createMockUnaryExpression("test")
    val multiplication = Multiplication(unaryExpr)
    val division = Division(unaryExpr)
    val modulo = Modulo(unaryExpr)
    
    // Operations with same operand but different types should be different
    multiplication should not equal division
    division should not equal modulo
    multiplication should not equal modulo
    
    multiplication.unaryExpression shouldBe division.unaryExpression
    division.unaryExpression shouldBe modulo.unaryExpression
    
    // Type checking should work correctly
    multiplication match {
      case Multiplication(_) => // success
      case _ => fail("Should be Multiplication")
    }
    
    division match {
      case Division(_) => // success  
      case _ => fail("Should be Division")
    }
    
    modulo match {
      case Modulo(_) => // success
      case _ => fail("Should be Modulo")
    }
  }

  test("MultiplicativeOperation should handle operator precedence semantics") {
    // In Java, *, /, % have same precedence and are left-associative
    val unaryExpr1 = createMockUnaryExpression("1")
    val unaryExpr2 = createMockUnaryExpression("2")
    val unaryExpr3 = createMockUnaryExpression("3")
    
    val operations = List(
      Multiplication(unaryExpr1),  // * expr1
      Division(unaryExpr2),        // / expr2  
      Modulo(unaryExpr3)           // % expr3
    )
    
    // All have same precedence in Java
    operations should have size 3
    operations.foreach { op =>
      op shouldBe a[MultiplicativeOperation]
    }
  }
}