package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class VariableDeclaratorIdTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock dims for testing
  private def createMockDims(count: Int): Dims = {
    Dims(annotations = List.empty, count = count)
  }

  test("VariableDeclaratorId should create instance with identifier and no dims") {
    val identifier = Identifier("testVar")
    val varId = VariableDeclaratorId(identifier, None)
    varId.identifier shouldBe identifier
    varId.dims shouldBe None
  }

  test("VariableDeclaratorId should create instance with identifier and dims") {
    val identifier = Identifier("arrayVar")
    val dims = createMockDims(2)
    val varId = VariableDeclaratorId(identifier, Some(dims))
    varId.identifier shouldBe identifier
    varId.dims shouldBe Some(dims)
  }

  test("VariableDeclaratorId should support equality comparison") {
    val id1 = Identifier("var1")
    val id2 = Identifier("var2")
    val dims1 = createMockDims(1)
    val dims2 = createMockDims(2)
    
    val varId1 = VariableDeclaratorId(id1, None)
    val varId2 = VariableDeclaratorId(id1, None)
    val varId3 = VariableDeclaratorId(id2, None)
    val varId4 = VariableDeclaratorId(id1, Some(dims1))

    varId1 shouldEqual varId2
    varId1 should not equal varId3
    varId1 should not equal varId4
  }

  test("VariableDeclaratorId should handle simple variable names") {
    val simpleVar = VariableDeclaratorId(Identifier("count"), None)
    simpleVar.identifier.name shouldBe "count"
    simpleVar.dims shouldBe None
  }

  test("VariableDeclaratorId should handle array variable names") {
    val arrayVar = VariableDeclaratorId(Identifier("matrix"), Some(createMockDims(2)))
    arrayVar.identifier.name shouldBe "matrix"
    arrayVar.dims should not be None
    arrayVar.dims.get.count shouldBe 2
  }

  test("VariableDeclaratorId should handle variable names with underscores") {
    val underscoreVar = VariableDeclaratorId(Identifier("my_variable"), None)
    underscoreVar.identifier.name shouldBe "my_variable"
    underscoreVar.dims shouldBe None
  }

  test("VariableDeclaratorId should handle variable names with numbers") {
    val numberVar = VariableDeclaratorId(Identifier("var123"), None)
    numberVar.identifier.name shouldBe "var123"
    numberVar.dims shouldBe None
  }

  test("VariableDeclaratorId should have proper toString representation") {
    val identifier = Identifier("testVar")
    val varId = VariableDeclaratorId(identifier, None)
    
    varId.toString should include("VariableDeclaratorId")
    varId.toString should include("testVar")
  }

  test("VariableDeclaratorId case class should support pattern matching") {
    val identifier = Identifier("matchVar")
    val dims = createMockDims(1)
    val varId = VariableDeclaratorId(identifier, Some(dims))

    val result = varId match {
      case VariableDeclaratorId(id, Some(d)) => s"array_${id.name}_${d.count}"
      case VariableDeclaratorId(id, None) => s"simple_${id.name}"
    }

    result shouldBe "array_matchVar_1"
  }

  test("VariableDeclaratorId should work with copy method") {
    val originalId = Identifier("original")
    val newId = Identifier("modified")
    val dims = createMockDims(1)
    val original = VariableDeclaratorId(originalId, None)
    
    val modified = original.copy(identifier = newId, dims = Some(dims))
    modified.identifier shouldBe newId
    modified.dims shouldBe Some(dims)
    original.identifier shouldBe originalId
    original.dims shouldBe None
  }

  test("VariableDeclaratorId should support type checking") {
    val varId = VariableDeclaratorId(Identifier("test"), None)
    varId shouldBe a[VariableDeclaratorId]
  }

  test("VariableDeclaratorId should handle collections") {
    val varIds = List(
      VariableDeclaratorId(Identifier("var1"), None),
      VariableDeclaratorId(Identifier("var2"), Some(createMockDims(1))),
      VariableDeclaratorId(Identifier("var3"), Some(createMockDims(2)))
    )

    varIds should have size 3
    varIds.count(_.dims.isEmpty) shouldBe 1
    varIds.count(_.dims.nonEmpty) shouldBe 2
  }

  test("VariableDeclaratorId should represent Java variable declarator correctly") {
    // Java variable declarators: varName, varName[], varName[][]
    val simpleVar = VariableDeclaratorId(Identifier("count"), None)       // int count
    val arrayVar = VariableDeclaratorId(Identifier("values"), Some(createMockDims(1)))  // int[] values
    val matrixVar = VariableDeclaratorId(Identifier("matrix"), Some(createMockDims(2))) // int[][] matrix

    simpleVar.identifier.name shouldBe "count"
    arrayVar.identifier.name shouldBe "values"
    matrixVar.identifier.name shouldBe "matrix"
    
    simpleVar.dims shouldBe None
    arrayVar.dims.get.count shouldBe 1
    matrixVar.dims.get.count shouldBe 2
  }

  test("VariableDeclaratorId should handle hashCode and equals contract") {
    val id = Identifier("test")
    val varId1 = VariableDeclaratorId(id, None)
    val varId2 = VariableDeclaratorId(id, None)
    val varId3 = VariableDeclaratorId(Identifier("other"), None)

    // Equal objects should have equal hash codes
    if (varId1 == varId2) {
      varId1.hashCode shouldEqual varId2.hashCode
    }
    
    // Different objects should have different hash codes
    varId1.hashCode should not equal varId3.hashCode
  }

  test("VariableDeclaratorId should support reflexivity, symmetry, and transitivity") {
    val id = Identifier("test")
    val varId1 = VariableDeclaratorId(id, None)
    val varId2 = VariableDeclaratorId(id, None)
    val varId3 = VariableDeclaratorId(id, None)

    // Reflexivity
    varId1 shouldEqual varId1

    // Symmetry
    if (varId1 == varId2) {
      varId2 shouldEqual varId1
    }

    // Transitivity
    if (varId1 == varId2 && varId2 == varId3) {
      varId1 shouldEqual varId3
    }
  }

  test("VariableDeclaratorId should work in collection contexts") {
    val varIds = List(
      VariableDeclaratorId(Identifier("a"), None),
      VariableDeclaratorId(Identifier("b"), Some(createMockDims(1))),
      VariableDeclaratorId(Identifier("c"), None)
    )

    varIds should have size 3
    
    val simpleVars = varIds.filter(_.dims.isEmpty)
    simpleVars should have size 2
    
    val arrayVars = varIds.filter(_.dims.nonEmpty)
    arrayVars should have size 1
  }

  test("VariableDeclaratorId should support functional operations") {
    val varIds = List(
      VariableDeclaratorId(Identifier("count"), None),
      VariableDeclaratorId(Identifier("values"), Some(createMockDims(1))),
      VariableDeclaratorId(Identifier("matrix"), Some(createMockDims(2)))
    )
    
    // Map to variable names
    val names = varIds.map(_.identifier.name)
    names should contain("count")
    names should contain("values")
    names should contain("matrix")
    
    // Count array variables
    val arrayCount = varIds.count(_.dims.nonEmpty)
    arrayCount shouldBe 2
    
    // Filter simple variables
    val simpleVars = varIds.filter(_.dims.isEmpty)
    simpleVars should have size 1
  }

  test("VariableDeclaratorId should handle Java variable declaration semantics") {
    // Java variable declarations in different contexts
    val localVar = VariableDeclaratorId(Identifier("localVar"), None)        // int localVar;
    val arrayParam = VariableDeclaratorId(Identifier("args"), Some(createMockDims(1)))  // String[] args
    val fieldVar = VariableDeclaratorId(Identifier("fieldVar"), None)        // private int fieldVar;
    
    localVar.identifier.name shouldBe "localVar"
    arrayParam.identifier.name shouldBe "args"
    fieldVar.identifier.name shouldBe "fieldVar"
    
    localVar.dims shouldBe None
    arrayParam.dims should not be None
    fieldVar.dims shouldBe None
  }

  test("VariableDeclaratorId should preserve variable naming conventions") {
    val camelCase = VariableDeclaratorId(Identifier("camelCaseVar"), None)
    val underscore = VariableDeclaratorId(Identifier("snake_case_var"), None)
    val dollarSign = VariableDeclaratorId(Identifier("var$WithDollar"), None)
    
    camelCase.identifier.name shouldBe "camelCaseVar"
    underscore.identifier.name shouldBe "snake_case_var"
    dollarSign.identifier.name shouldBe "var$WithDollar"
    
    // All should be valid Java identifiers (in terms of structure)
    List(camelCase, underscore, dollarSign).foreach { varId =>
      varId shouldBe a[VariableDeclaratorId]
      varId.identifier.name should not be empty
    }
  }

  test("VariableDeclaratorId should handle array dimension semantics") {
    val scalar = VariableDeclaratorId(Identifier("scalar"), None)           // int scalar
    val oneDim = VariableDeclaratorId(Identifier("array1D"), Some(createMockDims(1)))  // int[] array1D
    val twoDim = VariableDeclaratorId(Identifier("array2D"), Some(createMockDims(2)))  // int[][] array2D
    val threeDim = VariableDeclaratorId(Identifier("array3D"), Some(createMockDims(3))) // int[][][] array3D
    
    scalar.dims shouldBe None
    oneDim.dims.get.count shouldBe 1
    twoDim.dims.get.count shouldBe 2
    threeDim.dims.get.count shouldBe 3
    
    // Array variables should have different dimensions
    List(oneDim, twoDim, threeDim).foreach { arrayVar =>
      arrayVar.dims should not be None
      arrayVar.dims.get.count should be > 0
    }
  }

  test("VariableDeclaratorId should support comprehensive pattern matching") {
    def analyzeVariable(varId: VariableDeclaratorId): String = varId match {
      case VariableDeclaratorId(id, None) => s"scalar variable: ${id.name}"
      case VariableDeclaratorId(id, Some(dims)) if dims.count == 1 => s"1D array: ${id.name}"
      case VariableDeclaratorId(id, Some(dims)) if dims.count == 2 => s"2D array: ${id.name}"
      case VariableDeclaratorId(id, Some(dims)) => s"${dims.count}D array: ${id.name}"
    }
    
    val scalar = VariableDeclaratorId(Identifier("x"), None)
    val array1D = VariableDeclaratorId(Identifier("arr"), Some(createMockDims(1)))
    val array2D = VariableDeclaratorId(Identifier("matrix"), Some(createMockDims(2)))
    val array4D = VariableDeclaratorId(Identifier("tensor"), Some(createMockDims(4)))
    
    analyzeVariable(scalar) shouldBe "scalar variable: x"
    analyzeVariable(array1D) shouldBe "1D array: arr"
    analyzeVariable(array2D) shouldBe "2D array: matrix"
    analyzeVariable(array4D) shouldBe "4D array: tensor"
  }

  test("VariableDeclaratorId should maintain consistency with Java language spec") {
    // Java Language Specification: variable declarator is identifier with optional dimensions
    val javaVarDeclarators = List(
      VariableDeclaratorId(Identifier("i"), None),                          // int i
      VariableDeclaratorId(Identifier("arr"), Some(createMockDims(1))),     // int[] arr
      VariableDeclaratorId(Identifier("_underscore"), None),                // int _underscore
      VariableDeclaratorId(Identifier("$dollar"), None),                    // int $dollar
      VariableDeclaratorId(Identifier("CamelCase"), None)                   // int CamelCase
    )
    
    javaVarDeclarators should have size 5
    javaVarDeclarators.foreach { varId =>
      varId.identifier.name should not be empty
      varId shouldBe a[VariableDeclaratorId]
    }
  }

  test("VariableDeclaratorId should handle parameter vs field variable semantics") {
    // Method parameters vs field variables can have same structure
    val methodParam = VariableDeclaratorId(Identifier("param"), None)      // void method(int param)
    val fieldVariable = VariableDeclaratorId(Identifier("field"), None)    // private int field;
    val arrayParam = VariableDeclaratorId(Identifier("args"), Some(createMockDims(1))) // main(String[] args)
    
    // Structure should be the same for both contexts
    methodParam.identifier.name shouldBe "param"
    fieldVariable.identifier.name shouldBe "field"
    arrayParam.identifier.name shouldBe "args"
    
    methodParam.dims shouldBe None
    fieldVariable.dims shouldBe None
    arrayParam.dims should not be None
  }
}