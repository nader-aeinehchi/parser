import sbt._
import sbt.Keys._
import scala.sys.process._

object Antlr4Gen extends AutoPlugin {
  override def trigger = allRequirements

  def firstNonCommentLine(file: File): Option[String] =
    IO.readLines(file)
      .find(line => line.trim.nonEmpty && !line.trim.startsWith("//"))

  lazy val antlr4GenerateAll = taskKey[Unit](
    "Generate ANTLR4 sources for all .g4 files with -visitor -listener"
  )

  override lazy val projectSettings = Seq(
    antlr4GenerateAll := {
      val log = streams.value.log
      val antlrSrcDir = (Compile / sourceDirectory).value / "antlr4"
      val outDir = (Compile / sourceManaged).value / "antlr4"

      // Use the downloaded ANTLR complete jar
      val antlrCompleteJar =
        baseDirectory.value / "lib" / "antlr-4.13.2-complete.jar"
      if (!antlrCompleteJar.exists)
        sys.error(
          s"${antlrCompleteJar.getAbsolutePath} not found. Download it from https://www.antlr.org/download.html"
        )

      val classpath = antlrCompleteJar.getAbsolutePath

      val g4Files = (antlrSrcDir ** "*.g4").get

      // Partition files: lexer grammars first, then parser grammars
      val (lexerFiles, parserFiles) = g4Files.partition { file =>
        firstNonCommentLine(file).exists(_.trim.startsWith("lexer grammar"))
      }

      val orderedFiles = lexerFiles ++ parserFiles

      IO.createDirectory(outDir)

      orderedFiles.foreach { file =>
        log.info(s"Generating sources for ${file.getName}")
        val cmd = Seq(
          "java",
          "-cp",
          classpath,
          "org.antlr.v4.Tool",
          "-Dlanguage=Java",
          "-visitor",
          "-listener",
          "-o",
          outDir.getAbsolutePath,
          file.getAbsolutePath
        )
        val exitCode = Process(cmd).!
        if (exitCode != 0) sys.error(s"ANTLR4 failed for ${file.getName}")
      }
      log.info("ANTLR4 generation complete.")
    }
  )
}
