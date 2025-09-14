package org.naderica.parser.sourcecode.java.standard

import scala.collection.mutable.ListBuffer

/** A simple data structure to hold a class's signature and its members. */
class ClassSignature {
  var signature: String = ""
  val memberSignatures: ListBuffer[String] = ListBuffer.empty
  val innerClasses: ListBuffer[ClassSignature] = ListBuffer.empty

  override def toString: String = {
    val sb = new StringBuilder
    sb.append(s"Class Signature:\n    $signature\n")
    if (memberSignatures.nonEmpty) {

      sb.append("\tMembers, fields, constants:\n")
      memberSignatures.foreach { member =>
        sb.append(s"\t    - $member\n")
      }
    }
    if (innerClasses.nonEmpty) {
      sb.append("\tInner Classes:\n")
      innerClasses.foreach { inner =>
        sb.append("\t- " + inner.toString + "\n")
      }
    }
    sb.toString
  }
}
