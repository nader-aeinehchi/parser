package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class RelationalOperationTest extends AnyFunSuite with Matchers {

  test("RelationalOperation should have LessThan case object") {
    // Given & When
    val lessThan = RelationalOperation.LessThan

    // Then
    lessThan shouldBe a[RelationalOperation]
    lessThan shouldBe RelationalOperation.LessThan
  }

  test("RelationalOperation should have GreaterThan case object") {
    // Given & When
    val greaterThan = RelationalOperation.GreaterThan

    // Then
    greaterThan shouldBe a[RelationalOperation]
    greaterThan shouldBe RelationalOperation.GreaterThan
  }

  test("RelationalOperation should have LessThanOrEqual case object") {
    // Given & When
    val lessThanOrEqual = RelationalOperation.LessThanOrEqual

    // Then
    lessThanOrEqual shouldBe a[RelationalOperation]
    lessThanOrEqual shouldBe RelationalOperation.LessThanOrEqual
  }

  test("RelationalOperation should have GreaterThanOrEqual case object") {
    // Given & When
    val greaterThanOrEqual = RelationalOperation.GreaterThanOrEqual

    // Then
    greaterThanOrEqual shouldBe a[RelationalOperation]
    greaterThanOrEqual shouldBe RelationalOperation.GreaterThanOrEqual
  }

  test("RelationalOperation should have InstanceOf case object") {
    // Given & When
    val instanceOf = RelationalOperation.InstanceOf

    // Then
    instanceOf shouldBe a[RelationalOperation]
    instanceOf shouldBe RelationalOperation.InstanceOf
  }

  test("RelationalOperation case objects should support equality comparison") {
    // Given
    val lessThan1 = RelationalOperation.LessThan
    val lessThan2 = RelationalOperation.LessThan
    val greaterThan = RelationalOperation.GreaterThan

    // When & Then
    lessThan1 shouldBe lessThan2
    lessThan1 should not be greaterThan
    lessThan1.hashCode() shouldBe lessThan2.hashCode()
  }

  test("RelationalOperation should have proper toString representation") {
    // Given
    val operations = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThanOrEqual,
      RelationalOperation.GreaterThanOrEqual,
      RelationalOperation.InstanceOf
    )

    // When & Then
    operations.foreach { operation =>
      val operationString = operation.toString
      operationString should not be empty
      // Should contain the case object name
      operation match {
        case RelationalOperation.LessThan => operationString should include("LessThan")
        case RelationalOperation.GreaterThan => operationString should include("GreaterThan")
        case RelationalOperation.LessThanOrEqual => operationString should include("LessThanOrEqual")
        case RelationalOperation.GreaterThanOrEqual => operationString should include("GreaterThanOrEqual")
        case RelationalOperation.InstanceOf => operationString should include("InstanceOf")
      }
    }
  }

  test("RelationalOperation sealed trait should support pattern matching") {
    // Given
    val operations: List[RelationalOperation] = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThanOrEqual,
      RelationalOperation.GreaterThanOrEqual,
      RelationalOperation.InstanceOf
    )

    // When & Then
    operations.foreach { operation =>
      operation match {
        case RelationalOperation.LessThan =>
          operation shouldBe RelationalOperation.LessThan
        case RelationalOperation.GreaterThan =>
          operation shouldBe RelationalOperation.GreaterThan
        case RelationalOperation.LessThanOrEqual =>
          operation shouldBe RelationalOperation.LessThanOrEqual
        case RelationalOperation.GreaterThanOrEqual =>
          operation shouldBe RelationalOperation.GreaterThanOrEqual
        case RelationalOperation.InstanceOf =>
          operation shouldBe RelationalOperation.InstanceOf
      }
    }
  }

  test("RelationalOperation should handle type checking") {
    // Given
    val lessThan: RelationalOperation = RelationalOperation.LessThan
    val instanceOf: RelationalOperation = RelationalOperation.InstanceOf

    // When & Then
    lessThan.isInstanceOf[RelationalOperation] shouldBe true
    instanceOf.isInstanceOf[RelationalOperation] shouldBe true
    lessThan.isInstanceOf[RelationalOperation.LessThan.type] shouldBe true
    instanceOf.isInstanceOf[RelationalOperation.InstanceOf.type] shouldBe true
  }

  test("RelationalOperation should support exhaustive pattern matching") {
    // Given
    def describeOperation(operation: RelationalOperation): String = operation match {
      case RelationalOperation.LessThan => "<"
      case RelationalOperation.GreaterThan => ">"
      case RelationalOperation.LessThanOrEqual => "<="
      case RelationalOperation.GreaterThanOrEqual => ">="
      case RelationalOperation.InstanceOf => "instanceof"
    }

    // When & Then
    describeOperation(RelationalOperation.LessThan) shouldBe "<"
    describeOperation(RelationalOperation.GreaterThan) shouldBe ">"
    describeOperation(RelationalOperation.LessThanOrEqual) shouldBe "<="
    describeOperation(RelationalOperation.GreaterThanOrEqual) shouldBe ">="
    describeOperation(RelationalOperation.InstanceOf) shouldBe "instanceof"
  }

  test("RelationalOperation should handle collections") {
    // Given
    val operations = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThan,
      RelationalOperation.InstanceOf
    )

    // When
    val lessThanCount = operations.count(_ == RelationalOperation.LessThan)
    val instanceOfCount = operations.count(_ == RelationalOperation.InstanceOf)

    // Then
    lessThanCount shouldBe 2
    instanceOfCount shouldBe 1
    operations should have size 4
  }

  test("RelationalOperation should support set operations") {
    // Given
    val operationSet = Set(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThan,
      RelationalOperation.InstanceOf
    )

    // When & Then
    operationSet should have size 3
    operationSet should contain(RelationalOperation.LessThan)
    operationSet should contain(RelationalOperation.GreaterThan)
    operationSet should contain(RelationalOperation.InstanceOf)
  }

  test("RelationalOperation should represent Java relational operators") {
    // Given - In Java: <, >, <=, >=, instanceof
    val javaOperatorSymbols = Map(
      RelationalOperation.LessThan -> "<",
      RelationalOperation.GreaterThan -> ">",
      RelationalOperation.LessThanOrEqual -> "<=",
      RelationalOperation.GreaterThanOrEqual -> ">=",
      RelationalOperation.InstanceOf -> "instanceof"
    )

    // When & Then
    javaOperatorSymbols should have size 5
    javaOperatorSymbols(RelationalOperation.LessThan) shouldBe "<"
    javaOperatorSymbols(RelationalOperation.GreaterThan) shouldBe ">"
    javaOperatorSymbols(RelationalOperation.LessThanOrEqual) shouldBe "<="
    javaOperatorSymbols(RelationalOperation.GreaterThanOrEqual) shouldBe ">="
    javaOperatorSymbols(RelationalOperation.InstanceOf) shouldBe "instanceof"
  }

  test("RelationalOperation should handle map operations") {
    // Given
    val operations = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.InstanceOf
    )

    // When
    val descriptions = operations.map {
      case RelationalOperation.LessThan => "less than comparison"
      case RelationalOperation.GreaterThan => "greater than comparison"
      case RelationalOperation.LessThanOrEqual => "less than or equal comparison"
      case RelationalOperation.GreaterThanOrEqual => "greater than or equal comparison"
      case RelationalOperation.InstanceOf => "type checking"
    }

    // Then
    descriptions shouldBe List("less than comparison", "greater than comparison", "type checking")
  }

  test("RelationalOperation should support filter operations") {
    // Given
    val allOperations = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThanOrEqual,
      RelationalOperation.GreaterThanOrEqual,
      RelationalOperation.InstanceOf
    )

    // When
    val comparisonOperators = allOperations.filter {
      case RelationalOperation.InstanceOf => false
      case _ => true
    }
    val typeCheckOperators = allOperations.filter(_ == RelationalOperation.InstanceOf)

    // Then
    comparisonOperators should have size 4
    typeCheckOperators should have size 1
    comparisonOperators should contain(RelationalOperation.LessThan)
    comparisonOperators should contain(RelationalOperation.GreaterThanOrEqual)
    typeCheckOperators should contain only RelationalOperation.InstanceOf
  }

  test("RelationalOperation should handle Java semantic meaning") {
    // Given
    val semantics = Map(
      RelationalOperation.LessThan -> "numeric less than",
      RelationalOperation.GreaterThan -> "numeric greater than",
      RelationalOperation.LessThanOrEqual -> "numeric less than or equal",
      RelationalOperation.GreaterThanOrEqual -> "numeric greater than or equal",
      RelationalOperation.InstanceOf -> "runtime type checking"
    )

    // When & Then
    semantics should have size 5
    semantics(RelationalOperation.LessThan) should include("less than")
    semantics(RelationalOperation.InstanceOf) should include("type checking")
  }

  test("RelationalOperation should categorize comparison vs type operations") {
    // Given
    val allOperations = List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThanOrEqual,
      RelationalOperation.GreaterThanOrEqual,
      RelationalOperation.InstanceOf
    )

    // When
    val (numericComparisons, typeOperations) = allOperations.partition(_ != RelationalOperation.InstanceOf)

    // Then
    numericComparisons should have size 4
    typeOperations should have size 1
    numericComparisons should contain allElementsOf List(
      RelationalOperation.LessThan,
      RelationalOperation.GreaterThan,
      RelationalOperation.LessThanOrEqual,
      RelationalOperation.GreaterThanOrEqual
    )
    typeOperations should contain only RelationalOperation.InstanceOf
  }
}