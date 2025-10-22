package org.naderica.parser.sourcecode.java20.standard.tool

import java.nio.file.Files
import java.nio.file.Paths
import scala.util.{Try, Success, Failure}

class SingleTestGenerator(
    val className: String,
    val services: Services,
    val mainPath: String,
    val testPath: String,
    val apiKey: String,
    val maxRetries: Int,
    val timeoutSeconds: Int
) {

  def generate(): Boolean = {

    services.logger.info(s"Processing $className")
    generateAndWriteTest() match {
      case Failure(err) =>
        services.logger.error(
          s"Failed to generate/write test for $className: ${err.getMessage}"
        )
        false
      case Success(_) =>
        compileAndTest()
    }
  }

  private def generateAndWriteTest(): Try[Unit] = {
    Try {
      val classPath = Paths.get(s"$mainPath/$className.scala")
      val classContent = Files.readString(classPath)

      services.apiClient.generateTest(classContent, apiKey) match {
        case Failure(err) =>
          throw err
        case Success(testSrc) =>
          val testFile = Paths.get(s"$testPath/${className}Test.scala")
          val parent = testFile.getParent
          if (parent != null) Files.createDirectories(parent)
          Files.writeString(testFile, testSrc)
          ()
      }
    }
  }

  private def compileAndTest(
  ): Boolean = {

    def attempt(remaining: Int): Boolean = {
      if (remaining <= 0) return false
      val attemptNumber = maxRetries - remaining + 1
      services.logger.info(
        s"Compiling and testing $className (attempt ${attemptNumber}/$maxRetries)"
      )
      val cmd =
        Seq("sbt", "-batch", "-no-colors", s"testOnly *${className}Test")

      services.shellRunner.runCmd(cmd, timeoutSeconds) match {
        case Success((0, out)) =>
          if (out.contains("All tests passed") || out.contains("0 failed")) {
            services.logger.info(s"All tests passed for $className")
            true
          } else {
            services.logger.warn(
              s"Tests ran but did not indicate success for $className. Output length: ${out.length}"
            )
            attempt(remaining - 1)
          }
        case Success((code, out)) =>
          services.logger.error(
            s"sbt exited with code $code for $className. Output: ${out.linesIterator.take(20).mkString("\n")}"
          )
          attempt(remaining - 1)
        case Failure(err) =>
          services.logger.error(s"sbt run failed for $className: ${err.getMessage}")
          attempt(remaining - 1)
      }
    }

    attempt(maxRetries)
  }
}
