package org.naderica.parser.sourcecode.java20.standard

import scala.jdk.CollectionConverters.* 

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.naderica.parser.sourcecode.ast.java.standard.Java20Lexer
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser

class JavaParser() {
  def main(sourceFilePath: String): Unit = {

    try {
      val input = CharStreams.fromFileName(sourceFilePath)
      val lexer = new Java20Lexer(input)
      val tokens = new CommonTokenStream(lexer)
      val parser = new Java20Parser(tokens)
      val tree = parser.compilationUnit()

      // Create an instance of our custom visitor with the token stream.
      // val visitor = new SignatureVisitor(tokens, JModifier.PROTECTED)
      val visitor = new TypesafeVisitor(tokens, JModifier.PROTECTED)

      // Visit the parse tree to trigger the logic in our visitor.
      visitor.visit(tree)
      // SignatureFormatter.asText(visitor)
      println("\n----- JSON Output -----\n")
      // SignatureFormatter.asJson(visitor)

    } catch {
      case e: Exception =>
        System.err.println(s"Error reading file: ${e.getMessage}")
    }
  }

}

@main
def runJavaParser(
    pathToSourceFiles: String,
    recursive: Boolean = false
): Unit = {

  // Clear terminal (ANSI escape code)
  print("\u001b[2J\u001b[H")

  val parser = new JavaParser()
  val file = new java.io.File(pathToSourceFiles)

  def processDir(dir: java.io.File): Unit = {
    val files = dir.listFiles()
    if (files != null) {
      files.foreach { f =>
        if (f.isDirectory && recursive) processDir(f)
        else if (f.isFile && f.getName.endsWith(".java"))
          parser.main(f.getAbsolutePath)
      }
    }
  }

  if (file.isDirectory) {
    processDir(file)
  } else if (file.isFile && file.getName.endsWith(".java")) {
    parser.main(pathToSourceFiles)
  } else {
    println(
      s"Provided path is not a .java file or directory: $pathToSourceFiles"
    )
  }
}
