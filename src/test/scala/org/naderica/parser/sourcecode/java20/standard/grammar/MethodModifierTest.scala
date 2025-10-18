package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MethodModifierTest extends AnyFunSuite with Matchers {

  test("MethodModifier should have all access modifier cases") {
    // Given & When
    val modifiers = MethodModifier.values.toList

    // Then
    modifiers should contain(MethodModifier.Public)
    modifiers should contain(MethodModifier.Protected)
    modifiers should contain(MethodModifier.Private)
  }

  test("MethodModifier should have all behavioral modifier cases") {
    // Given & When
    val modifiers = MethodModifier.values.toList

    // Then
    modifiers should contain(MethodModifier.Abstract)
    modifiers should contain(MethodModifier.Static)
    modifiers should contain(MethodModifier.Final)
    modifiers should contain(MethodModifier.Synchronized)
    modifiers should contain(MethodModifier.Native)
    modifiers should contain(MethodModifier.Strictfp)
  }

  test("MethodModifier should have exactly 9 modifiers") {
    // Given & When
    val modifiers = MethodModifier.values

    // Then
    modifiers should have size 9
  }

  test("MethodModifier should support equality comparison") {
    // Given
    val public1 = MethodModifier.Public
    val public2 = MethodModifier.Public
    val private1 = MethodModifier.Private

    // When & Then
    public1 shouldBe public2
    public1 should not be private1
    public1.hashCode() shouldBe public2.hashCode()
  }

  test("MethodModifier should have proper toString representation") {
    // Given
    val modifiers = List(
      MethodModifier.Public,
      MethodModifier.Protected,
      MethodModifier.Private,
      MethodModifier.Abstract,
      MethodModifier.Static,
      MethodModifier.Final
    )

    // When & Then
    modifiers.foreach { modifier =>
      val modifierString = modifier.toString
      modifierString should not be empty
      modifierString should include(modifier.productPrefix)
    }
  }

  test("MethodModifier should support pattern matching") {
    // Given
    val modifiers = MethodModifier.values.toList

    // When & Then
    modifiers.foreach { modifier =>
      val result = modifier match {
        case MethodModifier.Public => "public"
        case MethodModifier.Protected => "protected"
        case MethodModifier.Private => "private"
        case MethodModifier.Abstract => "abstract"
        case MethodModifier.Static => "static"
        case MethodModifier.Final => "final"
        case MethodModifier.Synchronized => "synchronized"
        case MethodModifier.Native => "native"
        case MethodModifier.Strictfp => "strictfp"
      }
      result should not be empty
    }
  }

  test("MethodModifier should handle access modifiers") {
    // Given
    val accessModifiers = List(
      MethodModifier.Public,
      MethodModifier.Protected,
      MethodModifier.Private
    )

    // When & Then
    accessModifiers should have size 3
    accessModifiers should contain(MethodModifier.Public)
    accessModifiers should contain(MethodModifier.Protected)
    accessModifiers should contain(MethodModifier.Private)
  }

  test("MethodModifier should handle implementation modifiers") {
    // Given
    val implementationModifiers = List(
      MethodModifier.Abstract,
      MethodModifier.Final,
      MethodModifier.Native
    )

    // When & Then
    implementationModifiers should have size 3
    implementationModifiers should contain(MethodModifier.Abstract)
    implementationModifiers should contain(MethodModifier.Final)
    implementationModifiers should contain(MethodModifier.Native)
  }

  test("MethodModifier should handle concurrency modifiers") {
    // Given
    val concurrencyModifiers = List(
      MethodModifier.Synchronized
    )

    // When & Then
    concurrencyModifiers should have size 1
    concurrencyModifiers should contain(MethodModifier.Synchronized)
  }

  test("MethodModifier should handle special modifiers") {
    // Given
    val specialModifiers = List(
      MethodModifier.Static,
      MethodModifier.Strictfp
    )

    // When & Then
    specialModifiers should have size 2
    specialModifiers should contain(MethodModifier.Static)
    specialModifiers should contain(MethodModifier.Strictfp)
  }

  test("MethodModifier should support type checking") {
    // Given
    val modifier = MethodModifier.Public

    // When & Then
    modifier.isInstanceOf[MethodModifier] shouldBe true
    modifier shouldBe a[MethodModifier]
  }

  test("MethodModifier should handle collections") {
    // Given
    val modifiers = List(
      MethodModifier.Public,
      MethodModifier.Static,
      MethodModifier.Public,
      MethodModifier.Final
    )

    // When
    val publicCount = modifiers.count(_ == MethodModifier.Public)
    val uniqueModifiers = modifiers.toSet

    // Then
    publicCount shouldBe 2
    uniqueModifiers should have size 3
  }

  test("MethodModifier should represent Java method modifiers") {
    // Given - Java method modifiers
    val javaModifierKeywords = Map(
      MethodModifier.Public -> "public",
      MethodModifier.Protected -> "protected",
      MethodModifier.Private -> "private",
      MethodModifier.Abstract -> "abstract",
      MethodModifier.Static -> "static",
      MethodModifier.Final -> "final",
      MethodModifier.Synchronized -> "synchronized",
      MethodModifier.Native -> "native",
      MethodModifier.Strictfp -> "strictfp"
    )

    // When & Then
    javaModifierKeywords should have size 9
    javaModifierKeywords.keys should contain allElementsOf MethodModifier.values
    
    // Verify specific mappings
    javaModifierKeywords(MethodModifier.Public) shouldBe "public"
    javaModifierKeywords(MethodModifier.Synchronized) shouldBe "synchronized"
    javaModifierKeywords(MethodModifier.Native) shouldBe "native"
  }

  test("MethodModifier should handle ordinal values") {
    // Given
    val modifiers = MethodModifier.values

    // When & Then
    modifiers.zipWithIndex.foreach { case (modifier, index) =>
      modifier.ordinal shouldBe index
    }
  }

  test("MethodModifier should support filter operations") {
    // Given
    val allModifiers = MethodModifier.values.toList

    // When
    val accessModifiers = allModifiers.filter { modifier =>
      modifier == MethodModifier.Public ||
      modifier == MethodModifier.Protected ||
      modifier == MethodModifier.Private
    }

    val behavioralModifiers = allModifiers.filter { modifier =>
      modifier == MethodModifier.Abstract ||
      modifier == MethodModifier.Static ||
      modifier == MethodModifier.Final
    }

    // Then
    accessModifiers should have size 3
    behavioralModifiers should have size 3
    accessModifiers should contain(MethodModifier.Public)
    behavioralModifiers should contain(MethodModifier.Abstract)
  }

  test("MethodModifier should handle Java semantic constraints") {
    // Given - Some modifiers are mutually exclusive for methods
    val mutuallyExclusiveConstraints = Map(
      "abstract cannot be final" -> (MethodModifier.Abstract, MethodModifier.Final),
      "abstract cannot be static" -> (MethodModifier.Abstract, MethodModifier.Static),
      "abstract cannot be synchronized" -> (MethodModifier.Abstract, MethodModifier.Synchronized),
      "abstract cannot be native" -> (MethodModifier.Abstract, MethodModifier.Native)
    )

    // When & Then
    mutuallyExclusiveConstraints.foreach { case (constraint, (mod1, mod2)) =>
      mod1 should not be mod2
      constraint should not be empty
    }
  }

  test("MethodModifier should handle access visibility levels") {
    // Given
    val visibilityLevels = Map(
      MethodModifier.Private -> "class only",
      MethodModifier.Protected -> "package and subclasses",
      MethodModifier.Public -> "everywhere"
    )

    // When & Then
    visibilityLevels should have size 3
    visibilityLevels should contain key MethodModifier.Private
    visibilityLevels should contain key MethodModifier.Protected
    visibilityLevels should contain key MethodModifier.Public
  }

  test("MethodModifier should handle native method behavior") {
    // Given
    val nativeModifier = MethodModifier.Native

    // When & Then
    nativeModifier shouldBe MethodModifier.Native
    nativeModifier shouldBe a[MethodModifier]
    // native methods are implemented in platform-specific code (C/C++)
  }

  test("MethodModifier should handle synchronized thread safety") {
    // Given
    val synchronizedModifier = MethodModifier.Synchronized

    // When & Then
    synchronizedModifier shouldBe MethodModifier.Synchronized
    synchronizedModifier shouldBe a[MethodModifier]
    // synchronized methods acquire intrinsic lock before execution
  }

  test("MethodModifier should handle strictfp floating point behavior") {
    // Given
    val strictfpModifier = MethodModifier.Strictfp

    // When & Then
    strictfpModifier shouldBe MethodModifier.Strictfp
    strictfpModifier shouldBe a[MethodModifier]
    // strictfp ensures consistent floating-point behavior across platforms
  }

  test("MethodModifier should categorize by functionality") {
    // Given
    val allModifiers = MethodModifier.values.toList

    // When
    val (accessMods, behavioralMods) = allModifiers.partition { modifier =>
      modifier == MethodModifier.Public ||
      modifier == MethodModifier.Protected ||
      modifier == MethodModifier.Private
    }

    // Then
    accessMods should have size 3
    behavioralMods should have size 6
    accessMods should contain allElementsOf List(
      MethodModifier.Public,
      MethodModifier.Protected,
      MethodModifier.Private
    )
    behavioralMods should contain allElementsOf List(
      MethodModifier.Abstract,
      MethodModifier.Static,
      MethodModifier.Final,
      MethodModifier.Synchronized,
      MethodModifier.Native,
      MethodModifier.Strictfp
    )
  }
}