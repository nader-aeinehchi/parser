package org.naderica.parser.sourcecode.java20.standard.tool

import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters._

object FileFinder:
  def listScalaClasses(mainPath: String): Seq[String] =
    val p = Paths.get(mainPath)
    if !Files.exists(p) then Nil
    else Files.walk(p).iterator().asScala
      .filter(p => Files.isRegularFile(p) && p.toString.endsWith(".scala"))
      .map(p => p.getFileName.toString.stripSuffix(".scala")).toSeq.sorted

  def listTestClasses(testPath: String): Seq[String] =
    val p = Paths.get(testPath)
    if !Files.exists(p) then Nil
    else Files.walk(p).iterator().asScala
      .filter(p => Files.isRegularFile(p) && p.toString.endsWith("Test.scala"))
      .map(p => p.getFileName.toString.replaceAll("Test.scala$", "")).toSeq.sorted

  def untestedClasses(mainPath: String, testPath: String): Seq[String] =
    val mains = listScalaClasses(mainPath).toSet
    val tests = listTestClasses(testPath).toSet
    (mains -- tests).toSeq.sorted

