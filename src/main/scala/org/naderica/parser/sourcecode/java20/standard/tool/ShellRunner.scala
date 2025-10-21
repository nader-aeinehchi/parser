package org.naderica.parser.sourcecode.java20.standard.tool

import scala.sys.process._
import scala.util.{Try, Success, Failure}

object ShellRunner:
  def runCmd(cmd: Seq[String], timeoutSeconds: Int = 300): Try[(Int, String)] =
    Try {
      val pb = Process(cmd)
      val out = new StringBuilder
      val exit = pb ! ProcessLogger(s => out.append(s + "\n"), e => out.append(e + "\n"))
      (exit, out.toString())
    }

