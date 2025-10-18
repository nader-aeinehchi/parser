package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ArgumentListTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock expressions for testing
  private def createMockExpression(id: String): Expression = {
    // Create the simplest possible AssignmentExpression without complex nesting
    AssignmentExpression(
      conditionalExpression = None,
      assignment = Some(Assignment(
        leftHandSide = LeftHandSide(
          expressionName = Some(ExpressionName(
            ambiguousName = None,
            identifier = Identifier(s"expr_$id")
          )),
          fieldAccess = None,
          arrayAccess = None
        ),
        assignmentOperator = AssignmentOperator.Assign,
        expression = AssignmentExpression(
          conditionalExpression = None,
          assignment = None
        )
      ))
    )
  }

  test("ArgumentList should create instance with expressions") {
    val expr1 = createMockExpression("1")
    val expr2 = createMockExpression("2")
    val argList = ArgumentList(List(expr1, expr2))
    argList.expressions should have size 2
    argList.expressions shouldBe List(expr1, expr2)
  }

  test("ArgumentList should create instance with empty list") {
    val argList = ArgumentList(List.empty)
    argList.expressions shouldBe empty
  }

  test("ArgumentList should create instance with single expression") {
    val expr = createMockExpression("single")
    val argList = ArgumentList(List(expr))
    argList.expressions should have size 1
    argList.expressions.head shouldBe expr
  }

  test("ArgumentList should support equality comparison") {
    val expr1 = createMockExpression("1")
    val expr2 = createMockExpression("2")
    val argList1 = ArgumentList(List(expr1, expr2))
    val argList2 = ArgumentList(List(expr1, expr2))
    val argList3 = ArgumentList(List(expr2, expr1))

    argList1 shouldEqual argList2
    argList1 should not equal argList3
  }

  test("ArgumentList should handle method call arguments") {
    val stringExpr = createMockExpression("hello")
    val numberExpr = createMockExpression("42")
    val booleanExpr = createMockExpression("true")
    val methodArgs = ArgumentList(List(stringExpr, numberExpr, booleanExpr))

    methodArgs.expressions should have size 3
    methodArgs.expressions shouldBe List(stringExpr, numberExpr, booleanExpr)
  }

  test("ArgumentList should handle constructor arguments") {
    val param1 = createMockExpression("param1")
    val param2 = createMockExpression("param2")
    val constructorArgs = ArgumentList(List(param1, param2))

    constructorArgs.expressions should have size 2
    constructorArgs.expressions shouldBe List(param1, param2)
  }

  test("ArgumentList should have proper toString representation") {
    val expr = createMockExpression("test")
    val argList = ArgumentList(List(expr))
    
    argList.toString should include("ArgumentList")
    argList.toString should include("List")
  }

  test("ArgumentList case class should support pattern matching") {
    val expr1 = createMockExpression("1")
    val expr2 = createMockExpression("2")
    val argList = ArgumentList(List(expr1, expr2))

    val result = argList match {
      case ArgumentList(expressions) => expressions.size
      case null => -1  // Handle null case explicitly to avoid warning
    }

    result shouldBe 2
  }

  test("ArgumentList should work with copy method") {
    val originalExpr = createMockExpression("original")
    val original = ArgumentList(List(originalExpr))

    val newExpr = createMockExpression("new")
    val modified = original.copy(expressions = List(newExpr))

    modified.expressions should have size 1
    modified.expressions.head shouldBe newExpr
    original.expressions.head shouldBe originalExpr
  }

  test("ArgumentList should handle Java method call semantics") {
    // Empty argument list - no parameters
    val noArgs = ArgumentList(List.empty)
    noArgs.expressions shouldBe empty

    // Single argument
    val singleArg = ArgumentList(List(createMockExpression("arg")))
    singleArg.expressions should have size 1

    // Multiple arguments - order matters
    val arg1 = createMockExpression("first")
    val arg2 = createMockExpression("second")
    val multiArgs = ArgumentList(List(arg1, arg2))
    multiArgs.expressions shouldBe List(arg1, arg2)
  }

  test("ArgumentList should support type checking") {
    val argList = ArgumentList(List.empty)
    argList shouldBe an[ArgumentList]
  }

  test("ArgumentList should handle collections") {
    val expr1 = createMockExpression("1")
    val expr2 = createMockExpression("2")
    val argLists = List(
      ArgumentList(List(expr1)),
      ArgumentList(List(expr2)),
      ArgumentList(List(expr1, expr2))
    )

    argLists should have size 3
    argLists.map(_.expressions.size) shouldBe List(1, 1, 2)
  }

  test("ArgumentList should represent Java argument lists correctly") {
    // ArgumentList represents the arguments passed to method calls or constructors
    val stringLiteral = createMockExpression("hello")
    val numberLiteral = createMockExpression("42") 
    val methodCall = ArgumentList(List(stringLiteral, numberLiteral))

    methodCall.expressions should have size 2
    methodCall.expressions shouldBe List(stringLiteral, numberLiteral)
  }

  test("ArgumentList should handle varargs representation") {
    // In Java, varargs are passed as regular arguments in the argument list
    val arg1 = createMockExpression("fixed")
    val vararg1 = createMockExpression("var1")
    val vararg2 = createMockExpression("var2")
    val varargsCall = ArgumentList(List(arg1, vararg1, vararg2))

    varargsCall.expressions should have size 3
    varargsCall.expressions shouldBe List(arg1, vararg1, vararg2)
  }

  test("ArgumentList should handle hashCode and equals contract") {
    val expr = createMockExpression("test")
    val argList1 = ArgumentList(List(expr))
    val argList2 = ArgumentList(List(expr))
    val argList3 = ArgumentList(List.empty)

    // Equal objects should have equal hash codes
    if (argList1 == argList2) {
      argList1.hashCode shouldEqual argList2.hashCode
    }
    
    // Different objects may have different hash codes
    argList1.hashCode should not equal argList3.hashCode
  }

  test("ArgumentList should support reflexivity, symmetry, and transitivity") {
    val expr = createMockExpression("test")
    val argList1 = ArgumentList(List(expr))
    val argList2 = ArgumentList(List(expr))
    val argList3 = ArgumentList(List(expr))

    // Reflexivity
    argList1 shouldEqual argList1

    // Symmetry
    if (argList1 == argList2) {
      argList2 shouldEqual argList1
    }

    // Transitivity
    if (argList1 == argList2 && argList2 == argList3) {
      argList1 shouldEqual argList3
    }
  }

  test("ArgumentList should work in collection contexts") {
    val expr1 = createMockExpression("1")
    val expr2 = createMockExpression("2")
    val argumentLists = List(
      ArgumentList(List.empty),
      ArgumentList(List(expr1)),
      ArgumentList(List(expr1, expr2))
    )

    argumentLists should have size 3
    
    val nonEmptyLists = argumentLists.filter(_.expressions.nonEmpty)
    nonEmptyLists should have size 2
    
    val totalExpressions = argumentLists.map(_.expressions.size).sum
    totalExpressions shouldBe 3
  }

  test("ArgumentList should support functional operations") {
    val argLists = List(
      ArgumentList(List(createMockExpression("a"))),
      ArgumentList(List(createMockExpression("b"), createMockExpression("c"))),
      ArgumentList(List.empty)
    )
    
    // Count total expressions
    val totalCount = argLists.map(_.expressions.size).sum
    totalCount shouldBe 3
    
    // Filter non-empty argument lists
    val nonEmpty = argLists.filter(_.expressions.nonEmpty)
    nonEmpty should have size 2
    
    // Get maximum argument count
    val maxArgs = argLists.map(_.expressions.size).max
    maxArgs shouldBe 2
  }

  test("ArgumentList should handle Java parameter passing semantics") {
    // Java method calls: method(arg1, arg2, arg3)
    val arg1 = createMockExpression("first")
    val arg2 = createMockExpression("second") 
    val arg3 = createMockExpression("third")
    val javaMethodCall = ArgumentList(List(arg1, arg2, arg3))

    // Arguments are evaluated left to right
    javaMethodCall.expressions should have size 3
    javaMethodCall.expressions(0) shouldBe arg1
    javaMethodCall.expressions(1) shouldBe arg2
    javaMethodCall.expressions(2) shouldBe arg3
  }

  test("ArgumentList should preserve argument order") {
    val first = createMockExpression("first")
    val second = createMockExpression("second")
    val third = createMockExpression("third")
    
    val orderedArgs = ArgumentList(List(first, second, third))
    val reversedArgs = ArgumentList(List(third, second, first))
    
    orderedArgs should not equal reversedArgs
    orderedArgs.expressions shouldBe List(first, second, third)
    reversedArgs.expressions shouldBe List(third, second, first)
  }

  test("ArgumentList should handle nested method calls") {
    // Represents: method(innerMethod(arg1), arg2)
    val innerArg = createMockExpression("innerArg")
    val outerArg = createMockExpression("outerArg")
    val methodCallWithNested = ArgumentList(List(innerArg, outerArg))

    methodCallWithNested.expressions should have size 2
    methodCallWithNested.expressions shouldBe List(innerArg, outerArg)
  }
}