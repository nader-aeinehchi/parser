package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class BreakStatementTest extends AnyFunSuite with Matchers {

  test("BreakStatement should create instance with identifier") {
    val identifier = Identifier("outerLoop")
    val breakStatement = BreakStatement(Some(identifier))
    breakStatement.identifier shouldBe Some(identifier)
  }

  test("BreakStatement should create instance without identifier") {
    val breakStatement = BreakStatement(None)
    breakStatement.identifier shouldBe None
  }

  test("BreakStatement should support copy with modified identifier") {
    val original = BreakStatement(Some(Identifier("label1")))
    val modified = original.copy(identifier = Some(Identifier("label2")))
    modified.identifier shouldBe Some(Identifier("label2"))
    original.identifier shouldBe Some(Identifier("label1"))
  }

  test("BreakStatement should support copy from labeled to unlabeled") {
    val original = BreakStatement(Some(Identifier("loop")))
    val modified = original.copy(identifier = None)
    modified.identifier shouldBe None
    original.identifier shouldBe Some(Identifier("loop"))
  }

  test("BreakStatement should support copy from unlabeled to labeled") {
    val original = BreakStatement(None)
    val modified = original.copy(identifier = Some(Identifier("newLabel")))
    modified.identifier shouldBe Some(Identifier("newLabel"))
    original.identifier shouldBe None
  }

  test("BreakStatement equality should work correctly") {
    val stmt1 = BreakStatement(Some(Identifier("loop")))
    val stmt2 = BreakStatement(Some(Identifier("loop")))
    val stmt3 = BreakStatement(Some(Identifier("other")))
    val stmt4 = BreakStatement(None)

    stmt1 shouldEqual stmt2
    stmt1 should not equal stmt3
    stmt1 should not equal stmt4
  }

  test("BreakStatement toString should be readable") {
    val labeledBreak = BreakStatement(Some(Identifier("outerLoop")))
    val unlabeledBreak = BreakStatement(None)
    
    labeledBreak.toString should include("BreakStatement")
    labeledBreak.toString should include("outerLoop")
    unlabeledBreak.toString should include("BreakStatement")
  }

  test("BreakStatement should handle Java break statement semantics") {
    val unlabeledBreak = BreakStatement(None)
    val labeledBreak = BreakStatement(Some(Identifier("outerLoop")))
    val nestedLabelBreak = BreakStatement(Some(Identifier("innerLoop")))

    // Unlabeled break exits nearest enclosing loop or switch
    unlabeledBreak.identifier shouldBe None
    
    // Labeled break exits specified labeled statement
    labeledBreak.identifier.get.name shouldBe "outerLoop"
    nestedLabelBreak.identifier.get.name shouldBe "innerLoop"
  }

  test("BreakStatement should support pattern matching on labeled vs unlabeled") {
    val labeled = BreakStatement(Some(Identifier("loop")))
    val unlabeled = BreakStatement(None)

    val result1 = labeled match {
      case BreakStatement(Some(id)) => s"labeled break to ${id.name}"
      case BreakStatement(None) => "unlabeled break"
    }

    val result2 = unlabeled match {
      case BreakStatement(Some(id)) => s"labeled break to ${id.name}"
      case BreakStatement(None) => "unlabeled break"
    }

    result1 shouldBe "labeled break to loop"
    result2 shouldBe "unlabeled break"
  }

  test("BreakStatement should support pattern matching on specific labels") {
    val outerBreak = BreakStatement(Some(Identifier("outer")))
    val innerBreak = BreakStatement(Some(Identifier("inner")))

    val result1 = outerBreak match {
      case BreakStatement(Some(Identifier("outer"))) => "break outer loop"
      case BreakStatement(Some(Identifier("inner"))) => "break inner loop"
      case BreakStatement(Some(_)) => "break other label"
      case BreakStatement(None) => "unlabeled break"
    }

    val result2 = innerBreak match {
      case BreakStatement(Some(Identifier("outer"))) => "break outer loop"
      case BreakStatement(Some(Identifier("inner"))) => "break inner loop"
      case BreakStatement(Some(_)) => "break other label"
      case BreakStatement(None) => "unlabeled break"
    }

    result1 shouldBe "break outer loop"
    result2 shouldBe "break inner loop"
  }

  test("BreakStatement should handle control flow semantics") {
    val loopBreak = BreakStatement(Some(Identifier("forLoop")))
    val switchBreak = BreakStatement(None)
    val whileBreak = BreakStatement(Some(Identifier("whileLoop")))

    // Should represent different control flow exits
    loopBreak.identifier.get.name shouldBe "forLoop"
    switchBreak.identifier shouldBe None
    whileBreak.identifier.get.name shouldBe "whileLoop"
  }

  test("BreakStatement should handle null safety") {
    val breakStatement = BreakStatement(Some(Identifier("label")))
    val emptyBreak = BreakStatement(None)
    
    breakStatement should not be null
    breakStatement.identifier should not be null
    emptyBreak should not be null
    emptyBreak.identifier shouldBe None
  }

  test("BreakStatement should handle edge cases with special label names") {
    val underscoreLabel = BreakStatement(Some(Identifier("_")))
    val dollarLabel = BreakStatement(Some(Identifier("$label")))
    val unicodeLabel = BreakStatement(Some(Identifier("lööp")))

    underscoreLabel.identifier.get.name shouldBe "_"
    dollarLabel.identifier.get.name shouldBe "$label"
    unicodeLabel.identifier.get.name shouldBe "lööp"
  }

  test("BreakStatement should handle typical Java label naming conventions") {
    val camelCase = BreakStatement(Some(Identifier("outerLoop")))
    val underscoreCase = BreakStatement(Some(Identifier("outer_loop")))
    val allCaps = BreakStatement(Some(Identifier("MAIN_LOOP")))

    camelCase.identifier.get.name shouldBe "outerLoop"
    underscoreCase.identifier.get.name shouldBe "outer_loop"
    allCaps.identifier.get.name shouldBe "MAIN_LOOP"
  }

  test("BreakStatement should be a Statement") {
    val breakStatement = BreakStatement(None)
    breakStatement shouldBe a[Statement]
  }

  test("BreakStatement should handle hashCode and equals contract") {
    val stmt1 = BreakStatement(Some(Identifier("loop")))
    val stmt2 = BreakStatement(Some(Identifier("loop")))
    val stmt3 = BreakStatement(None)

    // Equal objects should have equal hash codes
    if (stmt1 == stmt2) {
      stmt1.hashCode shouldEqual stmt2.hashCode
    }
    
    // Different objects may have different hash codes
    stmt1.hashCode should not equal stmt3.hashCode
  }

  test("BreakStatement should support reflexivity, symmetry, and transitivity") {
    val stmt1 = BreakStatement(Some(Identifier("loop")))
    val stmt2 = BreakStatement(Some(Identifier("loop")))
    val stmt3 = BreakStatement(Some(Identifier("loop")))

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

  test("BreakStatement should represent Java control flow correctly") {
    // Java break statement can be labeled or unlabeled
    val unlabeled = BreakStatement(None)
    val labeled = BreakStatement(Some(Identifier("label")))

    // Unlabeled break terminates nearest enclosing switch, while, do-while, or for statement
    unlabeled.identifier shouldBe None
    
    // Labeled break terminates enclosing labeled statement
    labeled.identifier shouldBe defined
    labeled.identifier.get.name shouldBe "label"
  }

  test("BreakStatement should work in collection contexts") {
    val statements = List(
      BreakStatement(None),
      BreakStatement(Some(Identifier("outer"))),
      BreakStatement(Some(Identifier("inner")))
    )

    statements should have size 3
    
    val labeledStatements = statements.filter(_.identifier.isDefined)
    labeledStatements should have size 2
    
    val unlabeledStatements = statements.filter(_.identifier.isEmpty)
    unlabeledStatements should have size 1
  }

  test("BreakStatement should support functional operations") {
    val breakStatements = List(
      BreakStatement(Some(Identifier("loop1"))),
      BreakStatement(None),
      BreakStatement(Some(Identifier("loop2")))
    )
    
    // Extract label names
    val labels = breakStatements.flatMap(_.identifier.map(_.name))
    labels shouldBe List("loop1", "loop2")
    
    // Count labeled vs unlabeled
    val labeledCount = breakStatements.count(_.identifier.isDefined)
    val unlabeledCount = breakStatements.count(_.identifier.isEmpty)
    
    labeledCount shouldBe 2
    unlabeledCount shouldBe 1
  }
}