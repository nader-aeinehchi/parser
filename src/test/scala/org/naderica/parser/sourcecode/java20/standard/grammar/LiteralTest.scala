package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class LiteralTest extends AnyFunSuite with Matchers {

  test("Literal should have IntegerLiteral case class") {
    // Given & When
    val intLiteral = IntegerLiteral("42")

    // Then
    intLiteral shouldBe a[Literal]
    intLiteral shouldBe an[IntegerLiteral]
    intLiteral.value shouldBe "42"
  }

  test("Literal should have FloatingPointLiteral case class") {
    // Given & When
    val floatLiteral = FloatingPointLiteral("3.14")

    // Then
    floatLiteral shouldBe a[Literal]
    floatLiteral shouldBe a[FloatingPointLiteral]
    floatLiteral.value shouldBe "3.14"
  }

  test("Literal should have BooleanLiteral case class") {
    // Given & When
    val trueLiteral = BooleanLiteral(true)
    val falseLiteral = BooleanLiteral(false)

    // Then
    trueLiteral shouldBe a[Literal]
    trueLiteral shouldBe a[BooleanLiteral]
    trueLiteral.value shouldBe true
    falseLiteral.value shouldBe false
  }

  test("Literal should have CharacterLiteral case class") {
    // Given & When
    val charLiteral = CharacterLiteral("'a'")

    // Then
    charLiteral shouldBe a[Literal]
    charLiteral shouldBe a[CharacterLiteral]
    charLiteral.value shouldBe "'a'"
  }

  test("Literal should have StringLiteral case class") {
    // Given & When
    val stringLiteral = StringLiteral("\"hello\"")

    // Then
    stringLiteral shouldBe a[Literal]
    stringLiteral shouldBe a[StringLiteral]
    stringLiteral.value shouldBe "\"hello\""
  }

  test("Literal should have TextBlock case class") {
    // Given & When
    val textBlock = TextBlock("\"\"\"multi\nline\ntext\"\"\"")

    // Then
    textBlock shouldBe a[Literal]
    textBlock shouldBe a[TextBlock]
    textBlock.value shouldBe "\"\"\"multi\nline\ntext\"\"\""
  }

  test("Literal should have NullLiteral case object") {
    // Given & When
    val nullLiteral = NullLiteral

    // Then
    nullLiteral shouldBe a[Literal]
    nullLiteral shouldBe NullLiteral
  }

  test("Literal case classes should support equality comparison") {
    // Given
    val int1 = IntegerLiteral("42")
    val int2 = IntegerLiteral("42")
    val int3 = IntegerLiteral("24")

    // When & Then
    int1 shouldBe int2
    int1 should not be int3
    int1.hashCode() shouldBe int2.hashCode()
  }

  test("Literal should have proper toString representation") {
    // Given
    val literals = List(
      IntegerLiteral("123"),
      FloatingPointLiteral("1.23"),
      BooleanLiteral(true),
      CharacterLiteral("'x'"),
      StringLiteral("\"test\""),
      TextBlock("\"\"\"block\"\"\""),
      NullLiteral
    )

    // When & Then
    literals.foreach { literal =>
      val literalString = literal.toString
      literalString should not be empty
      literal match {
        case IntegerLiteral(value) => literalString should include(value)
        case FloatingPointLiteral(value) => literalString should include(value)
        case BooleanLiteral(value) => literalString should include(value.toString)
        case CharacterLiteral(value) => literalString should include(value)
        case StringLiteral(value) => literalString should include(value)
        case TextBlock(value) => literalString should include("TextBlock")
        case NullLiteral => literalString should include("NullLiteral")
      }
    }
  }

  test("Literal sealed trait should support pattern matching") {
    // Given
    val literals: List[Literal] = List(
      IntegerLiteral("42"),
      FloatingPointLiteral("3.14"),
      BooleanLiteral(true),
      CharacterLiteral("'a'"),
      StringLiteral("\"hello\""),
      TextBlock("\"\"\"text\"\"\""),
      NullLiteral
    )

    // When & Then
    literals.foreach { literal =>
      literal match {
        case IntegerLiteral(value) =>
          value should not be empty
        case FloatingPointLiteral(value) =>
          value should not be empty
        case BooleanLiteral(value) =>
          value shouldBe a[Boolean]
        case CharacterLiteral(value) =>
          value should not be empty
        case StringLiteral(value) =>
          value should not be empty
        case TextBlock(value) =>
          value should not be empty
        case NullLiteral =>
          literal shouldBe NullLiteral
      }
    }
  }

  test("Literal should handle type checking") {
    // Given
    val intLiteral: Literal = IntegerLiteral("42")
    val nullLiteral: Literal = NullLiteral

    // When & Then
    intLiteral.isInstanceOf[Literal] shouldBe true
    nullLiteral.isInstanceOf[Literal] shouldBe true
    intLiteral.isInstanceOf[IntegerLiteral] shouldBe true
    nullLiteral.isInstanceOf[NullLiteral.type] shouldBe true
  }

  test("Literal should support copy operations for case classes") {
    // Given
    val original = IntegerLiteral("42")

    // When
    val copied = original.copy(value = "24")

    // Then
    copied.value shouldBe "24"
    original.value shouldBe "42"
    copied should not be original
  }

  test("Literal should handle collections") {
    // Given
    val literals = List(
      IntegerLiteral("1"),
      IntegerLiteral("2"),
      FloatingPointLiteral("1.0"),
      NullLiteral,
      NullLiteral
    )

    // When
    val integerCount = literals.count(_.isInstanceOf[IntegerLiteral])
    val nullCount = literals.count(_ == NullLiteral)

    // Then
    integerCount shouldBe 2
    nullCount shouldBe 2
    literals should have size 5
  }

  test("Literal should represent Java literal values") {
    // Given - Java literal types
    val javaLiterals = Map(
      IntegerLiteral("42") -> "integer literal",
      FloatingPointLiteral("3.14") -> "floating-point literal",
      BooleanLiteral(true) -> "boolean literal",
      CharacterLiteral("'a'") -> "character literal",
      StringLiteral("\"hello\"") -> "string literal",
      TextBlock("\"\"\"text\"\"\"") -> "text block literal",
      NullLiteral -> "null literal"
    )

    // When & Then
    javaLiterals should have size 7
    javaLiterals.keys.foreach(_.shouldBe(a[Literal]))
    javaLiterals(IntegerLiteral("42")) should include("integer")
    javaLiterals(NullLiteral) should include("null")
  }

  test("Literal should handle integer literal formats") {
    // Given
    val integerLiterals = List(
      IntegerLiteral("42"),        // decimal
      IntegerLiteral("0x2A"),      // hexadecimal
      IntegerLiteral("052"),       // octal
      IntegerLiteral("0b101010"),  // binary
      IntegerLiteral("42L"),       // long
      IntegerLiteral("42_000")     // with underscores
    )

    // When & Then
    integerLiterals should have size 6
    integerLiterals.foreach { literal =>
      literal shouldBe an[IntegerLiteral]
      literal.value should not be empty
    }
  }

  test("Literal should handle floating-point literal formats") {
    // Given
    val floatingPointLiterals = List(
      FloatingPointLiteral("3.14"),
      FloatingPointLiteral("3.14f"),
      FloatingPointLiteral("3.14d"),
      FloatingPointLiteral("1e10"),
      FloatingPointLiteral("0x1.fffffffffffffP+1023"), // hex float
      FloatingPointLiteral("3.14_15_92")               // with underscores
    )

    // When & Then
    floatingPointLiterals should have size 6
    floatingPointLiterals.foreach { literal =>
      literal shouldBe a[FloatingPointLiteral]
      literal.value should not be empty
    }
  }

  test("Literal should handle character literal escapes") {
    // Given
    val characterLiterals = List(
      CharacterLiteral("'a'"),      // regular character
      CharacterLiteral("'\\n'"),    // newline escape
      CharacterLiteral("'\\t'"),    // tab escape
      CharacterLiteral("'\\''"),    // single quote escape
      CharacterLiteral("'\\\\'"),   // backslash escape
      CharacterLiteral("'\\u0041'") // unicode escape
    )

    // When & Then
    characterLiterals should have size 6
    characterLiterals.foreach { literal =>
      literal shouldBe a[CharacterLiteral]
      literal.value should not be empty
    }
  }

  test("Literal should handle string literal escapes") {
    // Given
    val stringLiterals = List(
      StringLiteral("\"hello\""),
      StringLiteral("\"hello\\nworld\""),
      StringLiteral("\"tab\\there\""),
      StringLiteral("\"quote\\\"here\""),
      StringLiteral("\"unicode\\u0041\"")
    )

    // When & Then
    stringLiterals should have size 5
    stringLiterals.foreach { literal =>
      literal shouldBe a[StringLiteral]
      literal.value should not be empty
    }
  }

  test("Literal should handle Java 15+ text blocks") {
    // Given
    val textBlocks = List(
      TextBlock("\"\"\"hello world\"\"\""),
      TextBlock("\"\"\"line1\nline2\nline3\"\"\""),
      TextBlock("\"\"\"  indented\n  text\n  block\"\"\"")
    )

    // When & Then
    textBlocks should have size 3
    textBlocks.foreach { literal =>
      literal shouldBe a[TextBlock]
      literal.value should not be empty
    }
    // Text blocks were introduced in Java 15
  }

  test("Literal should handle null literal singleton") {
    // Given
    val null1 = NullLiteral
    val null2 = NullLiteral

    // When & Then
    (null1 eq null2) shouldBe true // same object reference
    null1 shouldBe null2
    null1.hashCode() shouldBe null2.hashCode()
  }

  test("Literal should support filter operations") {
    // Given
    val allLiterals = List(
      IntegerLiteral("1"),
      FloatingPointLiteral("1.0"),
      BooleanLiteral(true),
      StringLiteral("\"text\""),
      NullLiteral
    )

    // When
    val numericLiterals = allLiterals.filter {
      case _: IntegerLiteral | _: FloatingPointLiteral => true
      case _ => false
    }
    val referenceLiterals = allLiterals.filter {
      case _: StringLiteral | NullLiteral => true
      case _ => false
    }

    // Then
    numericLiterals should have size 2
    referenceLiterals should have size 2
    numericLiterals.head shouldBe an[IntegerLiteral]
    referenceLiterals should contain(NullLiteral)
  }

  test("Literal should handle compile-time constants") {
    // Given
    val compileTimeConstants = List(
      IntegerLiteral("42"),
      FloatingPointLiteral("3.14"),
      BooleanLiteral(true),
      CharacterLiteral("'a'"),
      StringLiteral("\"constant\""),
      NullLiteral
    )

    // When & Then
    compileTimeConstants.foreach { literal =>
      literal shouldBe a[Literal]
      // All literals are compile-time constants in Java
    }
  }

  test("Literal should handle primitive vs reference types") {
    // Given
    val primitiveTypeLiterals = List(
      IntegerLiteral("42"),
      FloatingPointLiteral("3.14"),
      BooleanLiteral(true),
      CharacterLiteral("'a'")
    )
    val referenceTypeLiterals = List(
      StringLiteral("\"text\""),
      TextBlock("\"\"\"block\"\"\""),
      NullLiteral
    )

    // When & Then
    primitiveTypeLiterals should have size 4
    referenceTypeLiterals should have size 3
    primitiveTypeLiterals.foreach(_.shouldBe(a[Literal]))
    referenceTypeLiterals.foreach(_.shouldBe(a[Literal]))
  }
}