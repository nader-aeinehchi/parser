package org.naderica.parser.sourcecode.java.standard

import scala.collection.mutable.ListBuffer

/** A simple data structure to hold a class's signature and its members. */
class ClassSignature {
  var packageSignature: String = ""
  var packageName: String = ""
  var signature: String = ""
  val fieldSignatures: ListBuffer[String] = ListBuffer.empty
  val memberSignatures: ListBuffer[String] = ListBuffer.empty
  val innerClasses: ListBuffer[ClassSignature] = ListBuffer.empty

  override def toString: String = {
    val sb = new StringBuilder

    sb.append(s"Class Signature:\n $packageSignature \n $signature\n")

    if (memberSignatures.nonEmpty) {
      sb.append("\tMembers:\n")
      memberSignatures.foreach { member =>
        sb.append(s"\t    - $member\n")
      }
    }

    if (fieldSignatures.nonEmpty) {
      sb.append("\tFields:\n")
      fieldSignatures.foreach { field =>
        sb.append(s"\t    - $field\n")
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

  // Add this method inside your ClassSignature class
  def toJson: String = {
    val membersJson =
      memberSignatures.map(m => s""""$m"""").mkString("[", ",", "]")

    val fieldsJson =
      fieldSignatures.map(m => s""""$m"""").mkString("[", ",", "]")

    val innerJson = innerClasses.map(_.toJson).mkString("[", ",", "]")

    s"""{
    "package": "${packageName.replace("\"", "\\\"")}",
    "signature": "${signature.replace("\"", "\\\"")}",
    "fields": $fieldsJson,
    "members": $membersJson,
    "innerClasses": $innerJson
    }"""
  }

  def toPrettyJson(indent: String = ""): String = {
    val nextIndent = indent + "  "

    val membersJson =
      if (memberSignatures.isEmpty) "[]"
      else
        memberSignatures
          .map(escapeQuotes)
          .map(m => s"""$nextIndent  "$m"""")
          .mkString("[\n", ",\n", s"\n$nextIndent]")

    val fieldsJson =
      if (fieldSignatures.isEmpty) "[]"
      else
        fieldSignatures
          .map(escapeQuotes)
          .map(m => s"""$nextIndent  "$m"""")
          .mkString("[\n", ",\n", s"\n$nextIndent]")

    val innerJson =
      if (innerClasses.isEmpty) "[]"
      else
        innerClasses
          .map(_.toPrettyJson(nextIndent + "  "))
          .mkString("[\n", ",\n", s"\n$nextIndent]")

    s"""${indent}{
        ${nextIndent}"package": "${packageName.replace("\"", "\\\"")}",
        ${nextIndent}"signature": "${signature.replace("\"", "\\\"")}",
        ${nextIndent}"fields": $fieldsJson,
        ${nextIndent}"members": $membersJson,
        ${nextIndent}"innerClasses": $innerJson
        $indent}"""
  }

  private val escapeQuotes: String => String = _.replace("\"", "\\\"")

}
