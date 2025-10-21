package org.naderica.parser.sourcecode.java20.standard.tool

import java.io.{File, FileWriter, PrintWriter}
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

object Logger:
  private val logFile = new File("autonomous_test_generation.log")

  def info(msg: String): Unit = log("INFO", msg)
  def warn(msg: String): Unit = log("WARN", msg)
  def error(msg: String): Unit = log("ERROR", msg)
  def success(msg: String): Unit = log("OK", msg)

  private def timestamp: String = LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

  private def log(level: String, msg: String): Unit =
    val line = s"[$timestamp] [$level] $msg"
    // print to stdout
    println(line)
    // append to file
    val pw = new PrintWriter(new FileWriter(logFile, true))
    try pw.println(line) finally pw.close()

