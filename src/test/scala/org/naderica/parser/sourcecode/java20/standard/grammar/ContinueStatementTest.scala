package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ContinueStatementTest extends AnyFunSuite with Matchers {

  test("ContinueStatement should create instance with identifier") {
    val identifier = Identifier("outerLoop")
    val continueStatement = ContinueStatement(Some(identifier))
    continueStatement.identifier shouldBe Some(identifier)
  }

  test("ContinueStatement should create instance without identifier") {
    val continueStatement = ContinueStatement(None)
    continueStatement.identifier shouldBe None
  }

  test("ContinueStatement should support copy with modified identifier") {
    val original = ContinueStatement(Some(Identifier("label1")))
    val modified = original.copy(identifier = Some(Identifier("label2")))
    modified.identifier shouldBe Some(Identifier("label2"))
    original.identifier shouldBe Some(Identifier("label1"))
  }

  test("ContinueStatement should support copy from labeled to unlabeled") {
    val original = ContinueStatement(Some(Identifier("loop")))
    val modified = original.copy(identifier = None)
    modified.identifier shouldBe None
    original.identifier shouldBe Some(Identifier("loop"))
  }

  test("ContinueStatement should support copy from unlabeled to labeled") {
    val original = ContinueStatement(None)
    val modified = original.copy(identifier = Some(Identifier("newLabel")))
    modified.identifier shouldBe Some(Identifier("newLabel"))
    original.identifier shouldBe None
  }

  test("ContinueStatement equality should work correctly") {
    val stmt1 = ContinueStatement(Some(Identifier("loop")))
    val stmt2 = ContinueStatement(Some(Identifier("loop")))
    val stmt3 = ContinueStatement(Some(Identifier("other")))
    val stmt4 = ContinueStatement(None)

    stmt1 shouldEqual stmt2
    stmt1 should not equal stmt3
    stmt1 should not equal stmt4
  }

  test("ContinueStatement toString should be readable") {
    val labeledContinue = ContinueStatement(Some(Identifier("outerLoop")))
    val unlabeledContinue = ContinueStatement(None)
    
    labeledContinue.toString should include("ContinueStatement")
    labeledContinue.toString should include("outerLoop")
    unlabeledContinue.toString should include("ContinueStatement")
  }

  test("ContinueStatement should handle Java continue statement semantics") {
    val unlabeledContinue = ContinueStatement(None)
    val labeledContinue = ContinueStatement(Some(Identifier("outerLoop")))
    val nestedLabelContinue = ContinueStatement(Some(Identifier("innerLoop")))

    // Unlabeled continue skips to next iteration of nearest enclosing loop
    unlabeledContinue.identifier shouldBe None
    
    // Labeled continue skips to next iteration of specified labeled loop
    labeledContinue.identifier.get.name shouldBe "outerLoop"
    nestedLabelContinue.identifier.get.name shouldBe "innerLoop"
  }

  test("ContinueStatement should support pattern matching on labeled vs unlabeled") {
    val labeled = ContinueStatement(Some(Identifier("loop")))
    val unlabeled = ContinueStatement(None)

    val result1 = labeled match {
      case ContinueStatement(Some(id)) => s"labeled continue to ${id.name}"
      case ContinueStatement(None) => "unlabeled continue"
    }

    val result2 = unlabeled match {
      case ContinueStatement(Some(id)) => s"labeled continue to ${id.name}"
      case ContinueStatement(None) => "unlabeled continue"
    }

    result1 shouldBe "labeled continue to loop"
    result2 shouldBe "unlabeled continue"
  }

  test("ContinueStatement should support pattern matching on specific labels") {
    val outerContinue = ContinueStatement(Some(Identifier("outer")))
    val innerContinue = ContinueStatement(Some(Identifier("inner")))

    val result1 = outerContinue match {
      case ContinueStatement(Some(Identifier("outer"))) => "continue outer loop"
      case ContinueStatement(Some(Identifier("inner"))) => "continue inner loop"
      case ContinueStatement(Some(_)) => "continue other label"
      case ContinueStatement(None) => "unlabeled continue"
    }

    val result2 = innerContinue match {
      case ContinueStatement(Some(Identifier("outer"))) => "continue outer loop"
      case ContinueStatement(Some(Identifier("inner"))) => "continue inner loop"
      case ContinueStatement(Some(_)) => "continue other label"
      case ContinueStatement(None) => "unlabeled continue"
    }

    result1 shouldBe "continue outer loop"
    result2 shouldBe "continue inner loop"
  }

  test("ContinueStatement should handle loop control flow semantics") {
    val forContinue = ContinueStatement(Some(Identifier("forLoop")))
    val whileContinue = ContinueStatement(None)
    val doWhileContinue = ContinueStatement(Some(Identifier("doWhileLoop")))

    // Should represent different loop continuation behaviors
    forContinue.identifier.get.name shouldBe "forLoop"
    whileContinue.identifier shouldBe None
    doWhileContinue.identifier.get.name shouldBe "doWhileLoop"
  }

  test("ContinueStatement should handle null safety") {
    val continueStatement = ContinueStatement(Some(Identifier("label")))
    val emptyContinue = ContinueStatement(None)
    
    continueStatement should not be null
    continueStatement.identifier should not be null
    emptyContinue should not be null
    emptyContinue.identifier shouldBe None
  }

  test("ContinueStatement should handle edge cases with special label names") {
    val underscoreLabel = ContinueStatement(Some(Identifier("_")))
    val dollarLabel = ContinueStatement(Some(Identifier("$label")))
    val unicodeLabel = ContinueStatement(Some(Identifier("lööp")))

    underscoreLabel.identifier.get.name shouldBe "_"
    dollarLabel.identifier.get.name shouldBe "$label"
    unicodeLabel.identifier.get.name shouldBe "lööp"
  }

  test("ContinueStatement should handle typical Java label naming conventions") {
    val camelCase = ContinueStatement(Some(Identifier("outerLoop")))
    val underscoreCase = ContinueStatement(Some(Identifier("outer_loop")))
    val allCaps = ContinueStatement(Some(Identifier("MAIN_LOOP")))

    camelCase.identifier.get.name shouldBe "outerLoop"
    underscoreCase.identifier.get.name shouldBe "outer_loop"
    allCaps.identifier.get.name shouldBe "MAIN_LOOP"
  }

  test("ContinueStatement should be a Statement") {
    val continueStatement = ContinueStatement(None)
    continueStatement shouldBe a[Statement]
  }

  test("ContinueStatement should handle hashCode and equals contract") {
    val stmt1 = ContinueStatement(Some(Identifier("loop")))
    val stmt2 = ContinueStatement(Some(Identifier("loop")))
    val stmt3 = ContinueStatement(None)

    // Equal objects should have equal hash codes
    if (stmt1 == stmt2) {
      stmt1.hashCode shouldEqual stmt2.hashCode
    }
    
    // Different objects may have different hash codes
    stmt1.hashCode should not equal stmt3.hashCode
  }

  test("ContinueStatement should support reflexivity, symmetry, and transitivity") {
    val stmt1 = ContinueStatement(Some(Identifier("loop")))
    val stmt2 = ContinueStatement(Some(Identifier("loop")))
    val stmt3 = ContinueStatement(Some(Identifier("loop")))

    // Reflexivity
    stmt1 shouldEqual stmt1

    // Symmetry
    if (stmt1 == stmt2) {
      stmt2 shouldEqual stmt1
    }

    // Transitivity
    if (stmt1 == stmt2 && stmt2 == stmt3) {
      stmt1 shouldEqual stmt3
    }
  }

  test("ContinueStatement should represent Java loop control correctly") {
    // Java continue statement can be labeled or unlabeled
    val unlabeled = ContinueStatement(None)
    val labeled = ContinueStatement(Some(Identifier("label")))

    // Unlabeled continue skips to next iteration of nearest enclosing loop
    unlabeled.identifier shouldBe None
    
    // Labeled continue skips to next iteration of enclosing labeled loop
    labeled.identifier shouldBe defined
    labeled.identifier.get.name shouldBe "label"
  }

  test("ContinueStatement should work in collection contexts") {
    val statements = List(
      ContinueStatement(None),
      ContinueStatement(Some(Identifier("outer"))),
      ContinueStatement(Some(Identifier("inner")))
    )

    statements should have size 3
    
    val labeledStatements = statements.filter(_.identifier.isDefined)
    labeledStatements should have size 2
    
    val unlabeledStatements = statements.filter(_.identifier.isEmpty)
    unlabeledStatements should have size 1
  }

  test("ContinueStatement should support functional operations") {
    val continueStatements = List(
      ContinueStatement(Some(Identifier("loop1"))),
      ContinueStatement(None),
      ContinueStatement(Some(Identifier("loop2")))
    )
    
    // Extract label names
    val labels = continueStatements.flatMap(_.identifier.map(_.name))
    labels shouldBe List("loop1", "loop2")
    
    // Count labeled vs unlabeled
    val labeledCount = continueStatements.count(_.identifier.isDefined)
    val unlabeledCount = continueStatements.count(_.identifier.isEmpty)
    
    labeledCount shouldBe 2
    unlabeledCount shouldBe 1
  }

  test("ContinueStatement should differentiate from BreakStatement") {
    val continueStmt = ContinueStatement(Some(Identifier("loop")))
    val breakStmt = BreakStatement(Some(Identifier("loop")))

    // Should be different types despite same identifier
    continueStmt should not equal breakStmt
    continueStmt shouldBe a[ContinueStatement]
    breakStmt shouldBe a[BreakStatement]
  }

  test("ContinueStatement should handle loop iteration semantics correctly") {
    val forLoopContinue = ContinueStatement(Some(Identifier("forLoop")))
    val whileLoopContinue = ContinueStatement(Some(Identifier("whileLoop")))
    val enhancedForContinue = ContinueStatement(Some(Identifier("enhancedFor")))

    // All should maintain their specific loop context
    forLoopContinue.identifier.get.name shouldBe "forLoop"
    whileLoopContinue.identifier.get.name shouldBe "whileLoop"
    enhancedForContinue.identifier.get.name shouldBe "enhancedFor"
    
    // All should be continue statements for different loop types
    List(forLoopContinue, whileLoopContinue, enhancedForContinue).foreach { stmt =>
      stmt shouldBe a[ContinueStatement]
      stmt.identifier shouldBe defined
    }
  }
}