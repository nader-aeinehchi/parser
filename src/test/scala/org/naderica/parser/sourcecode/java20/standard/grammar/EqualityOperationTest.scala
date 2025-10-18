package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EqualityOperationTest extends AnyFunSuite with Matchers {

  test("EqualityOperation should have Equal case object") {
    // Given & When
    val equalOp = EqualityOperation.Equal

    // Then
    equalOp shouldBe a[EqualityOperation]
    equalOp shouldBe EqualityOperation.Equal
  }

  test("EqualityOperation should have NotEqual case object") {
    // Given & When
    val notEqualOp = EqualityOperation.NotEqual

    // Then
    notEqualOp shouldBe a[EqualityOperation]
    notEqualOp shouldBe EqualityOperation.NotEqual
  }

  test("EqualityOperation case objects should support equality comparison") {
    // Given
    val equal1 = EqualityOperation.Equal
    val equal2 = EqualityOperation.Equal
    val notEqual1 = EqualityOperation.NotEqual
    val notEqual2 = EqualityOperation.NotEqual

    // When & Then
    equal1 shouldBe equal2
    notEqual1 shouldBe notEqual2
    equal1 should not be notEqual1
    equal1.hashCode() shouldBe equal2.hashCode()
    notEqual1.hashCode() shouldBe notEqual2.hashCode()
  }

  test("EqualityOperation should have proper toString representation") {
    // Given
    val equalOp = EqualityOperation.Equal
    val notEqualOp = EqualityOperation.NotEqual

    // When
    val equalString = equalOp.toString
    val notEqualString = notEqualOp.toString

    // Then
    equalString should include("Equal")
    notEqualString should include("NotEqual")
  }

  test("EqualityOperation sealed trait should support pattern matching") {
    // Given
    val operations: List[EqualityOperation] = List(EqualityOperation.Equal, EqualityOperation.NotEqual)

    // When & Then
    operations.foreach { operation =>
      operation match {
        case EqualityOperation.Equal =>
          operation shouldBe EqualityOperation.Equal
        case EqualityOperation.NotEqual =>
          operation shouldBe EqualityOperation.NotEqual
      }
    }
  }

  test("EqualityOperation should handle type checking") {
    // Given
    val equalOp: EqualityOperation = EqualityOperation.Equal
    val notEqualOp: EqualityOperation = EqualityOperation.NotEqual

    // When & Then
    equalOp.isInstanceOf[EqualityOperation] shouldBe true
    notEqualOp.isInstanceOf[EqualityOperation] shouldBe true
    equalOp.isInstanceOf[EqualityOperation.Equal.type] shouldBe true
    notEqualOp.isInstanceOf[EqualityOperation.NotEqual.type] shouldBe true
  }

  test("EqualityOperation should support exhaustive pattern matching") {
    // Given
    def describeOperation(operation: EqualityOperation): String = operation match {
      case EqualityOperation.Equal => "=="
      case EqualityOperation.NotEqual => "!="
    }

    // When & Then
    describeOperation(EqualityOperation.Equal) shouldBe "=="
    describeOperation(EqualityOperation.NotEqual) shouldBe "!="
  }

  test("EqualityOperation should handle collections") {
    // Given
    val operations = List(EqualityOperation.Equal, EqualityOperation.NotEqual, EqualityOperation.Equal)

    // When
    val equalCount = operations.count(_ == EqualityOperation.Equal)
    val notEqualCount = operations.count(_ == EqualityOperation.NotEqual)

    // Then
    equalCount shouldBe 2
    notEqualCount shouldBe 1
    operations should have size 3
  }

  test("EqualityOperation should support set operations") {
    // Given
    val operationSet = Set(EqualityOperation.Equal, EqualityOperation.NotEqual, EqualityOperation.Equal)

    // When & Then
    operationSet should have size 2
    operationSet should contain(EqualityOperation.Equal)
    operationSet should contain(EqualityOperation.NotEqual)
  }

  test("EqualityOperation should represent Java equality operators") {
    // Given - In Java: ==, !=
    val equalOp = EqualityOperation.Equal
    val notEqualOp = EqualityOperation.NotEqual

    // When & Then
    // == tests for reference equality (or value equality for primitives)
    equalOp shouldBe EqualityOperation.Equal
    
    // != tests for reference inequality (or value inequality for primitives)
    notEqualOp shouldBe EqualityOperation.NotEqual
  }

  test("EqualityOperation should handle map operations") {
    // Given
    val operations = List(EqualityOperation.Equal, EqualityOperation.NotEqual)

    // When
    val javaOperators = operations.map {
      case EqualityOperation.Equal => "=="
      case EqualityOperation.NotEqual => "!="
    }

    // Then
    javaOperators shouldBe List("==", "!=")
  }

  test("EqualityOperation should support filter operations") {
    // Given
    val operations = List(EqualityOperation.Equal, EqualityOperation.NotEqual, EqualityOperation.Equal)

    // When
    val equalOperations = operations.filter(_ == EqualityOperation.Equal)
    val notEqualOperations = operations.filter(_ == EqualityOperation.NotEqual)

    // Then
    equalOperations should have size 2
    notEqualOperations should have size 1
    equalOperations.forall(_ == EqualityOperation.Equal) shouldBe true
    notEqualOperations.forall(_ == EqualityOperation.NotEqual) shouldBe true
  }

  test("EqualityOperation should handle Java semantic meaning") {
    // Given
    val equalityOps = Map(
      EqualityOperation.Equal -> "reference or value equality",
      EqualityOperation.NotEqual -> "reference or value inequality"
    )

    // When & Then
    equalityOps should have size 2
    equalityOps should contain key EqualityOperation.Equal
    equalityOps should contain key EqualityOperation.NotEqual
    
    // Verify semantic descriptions
    equalityOps(EqualityOperation.Equal) should include("equality")
    equalityOps(EqualityOperation.NotEqual) should include("inequality")
  }

  test("EqualityOperation should handle case object identity") {
    // Given
    val equal1 = EqualityOperation.Equal
    val equal2 = EqualityOperation.Equal

    // When & Then
    // Case objects are singletons
    (equal1 eq equal2) shouldBe true
    equal1.synchronized(equal1) shouldBe EqualityOperation.Equal
  }
}