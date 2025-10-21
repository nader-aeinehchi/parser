package org.naderica.parser.sourcecode.java20.standard.tool

object Reporter:
  def simpleReport(totalClasses: Int, testFiles: Int): String =
    f"Total classes: $totalClasses, Test files: $testFiles, Coverage: ${testFiles * 100.0 / totalClasses}%.1f%%"
