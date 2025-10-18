package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ClassModifierTest extends AnyFunSuite with Matchers {

  test("ClassModifier should have all access modifier cases") {
    // Given & When
    val modifiers = ClassModifier.values.toList

    // Then
    modifiers should contain(ClassModifier.Public)
    modifiers should contain(ClassModifier.Protected)
    modifiers should contain(ClassModifier.Private)
  }

  test("ClassModifier should have all behavioral modifier cases") {
    // Given & When
    val modifiers = ClassModifier.values.toList

    // Then
    modifiers should contain(ClassModifier.Abstract)
    modifiers should contain(ClassModifier.Static)
    modifiers should contain(ClassModifier.Final)
    modifiers should contain(ClassModifier.Sealed)
    modifiers should contain(ClassModifier.NonSealed)
    modifiers should contain(ClassModifier.Strictfp)
  }

  test("ClassModifier should have exactly 9 modifiers") {
    // Given & When
    val modifiers = ClassModifier.values

    // Then
    modifiers should have size 9
  }

  test("ClassModifier should support equality comparison") {
    // Given
    val public1 = ClassModifier.Public
    val public2 = ClassModifier.Public
    val private1 = ClassModifier.Private

    // When & Then
    public1 shouldBe public2
    public1 should not be private1
    public1.hashCode() shouldBe public2.hashCode()
  }

  test("ClassModifier should have proper toString representation") {
    // Given
    val modifiers = List(
      ClassModifier.Public,
      ClassModifier.Protected,
      ClassModifier.Private,
      ClassModifier.Abstract,
      ClassModifier.Static,
      ClassModifier.Final
    )

    // When & Then
    modifiers.foreach { modifier =>
      val modifierString = modifier.toString
      modifierString should not be empty
      modifierString should include(modifier.productPrefix)
    }
  }

  test("ClassModifier should support pattern matching") {
    // Given
    val modifiers = ClassModifier.values.toList

    // When & Then
    modifiers.foreach { modifier =>
      val result = modifier match {
        case ClassModifier.Public => "public"
        case ClassModifier.Protected => "protected"
        case ClassModifier.Private => "private"
        case ClassModifier.Abstract => "abstract"
        case ClassModifier.Static => "static"
        case ClassModifier.Final => "final"
        case ClassModifier.Sealed => "sealed"
        case ClassModifier.NonSealed => "non-sealed"
        case ClassModifier.Strictfp => "strictfp"
      }
      result should not be empty
    }
  }

  test("ClassModifier should handle access modifiers") {
    // Given
    val accessModifiers = List(
      ClassModifier.Public,
      ClassModifier.Protected,
      ClassModifier.Private
    )

    // When & Then
    accessModifiers should have size 3
    accessModifiers should contain(ClassModifier.Public)
    accessModifiers should contain(ClassModifier.Protected)
    accessModifiers should contain(ClassModifier.Private)
  }

  test("ClassModifier should handle inheritance modifiers") {
    // Given
    val inheritanceModifiers = List(
      ClassModifier.Abstract,
      ClassModifier.Final,
      ClassModifier.Sealed,
      ClassModifier.NonSealed
    )

    // When & Then
    inheritanceModifiers should have size 4
    inheritanceModifiers should contain(ClassModifier.Abstract)
    inheritanceModifiers should contain(ClassModifier.Final)
    inheritanceModifiers should contain(ClassModifier.Sealed)
    inheritanceModifiers should contain(ClassModifier.NonSealed)
  }

  test("ClassModifier should handle special modifiers") {
    // Given
    val specialModifiers = List(
      ClassModifier.Static,
      ClassModifier.Strictfp
    )

    // When & Then
    specialModifiers should have size 2
    specialModifiers should contain(ClassModifier.Static)
    specialModifiers should contain(ClassModifier.Strictfp)
  }

  test("ClassModifier should support type checking") {
    // Given
    val modifier = ClassModifier.Public

    // When & Then
    modifier.isInstanceOf[ClassModifier] shouldBe true
    modifier shouldBe a[ClassModifier]
  }

  test("ClassModifier should handle collections") {
    // Given
    val modifiers = List(
      ClassModifier.Public,
      ClassModifier.Static,
      ClassModifier.Public,
      ClassModifier.Final
    )

    // When
    val publicCount = modifiers.count(_ == ClassModifier.Public)
    val uniqueModifiers = modifiers.toSet

    // Then
    publicCount shouldBe 2
    uniqueModifiers should have size 3
  }

  test("ClassModifier should represent Java class modifiers") {
    // Given - Java class modifiers
    val javaModifierKeywords = Map(
      ClassModifier.Public -> "public",
      ClassModifier.Protected -> "protected",
      ClassModifier.Private -> "private",
      ClassModifier.Abstract -> "abstract",
      ClassModifier.Static -> "static",
      ClassModifier.Final -> "final",
      ClassModifier.Sealed -> "sealed",
      ClassModifier.NonSealed -> "non-sealed",
      ClassModifier.Strictfp -> "strictfp"
    )

    // When & Then
    javaModifierKeywords should have size 9
    javaModifierKeywords.keys should contain allElementsOf ClassModifier.values
    
    // Verify specific mappings
    javaModifierKeywords(ClassModifier.Public) shouldBe "public"
    javaModifierKeywords(ClassModifier.Abstract) shouldBe "abstract"
    javaModifierKeywords(ClassModifier.NonSealed) shouldBe "non-sealed"
  }

  test("ClassModifier should handle ordinal values") {
    // Given
    val modifiers = ClassModifier.values

    // When & Then
    modifiers.zipWithIndex.foreach { case (modifier, index) =>
      modifier.ordinal shouldBe index
    }
  }

  test("ClassModifier should support filter operations") {
    // Given
    val allModifiers = ClassModifier.values.toList

    // When
    val accessModifiers = allModifiers.filter { modifier =>
      modifier == ClassModifier.Public ||
      modifier == ClassModifier.Protected ||
      modifier == ClassModifier.Private
    }

    val behavioralModifiers = allModifiers.filter { modifier =>
      modifier == ClassModifier.Abstract ||
      modifier == ClassModifier.Static ||
      modifier == ClassModifier.Final
    }

    // Then
    accessModifiers should have size 3
    behavioralModifiers should have size 3
    accessModifiers should contain(ClassModifier.Public)
    behavioralModifiers should contain(ClassModifier.Abstract)
  }

  test("ClassModifier should handle Java semantic constraints") {
    // Given - Some modifiers are mutually exclusive
    val mutuallyExclusiveGroups = List(
      List(ClassModifier.Abstract, ClassModifier.Final), // abstract and final are mutually exclusive
      List(ClassModifier.Sealed, ClassModifier.NonSealed) // sealed and non-sealed are mutually exclusive
    )

    // When & Then
    mutuallyExclusiveGroups.foreach { group =>
      group should have size 2
      group.head should not be group.last
    }
    
    // Verify specific constraints
    ClassModifier.Abstract should not be ClassModifier.Final
    ClassModifier.Sealed should not be ClassModifier.NonSealed
  }

  test("ClassModifier should handle access visibility levels") {
    // Given
    val visibilityLevels = Map(
      ClassModifier.Private -> "class only",
      ClassModifier.Protected -> "package and subclasses",
      ClassModifier.Public -> "everywhere"
    )

    // When & Then
    visibilityLevels should have size 3
    visibilityLevels should contain key ClassModifier.Private
    visibilityLevels should contain key ClassModifier.Protected
    visibilityLevels should contain key ClassModifier.Public
  }

  test("ClassModifier should handle Java 17+ sealed class features") {
    // Given
    val sealedModifiers = List(ClassModifier.Sealed, ClassModifier.NonSealed)

    // When & Then
    sealedModifiers should have size 2
    sealedModifiers should contain(ClassModifier.Sealed)
    sealedModifiers should contain(ClassModifier.NonSealed)
    
    // These are newer Java features
    ClassModifier.Sealed should not be ClassModifier.NonSealed
  }

  test("ClassModifier should handle strictfp floating point behavior") {
    // Given
    val strictfpModifier = ClassModifier.Strictfp

    // When & Then
    strictfpModifier shouldBe ClassModifier.Strictfp
    strictfpModifier shouldBe a[ClassModifier]
    // strictfp ensures consistent floating-point behavior across platforms
  }
}