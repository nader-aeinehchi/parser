package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MethodBodyTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock blocks for testing
  private def createMockBlock(): Block = {
    Block(blockStatements = None)
  }

  test("BlockMethodBody should create instance with block") {
    val block = createMockBlock()
    val blockBody = BlockMethodBody(block)
    blockBody.block shouldBe block
  }

  test("EmptyMethodBody should be a singleton object") {
    val emptyBody = EmptyMethodBody
    emptyBody shouldBe EmptyMethodBody
  }

  test("BlockMethodBody should support equality comparison") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    val blockBody1 = BlockMethodBody(block1)
    val blockBody2 = BlockMethodBody(block1)
    val blockBody3 = BlockMethodBody(block2)

    blockBody1 shouldEqual blockBody2
    // Since our mock blocks are identical, they will be equal
    blockBody1 shouldEqual blockBody3
  }

  test("EmptyMethodBody should support equality comparison") {
    val empty1 = EmptyMethodBody
    val empty2 = EmptyMethodBody

    empty1 shouldEqual empty2
    empty1 shouldBe EmptyMethodBody
  }

  test("BlockMethodBody and EmptyMethodBody should be different types") {
    val block = createMockBlock()
    val blockBody = BlockMethodBody(block)
    val emptyBody = EmptyMethodBody

    blockBody should not equal emptyBody
    blockBody shouldBe a[BlockMethodBody]
    emptyBody shouldBe EmptyMethodBody
  }

  test("MethodBody sealed trait should support pattern matching") {
    val block = createMockBlock()
    val methodBodies: List[MethodBody] = List(
      BlockMethodBody(block),
      EmptyMethodBody,
      BlockMethodBody(createMockBlock())
    )

    val results = methodBodies.map {
      case BlockMethodBody(blk) => s"block_${blk.hashCode}"
      case EmptyMethodBody => "empty"
    }

    results should have size 3
    results should contain("empty")
    results.count(_ == "empty") shouldBe 1
    results.count(_.startsWith("block_")) shouldBe 2
  }

  test("BlockMethodBody should have proper toString representation") {
    val block = createMockBlock()
    val blockBody = BlockMethodBody(block)
    
    blockBody.toString should include("BlockMethodBody")
    blockBody.toString should include("Block")
  }

  test("EmptyMethodBody should have proper toString representation") {
    val emptyBody = EmptyMethodBody
    
    emptyBody.toString should include("EmptyMethodBody")
  }

  test("BlockMethodBody should work with copy method") {
    val originalBlock = createMockBlock()
    val newBlock = createMockBlock()
    val original = BlockMethodBody(originalBlock)
    val modified = original.copy(block = newBlock)

    modified.block shouldBe newBlock
    original.block shouldBe originalBlock
  }

  test("MethodBody should support type checking") {
    val block = createMockBlock()
    val blockBody: MethodBody = BlockMethodBody(block)
    val emptyBody: MethodBody = EmptyMethodBody

    blockBody shouldBe a[MethodBody]
    emptyBody shouldBe a[MethodBody]
    blockBody shouldBe a[BlockMethodBody]
    emptyBody shouldBe EmptyMethodBody
  }

  test("MethodBody should handle collections") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    val methodBodies = List(
      BlockMethodBody(block1),
      EmptyMethodBody,
      BlockMethodBody(block2),
      EmptyMethodBody
    )

    methodBodies should have size 4
    methodBodies.count(_.isInstanceOf[BlockMethodBody]) shouldBe 2
    methodBodies.count(_ == EmptyMethodBody) shouldBe 2
  }

  test("BlockMethodBody should represent Java method with implementation") {
    // BlockMethodBody represents: method() { statements }
    val block = createMockBlock()
    val blockBody = BlockMethodBody(block)
    
    blockBody.block shouldBe block
    blockBody shouldBe a[BlockMethodBody]
  }

  test("EmptyMethodBody should represent Java abstract or native method") {
    // EmptyMethodBody represents: abstract method() or native method();
    val emptyBody = EmptyMethodBody
    
    emptyBody shouldBe EmptyMethodBody
    emptyBody shouldBe a[MethodBody]
  }

  test("MethodBody should handle hashCode and equals contract") {
    val block = createMockBlock()
    val blockBody1 = BlockMethodBody(block)
    val blockBody2 = BlockMethodBody(block)
    val empty1 = EmptyMethodBody
    val empty2 = EmptyMethodBody

    // Equal objects should have equal hash codes
    if (blockBody1 == blockBody2) {
      blockBody1.hashCode shouldEqual blockBody2.hashCode
    }
    
    if (empty1 == empty2) {
      empty1.hashCode shouldEqual empty2.hashCode
    }
    
    // Different types should have different hash codes
    blockBody1.hashCode should not equal empty1.hashCode
  }

  test("MethodBody should support reflexivity, symmetry, and transitivity") {
    val block = createMockBlock()
    val blockBody1 = BlockMethodBody(block)
    val blockBody2 = BlockMethodBody(block)
    val blockBody3 = BlockMethodBody(block)
    val empty1 = EmptyMethodBody
    val empty2 = EmptyMethodBody

    // Reflexivity
    blockBody1 shouldEqual blockBody1
    empty1 shouldEqual empty1

    // Symmetry
    if (blockBody1 == blockBody2) {
      blockBody2 shouldEqual blockBody1
    }
    if (empty1 == empty2) {
      empty2 shouldEqual empty1
    }

    // Transitivity
    if (blockBody1 == blockBody2 && blockBody2 == blockBody3) {
      blockBody1 shouldEqual blockBody3
    }
  }

  test("MethodBody should work in collection contexts") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    val methodBodies = List(
      BlockMethodBody(block1),
      EmptyMethodBody,
      BlockMethodBody(block2)
    )

    methodBodies should have size 3
    
    val blockBodies = methodBodies.collect { case bb: BlockMethodBody => bb }
    blockBodies should have size 2
    
    val emptyBodies = methodBodies.filter(_ == EmptyMethodBody)
    emptyBodies should have size 1
  }

  test("MethodBody should support functional operations") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    val methodBodies = List(
      BlockMethodBody(block1),
      EmptyMethodBody,
      BlockMethodBody(block2),
      EmptyMethodBody
    )
    
    // Count method bodies by type
    val blockBodyCount = methodBodies.count(_.isInstanceOf[BlockMethodBody])
    val emptyBodyCount = methodBodies.count(_ == EmptyMethodBody)
    
    blockBodyCount shouldBe 2
    emptyBodyCount shouldBe 2
    
    // Filter method bodies
    val blockBodies = methodBodies.filter(_.isInstanceOf[BlockMethodBody])
    blockBodies should have size 2
    
    // Map to analyze types
    val bodyTypes = methodBodies.map {
      case _: BlockMethodBody => "implemented"
      case EmptyMethodBody => "abstract"
    }
    
    bodyTypes should contain("implemented")
    bodyTypes should contain("abstract")
  }

  test("MethodBody should handle Java method declaration semantics") {
    // Java method bodies: { statements } or empty (abstract/native)
    val block = createMockBlock()
    val implementedMethod = BlockMethodBody(block)  // public void method() { statements }
    val abstractMethod = EmptyMethodBody            // public abstract void method();
    
    implementedMethod.block shouldBe block
    abstractMethod shouldBe EmptyMethodBody
    
    implementedMethod should not equal abstractMethod
  }

  test("MethodBody should preserve method implementation semantics") {
    val block = createMockBlock()
    val blockBody = BlockMethodBody(block)
    val emptyBody = EmptyMethodBody
    
    // Block method has implementation
    blockBody shouldBe a[BlockMethodBody]
    
    // Empty method has no implementation
    emptyBody shouldBe EmptyMethodBody
    
    // They should be different
    blockBody should not equal emptyBody
  }

  test("MethodBody should handle method type classification") {
    val block = createMockBlock()
    val concreteMethod = BlockMethodBody(block)
    val abstractMethod = EmptyMethodBody
    
    // Concrete methods have bodies
    concreteMethod shouldBe a[BlockMethodBody]
    
    // Abstract/native methods have no bodies
    abstractMethod shouldBe EmptyMethodBody
    
    // They should be different
    concreteMethod should not equal abstractMethod
  }

  test("MethodBody should support comprehensive pattern matching") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    
    def analyzeMethodBody(body: MethodBody): String = body match {
      case BlockMethodBody(block) => s"concrete method with ${block.hashCode} block"
      case EmptyMethodBody => "abstract or native method"
    }
    
    val concrete = BlockMethodBody(block1)
    val abstractMethod = EmptyMethodBody
    
    analyzeMethodBody(concrete) should include("concrete method")
    analyzeMethodBody(abstractMethod) should include("abstract or native")
  }

  test("MethodBody should maintain consistency with Java language spec") {
    // Java Language Specification: method bodies are either blocks or empty
    val block = createMockBlock()
    val methodWithBody = BlockMethodBody(block)      // regular method
    val methodWithoutBody = EmptyMethodBody          // abstract/native method
    
    // These are the only two types of method bodies in Java
    val allMethodBodyTypes = Set(methodWithBody.getClass, methodWithoutBody.getClass)
    allMethodBodyTypes should have size 2
  }

  test("MethodBody should handle interface and abstract class semantics") {
    // In interfaces: default methods have bodies, abstract methods don't
    // In abstract classes: concrete methods have bodies, abstract methods don't
    
    val defaultMethodBody = BlockMethodBody(createMockBlock())  // default void method() { }
    val abstractMethodBody = EmptyMethodBody                    // void method();
    
    defaultMethodBody shouldBe a[BlockMethodBody]
    abstractMethodBody shouldBe EmptyMethodBody
    
    // Different method types
    defaultMethodBody should not equal abstractMethodBody
  }

  test("EmptyMethodBody should be a stable singleton") {
    val empty1 = EmptyMethodBody
    val empty2 = EmptyMethodBody
    
    // Should be the same instance (singleton)
    empty1 should be theSameInstanceAs empty2
    empty1.hashCode shouldEqual empty2.hashCode
    empty1 shouldEqual empty2
  }
}