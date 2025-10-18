package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class UnannTypeVariableTest extends AnyFunSuite with Matchers {

  test("UnannTypeVariable should create instance with TypeIdentifier") {
    val typeIdentifier = TypeIdentifier("T")
    val unannTypeVariable = UnannTypeVariable(typeIdentifier)
    unannTypeVariable.typeIdentifier shouldBe typeIdentifier
  }

  test("UnannTypeVariable should support copy with modified TypeIdentifier") {
    val original = UnannTypeVariable(TypeIdentifier("T"))
    val modified = original.copy(typeIdentifier = TypeIdentifier("U"))
    modified.typeIdentifier shouldBe TypeIdentifier("U")
    original.typeIdentifier shouldBe TypeIdentifier("T")
  }

  test("UnannTypeVariable equality should work correctly") {
    val var1 = UnannTypeVariable(TypeIdentifier("T"))
    val var2 = UnannTypeVariable(TypeIdentifier("T"))
    val var3 = UnannTypeVariable(TypeIdentifier("U"))

    var1 shouldEqual var2
    var1 should not equal var3
  }

  test("UnannTypeVariable toString should be readable") {
    val unannTypeVariable = UnannTypeVariable(TypeIdentifier("GenericType"))
    unannTypeVariable.toString should include("UnannTypeVariable")
    unannTypeVariable.toString should include("GenericType")
  }

  test("UnannTypeVariable should handle Java type variable conventions") {
    val singleLetter = UnannTypeVariable(TypeIdentifier("T"))
    val descriptive = UnannTypeVariable(TypeIdentifier("Element"))
    val bounded = UnannTypeVariable(TypeIdentifier("K"))
    val wildcardStyle = UnannTypeVariable(TypeIdentifier("ELEMENT"))

    singleLetter.typeIdentifier.name shouldBe "T"
    descriptive.typeIdentifier.name shouldBe "Element"
    bounded.typeIdentifier.name shouldBe "K"
    wildcardStyle.typeIdentifier.name shouldBe "ELEMENT"
  }

  test("UnannTypeVariable should support pattern matching") {
    val typeVar = UnannTypeVariable(TypeIdentifier("T"))
    
    val result = typeVar match {
      case UnannTypeVariable(TypeIdentifier("T")) => "type parameter T"
      case UnannTypeVariable(TypeIdentifier("U")) => "type parameter U"
      case UnannTypeVariable(TypeIdentifier(name)) => s"type parameter $name"
    }
    
    result shouldBe "type parameter T"
  }

  test("UnannTypeVariable should handle common Java generic type parameter names") {
    val typeParam = UnannTypeVariable(TypeIdentifier("T"))
    val elementParam = UnannTypeVariable(TypeIdentifier("E"))
    val keyParam = UnannTypeVariable(TypeIdentifier("K"))
    val valueParam = UnannTypeVariable(TypeIdentifier("V"))
    val numberParam = UnannTypeVariable(TypeIdentifier("N"))

    typeParam.typeIdentifier.name shouldBe "T"
    elementParam.typeIdentifier.name shouldBe "E"
    keyParam.typeIdentifier.name shouldBe "K"
    valueParam.typeIdentifier.name shouldBe "V"
    numberParam.typeIdentifier.name shouldBe "N"
  }

  test("UnannTypeVariable should handle multi-character type parameter names") {
    val descriptiveParam = UnannTypeVariable(TypeIdentifier("Element"))
    val abbreviationParam = UnannTypeVariable(TypeIdentifier("Elem"))
    val numericalParam = UnannTypeVariable(TypeIdentifier("T1"))

    descriptiveParam.typeIdentifier.name shouldBe "Element"
    abbreviationParam.typeIdentifier.name shouldBe "Elem"
    numericalParam.typeIdentifier.name shouldBe "T1"
  }

  test("UnannTypeVariable should handle null safety") {
    val unannTypeVariable = UnannTypeVariable(TypeIdentifier("T"))
    
    unannTypeVariable should not be null
    unannTypeVariable.typeIdentifier should not be null
  }

  test("UnannTypeVariable should represent unannotated type variables correctly") {
    // UnannTypeVariable represents type variables without annotations
    val plainTypeVar = UnannTypeVariable(TypeIdentifier("T"))
    val customTypeVar = UnannTypeVariable(TypeIdentifier("CustomType"))

    // Should maintain the type identifier exactly
    plainTypeVar.typeIdentifier shouldBe TypeIdentifier("T")
    customTypeVar.typeIdentifier shouldBe TypeIdentifier("CustomType")
    
    // Should be distinguishable from each other
    plainTypeVar should not equal customTypeVar
  }

  test("UnannTypeVariable should handle edge cases with special names") {
    val underscoreParam = UnannTypeVariable(TypeIdentifier("_"))
    val dollarParam = UnannTypeVariable(TypeIdentifier("$T"))
    val unicodeParam = UnannTypeVariable(TypeIdentifier("Tÿpe"))

    underscoreParam.typeIdentifier.name shouldBe "_"
    dollarParam.typeIdentifier.name shouldBe "$T"
    unicodeParam.typeIdentifier.name shouldBe "Tÿpe"
  }

  test("UnannTypeVariable should handle hashCode and equals contract") {
    val var1 = UnannTypeVariable(TypeIdentifier("T"))
    val var2 = UnannTypeVariable(TypeIdentifier("T"))
    val var3 = UnannTypeVariable(TypeIdentifier("U"))

    // Equal objects should have equal hash codes
    if (var1 == var2) {
      var1.hashCode shouldEqual var2.hashCode
    }
    
    // Different objects may have different hash codes
    var1.hashCode should not equal var3.hashCode
  }

  test("UnannTypeVariable should support reflexivity, symmetry, and transitivity") {
    val var1 = UnannTypeVariable(TypeIdentifier("T"))
    val var2 = UnannTypeVariable(TypeIdentifier("T"))
    val var3 = UnannTypeVariable(TypeIdentifier("T"))

    // Reflexivity
    var1 shouldEqual var1

    // Symmetry
    if (var1 == var2) {
      var2 shouldEqual var1
    }

    // Transitivity
    if (var1 == var2 && var2 == var3) {
      var1 shouldEqual var3
    }
  }

  test("UnannTypeVariable should represent Java generics type system correctly") {
    // Should be suitable for use in generic type declarations
    val listElementType = UnannTypeVariable(TypeIdentifier("E"))
    val mapKeyType = UnannTypeVariable(TypeIdentifier("K"))
    val mapValueType = UnannTypeVariable(TypeIdentifier("V"))

    // Should preserve type parameter semantics
    listElementType.typeIdentifier.name shouldBe "E"
    mapKeyType.typeIdentifier.name shouldBe "K"
    mapValueType.typeIdentifier.name shouldBe "V"

    // Each should be distinct
    listElementType should not equal mapKeyType
    mapKeyType should not equal mapValueType
    listElementType should not equal mapValueType
  }

  test("UnannTypeVariable should work in collection contexts") {
    val typeVars = List(
      UnannTypeVariable(TypeIdentifier("T")),
      UnannTypeVariable(TypeIdentifier("U")),
      UnannTypeVariable(TypeIdentifier("V"))
    )

    typeVars should have size 3
    typeVars.map(_.typeIdentifier.name) shouldBe List("T", "U", "V")
    
    val distinct = typeVars.distinct
    distinct should have size 3
  }

  test("UnannTypeVariable should support functional operations") {
    val typeVar = UnannTypeVariable(TypeIdentifier("T"))
    
    // Should work with map operations on collections
    val typeVars = List(typeVar)
    val names = typeVars.map(_.typeIdentifier.name)
    names shouldBe List("T")
    
    // Should work with filter operations
    val filtered = typeVars.filter(_.typeIdentifier.name == "T")
    filtered should contain(typeVar)
  }
}