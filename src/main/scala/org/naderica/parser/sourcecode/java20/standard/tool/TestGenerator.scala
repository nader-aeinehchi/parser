package org.naderica.parser.sourcecode.java20.standard.tool

object TestGenerator extends App {
  val MAIN_PATH = "src/main/scala/org/naderica/parser/sourcecode/java20/standard/grammar"
  val TEST_PATH = "src/test/scala/org/naderica/parser/sourcecode/java20/standard/grammar"

  Logger.info("Starting TestGenerator")

  val mains = FileFinder.listScalaClasses(MAIN_PATH)
  val tests = FileFinder.listTestClasses(TEST_PATH)
  val untested = FileFinder.untestedClasses(MAIN_PATH, TEST_PATH)

  Logger.info(s"Found ${mains.size} main classes and ${tests.size} test classes")
  Logger.info("Untested classes:")
  // Print plain class names (one per line) and also log formatted entries
  untested.foreach { c =>
    println(c)
    // Logger.info(s"  - $c")
  }

  Logger.info("Done")
}
