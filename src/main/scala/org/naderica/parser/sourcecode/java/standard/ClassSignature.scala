package org.naderica.parser.sourcecode.java.standard

import scala.collection.mutable.ListBuffer

/** A simple data structure to hold a class's signature and its members. */
class ClassSignature {
  var signature: String = ""
  val memberSignatures: ListBuffer[String] = ListBuffer.empty
  val innerClasses: ListBuffer[ClassSignature] = ListBuffer.empty

  override def toString: String = {
    val sb = new StringBuilder
    sb.append(s"Class Signature: $signature\n")
    if (memberSignatures.nonEmpty) {
      sb.append("  Members:\n")
      memberSignatures.foreach { member =>
        sb.append(s"    - $member\n")
      }
    }
    if (innerClasses.nonEmpty) {
      sb.append("  Inner Classes:\n")
      innerClasses.foreach { inner =>
        sb.append("    " + inner.toString.replace("\n", "\n    ") + "\n")
      }
    }
    sb.toString
  }
}