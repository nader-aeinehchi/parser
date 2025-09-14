package org.naderica.parser.sourcecode.java.standard

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
      val visitor = new SignatureVisitor(tokens)

      // Visit the parse tree to trigger the logic in our visitor.
      visitor.visit(tree)

      println("Extracted Class Signatures and Members:")
      import scala.jdk.CollectionConverters._
      for (classInfo <- visitor.getResult.asScala) {
        println(classInfo)
      }
    } catch {
      case e: Exception =>
        System.err.println(s"Error reading file: ${e.getMessage}")
    }
  }

}

@main
def runJavaParser(pathToSourceFiles: String): Unit = {
  val parser = new JavaParser()
  parser.main(pathToSourceFiles)
}
