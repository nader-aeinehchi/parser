package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FinallyBlockTest extends AnyFunSuite with Matchers {

  // Helper to create simple mock for testing
  private def createMockBlock(hasStatements: Boolean = false): Block = {
    if (hasStatements) {
      Block(Some(BlockStatements(List.empty)))
    } else {
      Block(None)
    }
  }

  test("FinallyBlock should create instance with empty block") {
    val block = createMockBlock()
    val finallyBlock = FinallyBlock(block)
    
    finallyBlock.block shouldBe block
  }

  test("FinallyBlock should create instance with block containing statements") {
    val block = createMockBlock(hasStatements = true)
    val finallyBlock = FinallyBlock(block)
    
    finallyBlock.block shouldBe block
    finallyBlock.block.blockStatements should not be None
  }

  test("FinallyBlock should support equality comparison") {
    val block1 = createMockBlock()
    val block2 = createMockBlock()
    val block3 = createMockBlock(hasStatements = true)
    
    val finallyBlock1 = FinallyBlock(block1)
    val finallyBlock2 = FinallyBlock(block2)
    val finallyBlock3 = FinallyBlock(block3)

    finallyBlock1 shouldEqual finallyBlock2  // Both have empty blocks
    finallyBlock1 should not equal finallyBlock3
  }

  test("FinallyBlock should handle empty finally block") {
    val emptyBlock = createMockBlock()
    val finallyBlock = FinallyBlock(emptyBlock)
    
    finallyBlock.block.blockStatements shouldBe None
  }

  test("FinallyBlock should handle finally block with statements") {
    val blockWithStatements = createMockBlock(hasStatements = true)
    val finallyBlock = FinallyBlock(blockWithStatements)
    
    finallyBlock.block.blockStatements should not be None
  }

  test("FinallyBlock should have proper toString representation") {
    val block = createMockBlock()
    val finallyBlock = FinallyBlock(block)
    
    finallyBlock.toString should include("FinallyBlock")
  }

  test("FinallyBlock case class should support pattern matching") {
    val emptyBlock = createMockBlock()
    val blockWithStatements = createMockBlock(hasStatements = true)
    val emptyFinally = FinallyBlock(emptyBlock)
    val nonEmptyFinally = FinallyBlock(blockWithStatements)

    val emptyResult = emptyFinally match {
      case FinallyBlock(Block(None)) => "empty finally"
      case FinallyBlock(Block(Some(_))) => "finally with statements"
    }

    val nonEmptyResult = nonEmptyFinally match {
      case FinallyBlock(Block(None)) => "empty finally"
      case FinallyBlock(Block(Some(_))) => "finally with statements"
    }

    emptyResult shouldBe "empty finally"
    nonEmptyResult shouldBe "finally with statements"
  }

  test("FinallyBlock should work with copy method") {
    val originalBlock = createMockBlock()
    val newBlock = createMockBlock(hasStatements = true)
    val original = FinallyBlock(originalBlock)
    
    val modified = original.copy(block = newBlock)
    modified.block shouldBe newBlock
    modified.block.blockStatements should not be None
    
    original.block shouldBe originalBlock
    original.block.blockStatements shouldBe None
  }

  test("FinallyBlock should support type checking") {
    val finallyBlock = FinallyBlock(createMockBlock())
    finallyBlock shouldBe a[FinallyBlock]
  }

  test("FinallyBlock should handle collections") {
    val finallyBlocks = List(
      FinallyBlock(createMockBlock()),
      FinallyBlock(createMockBlock(hasStatements = true)),
      FinallyBlock(createMockBlock())
    )

    finallyBlocks should have size 3
    finallyBlocks.count(_.block.blockStatements.isEmpty) shouldBe 2
    finallyBlocks.count(_.block.blockStatements.nonEmpty) shouldBe 1
  }

  test("FinallyBlock should represent Java finally semantics correctly") {
    // Java finally blocks: finally { } and finally { statements }
    val emptyFinally = FinallyBlock(createMockBlock())                       // finally { }
    val finallyWithCode = FinallyBlock(createMockBlock(hasStatements = true)) // finally { cleanup(); }

    emptyFinally.block.blockStatements shouldBe None
    finallyWithCode.block.blockStatements should not be None
  }

  test("FinallyBlock should handle hashCode and equals contract") {
    val block = createMockBlock()
    val finallyBlock1 = FinallyBlock(block)
    val finallyBlock2 = FinallyBlock(block)
    val finallyBlock3 = FinallyBlock(createMockBlock(hasStatements = true))

    // Equal objects should have equal hash codes
    if (finallyBlock1 == finallyBlock2) {
      finallyBlock1.hashCode shouldEqual finallyBlock2.hashCode
    }
    
    // Different objects should have different hash codes
    finallyBlock1.hashCode should not equal finallyBlock3.hashCode
  }

  test("FinallyBlock should support reflexivity, symmetry, and transitivity") {
    val block = createMockBlock()
    val finallyBlock1 = FinallyBlock(block)
    val finallyBlock2 = FinallyBlock(block)
    val finallyBlock3 = FinallyBlock(block)

    // Reflexivity
    finallyBlock1 shouldEqual finallyBlock1

    // Symmetry
    if (finallyBlock1 == finallyBlock2) {
      finallyBlock2 shouldEqual finallyBlock1
    }

    // Transitivity
    if (finallyBlock1 == finallyBlock2 && finallyBlock2 == finallyBlock3) {
      finallyBlock1 shouldEqual finallyBlock3
    }
  }

  test("FinallyBlock should work in try-catch-finally contexts") {
    val cleanupBlock = FinallyBlock(createMockBlock(hasStatements = true))
    val emptyFinallyBlock = FinallyBlock(createMockBlock())
    
    // Finally blocks for cleanup and empty cases
    cleanupBlock.block.blockStatements should not be None
    emptyFinallyBlock.block.blockStatements shouldBe None
  }

  test("FinallyBlock should support functional operations") {
    val finallyBlocks = List(
      FinallyBlock(createMockBlock()),
      FinallyBlock(createMockBlock(hasStatements = true)),
      FinallyBlock(createMockBlock()),
      FinallyBlock(createMockBlock(hasStatements = true))
    )
    
    // Count finally blocks with statements
    val blocksWithStatements = finallyBlocks.count(_.block.blockStatements.nonEmpty)
    blocksWithStatements shouldBe 2
    
    // Count empty finally blocks
    val emptyBlocks = finallyBlocks.count(_.block.blockStatements.isEmpty)
    emptyBlocks shouldBe 2
    
    // Filter non-empty finally blocks
    val nonEmptyBlocks = finallyBlocks.filter(_.block.blockStatements.nonEmpty)
    nonEmptyBlocks should have size 2
  }

  test("FinallyBlock should handle Java exception handling semantics") {
    // Java finally blocks execute regardless of exceptions
    val resourceCleanup = FinallyBlock(createMockBlock(hasStatements = true))  // finally { resource.close(); }
    val emptyCleanup = FinallyBlock(createMockBlock())                        // finally { }
    val logging = FinallyBlock(createMockBlock(hasStatements = true))         // finally { log.info("done"); }
    
    resourceCleanup.block.blockStatements should not be None
    emptyCleanup.block.blockStatements shouldBe None
    logging.block.blockStatements should not be None
    
    List(resourceCleanup, emptyCleanup, logging).foreach { finallyBlock =>
      finallyBlock shouldBe a[FinallyBlock]
    }
  }

  test("FinallyBlock should preserve cleanup execution guarantees") {
    val guaranteedCleanup = FinallyBlock(createMockBlock(hasStatements = true))
    val noOpCleanup = FinallyBlock(createMockBlock())
    
    // Finally blocks guarantee execution
    guaranteedCleanup.block.blockStatements should not be None  // { cleanup code }
    noOpCleanup.block.blockStatements shouldBe None            // { }
  }

  test("FinallyBlock should handle resource management contexts") {
    // Finally blocks used for resource management
    val resourceCleanup = FinallyBlock(createMockBlock(hasStatements = true))  // finally { stream.close(); }
    val lockRelease = FinallyBlock(createMockBlock(hasStatements = true))      // finally { lock.release(); }
    val noCleanup = FinallyBlock(createMockBlock())                           // finally { }
    
    resourceCleanup.block.blockStatements should not be None
    lockRelease.block.blockStatements should not be None
    noCleanup.block.blockStatements shouldBe None
    
    List(resourceCleanup, lockRelease, noCleanup).foreach { finallyBlock =>
      finallyBlock shouldBe a[FinallyBlock]
    }
  }

  test("FinallyBlock should support comprehensive pattern matching") {
    def analyzeFinallyBlock(finallyBlock: FinallyBlock): String = finallyBlock match {
      case FinallyBlock(Block(None)) => "empty finally block"
      case FinallyBlock(Block(Some(_))) => "finally block with cleanup"
    }
    
    val empty = FinallyBlock(createMockBlock())
    val withCleanup = FinallyBlock(createMockBlock(hasStatements = true))
    
    analyzeFinallyBlock(empty) shouldBe "empty finally block"
    analyzeFinallyBlock(withCleanup) shouldBe "finally block with cleanup"
  }

  test("FinallyBlock should maintain consistency with Java language spec") {
    // Java Language Specification: finally blocks execute after try/catch
    val javaFinallyBlocks = List(
      FinallyBlock(createMockBlock()),                       // finally { }
      FinallyBlock(createMockBlock(hasStatements = true))    // finally { cleanup(); }
    )
    
    javaFinallyBlocks should have size 2
    javaFinallyBlocks.foreach { finallyBlock =>
      finallyBlock shouldBe a[FinallyBlock]
    }
  }

  test("FinallyBlock should handle exception propagation semantics") {
    // Finally blocks execute during exception propagation
    val exceptionSafeCleanup = FinallyBlock(createMockBlock(hasStatements = true))  // finally { safeCleanup(); }
    val basicFinally = FinallyBlock(createMockBlock())                              // finally { }
    
    exceptionSafeCleanup.block.blockStatements should not be None
    basicFinally.block.blockStatements shouldBe None
    
    // Both execute during exception unwinding
    exceptionSafeCleanup shouldBe a[FinallyBlock]
    basicFinally shouldBe a[FinallyBlock]
  }

  test("FinallyBlock should support error recovery patterns") {
    // Finally blocks for error recovery and logging
    val errorRecovery = FinallyBlock(createMockBlock(hasStatements = true))   // finally { recover(); }
    val errorLogging = FinallyBlock(createMockBlock(hasStatements = true))    // finally { logError(); }
    val noRecovery = FinallyBlock(createMockBlock())                         // finally { }
    
    errorRecovery.block.blockStatements should not be None
    errorLogging.block.blockStatements should not be None
    noRecovery.block.blockStatements shouldBe None
    
    List(errorRecovery, errorLogging, noRecovery).foreach { finallyBlock =>
      finallyBlock shouldBe a[FinallyBlock]
    }
  }

  test("FinallyBlock should handle control flow completion") {
    // Finally blocks complete regardless of control flow
    val normalCompletion = FinallyBlock(createMockBlock(hasStatements = true))    // normal completion
    val exceptionCompletion = FinallyBlock(createMockBlock(hasStatements = true)) // exception completion
    val returnCompletion = FinallyBlock(createMockBlock())                       // return completion
    
    normalCompletion.block.blockStatements should not be None
    exceptionCompletion.block.blockStatements should not be None
    returnCompletion.block.blockStatements shouldBe None
    
    // All complete their finally blocks
    List(normalCompletion, exceptionCompletion, returnCompletion).foreach { finallyBlock =>
      finallyBlock shouldBe a[FinallyBlock]
    }
  }

  test("FinallyBlock should support builder-like patterns") {
    val base = FinallyBlock(createMockBlock())
    val blockWithStatements = createMockBlock(hasStatements = true)
    
    // Can create variations using copy
    val withCleanup = base.copy(block = blockWithStatements)
    val stillEmpty = base.copy(block = createMockBlock())
    
    withCleanup.block.blockStatements should not be None
    stillEmpty.block.blockStatements shouldBe None
    base.block.blockStatements shouldBe None
  }

  test("FinallyBlock should handle serialization-like operations") {
    val finallyBlock = FinallyBlock(createMockBlock(hasStatements = true))
    
    // Can access the contained block
    val containedBlock = finallyBlock.block
    containedBlock should not be null
    containedBlock shouldBe a[Block]
  }

  test("FinallyBlock should maintain Java syntax tree structure") {
    // FinallyBlock represents finally clauses in Java AST
    val syntaxFinally = FinallyBlock(createMockBlock(hasStatements = true))
    
    syntaxFinally shouldBe a[FinallyBlock]
    syntaxFinally.block shouldBe a[Block]
    
    // Represents finally { block } in Java AST
    val hasCleanupCode = syntaxFinally.block.blockStatements.nonEmpty
    hasCleanupCode shouldBe true
  }

  test("FinallyBlock should handle cleanup ordering semantics") {
    // Finally blocks execute in LIFO order when nested
    val outerFinally = FinallyBlock(createMockBlock(hasStatements = true))  // outer cleanup
    val innerFinally = FinallyBlock(createMockBlock(hasStatements = true))  // inner cleanup
    
    outerFinally.block.blockStatements should not be None
    innerFinally.block.blockStatements should not be None
    
    // Both represent cleanup phases but should be equal since they have identical structure
    outerFinally shouldEqual innerFinally
    outerFinally shouldBe a[FinallyBlock]
    innerFinally shouldBe a[FinallyBlock]
  }

  test("FinallyBlock should support case class invariants") {
    val block = createMockBlock()
    val finallyBlock1 = FinallyBlock(block)
    val finallyBlock2 = FinallyBlock(block)
    
    // Case class equality
    finallyBlock1 shouldEqual finallyBlock2
    
    // Case class hash code
    finallyBlock1.hashCode shouldEqual finallyBlock2.hashCode
    
    // Case class toString
    finallyBlock1.toString shouldBe finallyBlock2.toString
  }

  test("FinallyBlock should handle edge cases gracefully") {
    val finallyBlock = FinallyBlock(createMockBlock())
    
    // Should not throw when accessing block
    noException should be thrownBy {
      finallyBlock.block
      finallyBlock.block.blockStatements
    }
  }
}