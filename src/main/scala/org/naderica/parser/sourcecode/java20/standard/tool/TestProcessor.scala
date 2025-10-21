package org.naderica.parser.sourcecode.java20.standard.tool

import scala.util.{Try, Success, Failure}
import java.nio.file.{Files, Paths}

class TestProcessor(
    mainPath: String = TestGenerator.MAIN_PATH,
    testPath: String = TestGenerator.TEST_PATH,
    apiKey: String,
    batchSize: Int = 5,
    maxRetries: Int = 3,
    timeoutSeconds: Int = 300
):

  def processAll(): Int =
    Logger.info("Starting fully autonomous test generation process...")

    val untested = FileFinder.untestedClasses(mainPath, testPath)
    if untested.isEmpty then
      Logger.info("No untested classes found. All classes have tests!")
      return 0
    
    Logger.info(s"Found ${untested.size} classes without tests")
    Logger.info("Listing untested classes:")
    untested.foreach(uc => println(uc))

    var successCount = 0
    var failed = Vector.empty[String]

    val groups = untested.grouped(batchSize).toSeq
    for ((group, idx) <- groups.zipWithIndex) do
      Logger.info(s"Processing batch ${idx + 1}/${groups.size}: ${group.size} classes")
      for cls <- group do
        Logger.info(s"Processing $cls")
        val classContent = Files.readString(Paths.get(s"$mainPath/$cls.scala"))
        val generated = ApiClient.generateTest(classContent, apiKey)
        generated match
          case Failure(err) =>
            Logger.error(s"Failed to generate test for $cls: ${err.getMessage}")
            failed :+= cls
          case Success(testSrc) =>
            val testFile = Paths.get(s"$testPath/${cls}Test.scala")
            Files.createDirectories(testFile.getParent)
            Files.writeString(testFile, testSrc)
            // compile and run test using sbt
            val attempt = compileAndTest(cls)
            if attempt then
              successCount += 1
            else
              failed :+= cls

    Logger.info(s"Completed processing. Success: $successCount, Failed: ${failed.size}")
    if failed.nonEmpty then Logger.warn(s"Failed classes: ${failed.mkString(", ")}")
    successCount

  private def compileAndTest(className: String): Boolean =
    var retries = 0
    while retries < maxRetries do
      Logger.info(s"Compiling and testing $className (attempt ${retries + 1}/$maxRetries)")
      val cmd = Seq("sbt", "-batch", "-no-colors", s"testOnly *${className}Test")
      ShellRunner.runCmd(cmd, timeoutSeconds) match
        case Success((0, out)) =>
          // look for successful output
          if out.contains("All tests passed") || out.contains("0 failed") then
            Logger.info(s"All tests passed for $className")
            return true
          else
            Logger.warn(s"Tests ran but did not indicate success for $className. Output length: ${out.length}")
        case Success((code, out)) =>
          Logger.error(s"sbt exited with code $code for $className. Output: ${out.linesIterator.take(20).mkString("\n")}")
        case Failure(err) =>
          Logger.error(s"sbt run failed for $className: ${err.getMessage}")
      retries += 1
      // attempt simple fixes could be implemented here
    false
