package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MethodNameTest extends AnyFunSuite with Matchers {

  test("MethodName should be created with an UnqualifiedMethodIdentifier") {
    // Given
    val methodIdentifier = UnqualifiedMethodIdentifier("calculateSum")

    // When
    val methodName = MethodName(methodIdentifier)

    // Then
    methodName.unqualifiedMethodIdentifier shouldBe methodIdentifier
    methodName.unqualifiedMethodIdentifier.name shouldBe "calculateSum"
  }

  test("MethodName should support equality comparison") {
    // Given
    val methodId1 = UnqualifiedMethodIdentifier("toString")
    val methodId2 = UnqualifiedMethodIdentifier("toString")
    val methodId3 = UnqualifiedMethodIdentifier("hashCode")
    
    val methodName1 = MethodName(methodId1)
    val methodName2 = MethodName(methodId2)
    val methodName3 = MethodName(methodId3)

    // When & Then
    methodName1 shouldBe methodName2
    methodName1 should not be methodName3
    methodName1.hashCode() shouldBe methodName2.hashCode()
  }

  test("MethodName should handle common Java method names") {
    // Given
    val commonMethods = List("equals", "hashCode", "toString", "clone", "finalize")

    // When & Then
    commonMethods.foreach { method =>
      val methodIdentifier = UnqualifiedMethodIdentifier(method)
      val methodName = MethodName(methodIdentifier)
      methodName.unqualifiedMethodIdentifier.name shouldBe method
    }
  }

  test("MethodName should handle constructor methods") {
    // Given
    val constructorIdentifier = UnqualifiedMethodIdentifier("<init>")

    // When
    val methodName = MethodName(constructorIdentifier)

    // Then
    methodName.unqualifiedMethodIdentifier.name shouldBe "<init>"
  }

  test("MethodName should handle static initializer methods") {
    // Given
    val staticInitIdentifier = UnqualifiedMethodIdentifier("<clinit>")

    // When
    val methodName = MethodName(staticInitIdentifier)

    // Then
    methodName.unqualifiedMethodIdentifier.name shouldBe "<clinit>"
  }

  test("MethodName should handle getter and setter methods") {
    // Given
    val getterMethod = MethodName(UnqualifiedMethodIdentifier("getName"))
    val setterMethod = MethodName(UnqualifiedMethodIdentifier("setName"))
    val booleanGetter = MethodName(UnqualifiedMethodIdentifier("isActive"))

    // When & Then
    getterMethod.unqualifiedMethodIdentifier.name shouldBe "getName"
    setterMethod.unqualifiedMethodIdentifier.name shouldBe "setName"
    booleanGetter.unqualifiedMethodIdentifier.name shouldBe "isActive"
  }

  test("MethodName should handle camelCase method names") {
    // Given
    val camelCaseMethod = UnqualifiedMethodIdentifier("calculateTotalAmount")

    // When
    val methodName = MethodName(camelCaseMethod)

    // Then
    methodName.unqualifiedMethodIdentifier.name shouldBe "calculateTotalAmount"
  }

  test("MethodName should have proper toString representation") {
    // Given
    val methodIdentifier = UnqualifiedMethodIdentifier("processData")
    val methodName = MethodName(methodIdentifier)

    // When
    val stringRepresentation = methodName.toString

    // Then
    stringRepresentation should include("MethodName")
    stringRepresentation should include("processData")
  }

  test("MethodName case class should support pattern matching") {
    // Given
    val methodIdentifier = UnqualifiedMethodIdentifier("executeQuery")
    val methodName = MethodName(methodIdentifier)

    // When & Then
    methodName match {
      case MethodName(UnqualifiedMethodIdentifier(name)) =>
        name shouldBe "executeQuery"
    }
  }

  test("MethodName should work with copy method") {
    // Given
    val original = MethodName(UnqualifiedMethodIdentifier("original"))
    val newMethodIdentifier = UnqualifiedMethodIdentifier("modified")

    // When
    val copied = original.copy(unqualifiedMethodIdentifier = newMethodIdentifier)

    // Then
    original.unqualifiedMethodIdentifier.name shouldBe "original"
    copied.unqualifiedMethodIdentifier.name shouldBe "modified"
    original should not be copied
  }

  test("MethodName should handle method names with underscores") {
    // Given
    val methodIdentifier = UnqualifiedMethodIdentifier("process_data_batch")

    // When
    val methodName = MethodName(methodIdentifier)

    // Then
    methodName.unqualifiedMethodIdentifier.name shouldBe "process_data_batch"
  }

  test("MethodName should handle single character method names") {
    // Given
    val singleCharMethod = UnqualifiedMethodIdentifier("x")

    // When
    val methodName = MethodName(singleCharMethod)

    // Then
    methodName.unqualifiedMethodIdentifier.name shouldBe "x"
  }
}