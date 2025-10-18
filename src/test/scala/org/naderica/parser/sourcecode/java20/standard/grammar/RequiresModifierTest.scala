package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class RequiresModifierTest extends AnyFunSuite with Matchers {

  test("RequiresModifier should have TransitiveRequiresModifier case object") {
    // Given & When
    val transitiveModifier = TransitiveRequiresModifier

    // Then
    transitiveModifier shouldBe a[RequiresModifier]
    transitiveModifier shouldBe TransitiveRequiresModifier
  }

  test("RequiresModifier should have StaticRequiresModifier case object") {
    // Given & When
    val staticModifier = StaticRequiresModifier

    // Then
    staticModifier shouldBe a[RequiresModifier]
    staticModifier shouldBe StaticRequiresModifier
  }

  test("RequiresModifier case objects should support equality comparison") {
    // Given
    val transitive1 = TransitiveRequiresModifier
    val transitive2 = TransitiveRequiresModifier
    val static1 = StaticRequiresModifier
    val static2 = StaticRequiresModifier

    // When & Then
    transitive1 shouldBe transitive2
    static1 shouldBe static2
    transitive1 should not be static1
    transitive1.hashCode() shouldBe transitive2.hashCode()
    static1.hashCode() shouldBe static2.hashCode()
  }

  test("RequiresModifier should have proper toString representation") {
    // Given
    val transitiveModifier = TransitiveRequiresModifier
    val staticModifier = StaticRequiresModifier

    // When
    val transitiveString = transitiveModifier.toString
    val staticString = staticModifier.toString

    // Then
    transitiveString should include("TransitiveRequiresModifier")
    staticString should include("StaticRequiresModifier")
  }

  test("RequiresModifier sealed trait should support pattern matching") {
    // Given
    val modifiers: List[RequiresModifier] = List(TransitiveRequiresModifier, StaticRequiresModifier)

    // When & Then
    modifiers.foreach { modifier =>
      modifier match {
        case TransitiveRequiresModifier =>
          modifier shouldBe TransitiveRequiresModifier
        case StaticRequiresModifier =>
          modifier shouldBe StaticRequiresModifier
      }
    }
  }

  test("RequiresModifier should handle type checking") {
    // Given
    val transitiveModifier: RequiresModifier = TransitiveRequiresModifier
    val staticModifier: RequiresModifier = StaticRequiresModifier

    // When & Then
    transitiveModifier.isInstanceOf[RequiresModifier] shouldBe true
    staticModifier.isInstanceOf[RequiresModifier] shouldBe true
    transitiveModifier.isInstanceOf[TransitiveRequiresModifier.type] shouldBe true
    staticModifier.isInstanceOf[StaticRequiresModifier.type] shouldBe true
  }

  test("RequiresModifier should support exhaustive pattern matching") {
    // Given
    def describeModifier(modifier: RequiresModifier): String = modifier match {
      case TransitiveRequiresModifier => "transitive"
      case StaticRequiresModifier => "static"
    }

    // When & Then
    describeModifier(TransitiveRequiresModifier) shouldBe "transitive"
    describeModifier(StaticRequiresModifier) shouldBe "static"
  }

  test("RequiresModifier should handle collections") {
    // Given
    val modifiers = List(TransitiveRequiresModifier, StaticRequiresModifier, TransitiveRequiresModifier)

    // When
    val transitiveCount = modifiers.count(_ == TransitiveRequiresModifier)
    val staticCount = modifiers.count(_ == StaticRequiresModifier)

    // Then
    transitiveCount shouldBe 2
    staticCount shouldBe 1
    modifiers should have size 3
  }

  test("RequiresModifier should support set operations") {
    // Given
    val modifierSet = Set(TransitiveRequiresModifier, StaticRequiresModifier, TransitiveRequiresModifier)

    // When & Then
    modifierSet should have size 2
    modifierSet should contain(TransitiveRequiresModifier)
    modifierSet should contain(StaticRequiresModifier)
  }

  test("RequiresModifier should represent Java module requires modifiers") {
    // Given - In Java: requires transitive java.base; requires static java.logging;
    val transitiveRequires = TransitiveRequiresModifier
    val staticRequires = StaticRequiresModifier

    // When & Then
    // transitive means the module exports the required module's exports
    transitiveRequires shouldBe TransitiveRequiresModifier
    
    // static means the module is required only at compile time
    staticRequires shouldBe StaticRequiresModifier
  }

  test("RequiresModifier should handle map operations") {
    // Given
    val modifiers = List(TransitiveRequiresModifier, StaticRequiresModifier)

    // When
    val descriptions = modifiers.map {
      case TransitiveRequiresModifier => "exports dependencies"
      case StaticRequiresModifier => "compile-time only"
    }

    // Then
    descriptions shouldBe List("exports dependencies", "compile-time only")
  }
}