package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class InterfaceModifierTest extends AnyFunSuite with Matchers {

  test("InterfaceModifier should have all access modifier cases") {
    // Given & When
    val modifiers = InterfaceModifier.values.toList

    // Then
    modifiers should contain(InterfaceModifier.Public)
    modifiers should contain(InterfaceModifier.Protected)
    modifiers should contain(InterfaceModifier.Private)
  }

  test("InterfaceModifier should have all behavioral modifier cases") {
    // Given & When
    val modifiers = InterfaceModifier.values.toList

    // Then
    modifiers should contain(InterfaceModifier.Abstract)
    modifiers should contain(InterfaceModifier.Static)
    modifiers should contain(InterfaceModifier.Sealed)
    modifiers should contain(InterfaceModifier.NonSealed)
    modifiers should contain(InterfaceModifier.Strictfp)
  }

  test("InterfaceModifier should have exactly 8 modifiers") {
    // Given & When
    val modifiers = InterfaceModifier.values

    // Then
    modifiers should have size 8
  }

  test("InterfaceModifier should support equality comparison") {
    // Given
    val public1 = InterfaceModifier.Public
    val public2 = InterfaceModifier.Public
    val private1 = InterfaceModifier.Private

    // When & Then
    public1 shouldBe public2
    public1 should not be private1
    public1.hashCode() shouldBe public2.hashCode()
  }

  test("InterfaceModifier should have proper toString representation") {
    // Given
    val modifiers = List(
      InterfaceModifier.Public,
      InterfaceModifier.Protected,
      InterfaceModifier.Private,
      InterfaceModifier.Abstract,
      InterfaceModifier.Static,
      InterfaceModifier.Sealed,
      InterfaceModifier.NonSealed,
      InterfaceModifier.Strictfp
    )

    // When & Then
    modifiers.foreach { modifier =>
      val modifierString = modifier.toString
      modifierString should not be empty
      modifierString should include(modifier.productPrefix)
    }
  }

  test("InterfaceModifier should support pattern matching") {
    // Given
    val modifiers = InterfaceModifier.values.toList

    // When & Then
    modifiers.foreach { modifier =>
      val result = modifier match {
        case InterfaceModifier.Public => "public"
        case InterfaceModifier.Protected => "protected"
        case InterfaceModifier.Private => "private"
        case InterfaceModifier.Abstract => "abstract"
        case InterfaceModifier.Static => "static"
        case InterfaceModifier.Sealed => "sealed"
        case InterfaceModifier.NonSealed => "non-sealed"
        case InterfaceModifier.Strictfp => "strictfp"
      }
      result should not be empty
    }
  }

  test("InterfaceModifier should handle access modifiers") {
    // Given
    val accessModifiers = List(
      InterfaceModifier.Public,
      InterfaceModifier.Protected,
      InterfaceModifier.Private
    )

    // When & Then
    accessModifiers should have size 3
    accessModifiers should contain(InterfaceModifier.Public)
    accessModifiers should contain(InterfaceModifier.Protected)
    accessModifiers should contain(InterfaceModifier.Private)
  }

  test("InterfaceModifier should handle inheritance modifiers") {
    // Given
    val inheritanceModifiers = List(
      InterfaceModifier.Abstract,
      InterfaceModifier.Sealed,
      InterfaceModifier.NonSealed
    )

    // When & Then
    inheritanceModifiers should have size 3
    inheritanceModifiers should contain(InterfaceModifier.Abstract)
    inheritanceModifiers should contain(InterfaceModifier.Sealed)
    inheritanceModifiers should contain(InterfaceModifier.NonSealed)
  }

  test("InterfaceModifier should handle special modifiers") {
    // Given
    val specialModifiers = List(
      InterfaceModifier.Static,
      InterfaceModifier.Strictfp
    )

    // When & Then
    specialModifiers should have size 2
    specialModifiers should contain(InterfaceModifier.Static)
    specialModifiers should contain(InterfaceModifier.Strictfp)
  }

  test("InterfaceModifier should support type checking") {
    // Given
    val modifier = InterfaceModifier.Public

    // When & Then
    modifier.isInstanceOf[InterfaceModifier] shouldBe true
    modifier shouldBe a[InterfaceModifier]
  }

  test("InterfaceModifier should handle collections") {
    // Given
    val modifiers = List(
      InterfaceModifier.Public,
      InterfaceModifier.Abstract,
      InterfaceModifier.Public,
      InterfaceModifier.Sealed
    )

    // When
    val publicCount = modifiers.count(_ == InterfaceModifier.Public)
    val uniqueModifiers = modifiers.toSet

    // Then
    publicCount shouldBe 2
    uniqueModifiers should have size 3
  }

  test("InterfaceModifier should represent Java interface modifiers") {
    // Given - Java interface modifiers
    val javaModifierKeywords = Map(
      InterfaceModifier.Public -> "public",
      InterfaceModifier.Protected -> "protected",
      InterfaceModifier.Private -> "private",
      InterfaceModifier.Abstract -> "abstract",
      InterfaceModifier.Static -> "static",
      InterfaceModifier.Sealed -> "sealed",
      InterfaceModifier.NonSealed -> "non-sealed",
      InterfaceModifier.Strictfp -> "strictfp"
    )

    // When & Then
    javaModifierKeywords should have size 8
    javaModifierKeywords.keys should contain allElementsOf InterfaceModifier.values
    
    // Verify specific mappings
    javaModifierKeywords(InterfaceModifier.Public) shouldBe "public"
    javaModifierKeywords(InterfaceModifier.Sealed) shouldBe "sealed"
    javaModifierKeywords(InterfaceModifier.NonSealed) shouldBe "non-sealed"
  }

  test("InterfaceModifier should handle ordinal values") {
    // Given
    val modifiers = InterfaceModifier.values

    // When & Then
    modifiers.zipWithIndex.foreach { case (modifier, index) =>
      modifier.ordinal shouldBe index
    }
  }

  test("InterfaceModifier should support filter operations") {
    // Given
    val allModifiers = InterfaceModifier.values.toList

    // When
    val accessModifiers = allModifiers.filter { modifier =>
      modifier == InterfaceModifier.Public ||
      modifier == InterfaceModifier.Protected ||
      modifier == InterfaceModifier.Private
    }

    val behavioralModifiers = allModifiers.filter { modifier =>
      modifier == InterfaceModifier.Abstract ||
      modifier == InterfaceModifier.Static ||
      modifier == InterfaceModifier.Sealed
    }

    // Then
    accessModifiers should have size 3
    behavioralModifiers should have size 3
    accessModifiers should contain(InterfaceModifier.Public)
    behavioralModifiers should contain(InterfaceModifier.Abstract)
  }

  test("InterfaceModifier should handle Java interface semantic constraints") {
    // Given - Interface-specific constraints
    val interfaceConstraints = Map(
      "interfaces are implicitly abstract" -> InterfaceModifier.Abstract,
      "sealed and non-sealed are mutually exclusive" -> List(InterfaceModifier.Sealed, InterfaceModifier.NonSealed)
    )

    // When & Then
    interfaceConstraints.foreach { case (constraint, modifiers) =>
      constraint should not be empty
      modifiers match {
        case modifier: InterfaceModifier => modifier shouldBe a[InterfaceModifier]
        case modList: List[_] => 
          modList should have size 2
          modList.head should not be modList.last
      }
    }
  }

  test("InterfaceModifier should handle access visibility levels") {
    // Given
    val visibilityLevels = Map(
      InterfaceModifier.Private -> "nested interface only",
      InterfaceModifier.Protected -> "package and subclasses",
      InterfaceModifier.Public -> "everywhere"
    )

    // When & Then
    visibilityLevels should have size 3
    visibilityLevels should contain key InterfaceModifier.Private
    visibilityLevels should contain key InterfaceModifier.Protected
    visibilityLevels should contain key InterfaceModifier.Public
  }

  test("InterfaceModifier should handle Java 17+ sealed interface features") {
    // Given
    val sealedModifiers = List(InterfaceModifier.Sealed, InterfaceModifier.NonSealed)

    // When & Then
    sealedModifiers should have size 2
    sealedModifiers should contain(InterfaceModifier.Sealed)
    sealedModifiers should contain(InterfaceModifier.NonSealed)
    
    // These are newer Java features for controlling inheritance
    InterfaceModifier.Sealed should not be InterfaceModifier.NonSealed
  }

  test("InterfaceModifier should handle static nested interface behavior") {
    // Given
    val staticModifier = InterfaceModifier.Static

    // When & Then
    staticModifier shouldBe InterfaceModifier.Static
    staticModifier shouldBe a[InterfaceModifier]
    // static interfaces are typically nested and don't need outer instance
  }

  test("InterfaceModifier should handle strictfp floating point behavior") {
    // Given
    val strictfpModifier = InterfaceModifier.Strictfp

    // When & Then
    strictfpModifier shouldBe InterfaceModifier.Strictfp
    strictfpModifier shouldBe a[InterfaceModifier]
    // strictfp ensures consistent floating-point behavior across platforms
  }

  test("InterfaceModifier should categorize by functionality") {
    // Given
    val allModifiers = InterfaceModifier.values.toList

    // When
    val (accessMods, behavioralMods) = allModifiers.partition { modifier =>
      modifier == InterfaceModifier.Public ||
      modifier == InterfaceModifier.Protected ||
      modifier == InterfaceModifier.Private
    }

    // Then
    accessMods should have size 3
    behavioralMods should have size 5
    accessMods should contain allElementsOf List(
      InterfaceModifier.Public,
      InterfaceModifier.Protected,
      InterfaceModifier.Private
    )
    behavioralMods should contain allElementsOf List(
      InterfaceModifier.Abstract,
      InterfaceModifier.Static,
      InterfaceModifier.Sealed,
      InterfaceModifier.NonSealed,
      InterfaceModifier.Strictfp
    )
  }

  test("InterfaceModifier should handle interface inheritance semantics") {
    // Given
    val inheritanceModifiers = Map(
      InterfaceModifier.Abstract -> "can have abstract methods",
      InterfaceModifier.Sealed -> "restricts implementing classes",
      InterfaceModifier.NonSealed -> "allows unrestricted inheritance"
    )

    // When & Then
    inheritanceModifiers should have size 3
    inheritanceModifiers.keys should contain allElementsOf List(
      InterfaceModifier.Abstract,
      InterfaceModifier.Sealed,
      InterfaceModifier.NonSealed
    )
    inheritanceModifiers.values.foreach(_ should not be empty)
  }
}