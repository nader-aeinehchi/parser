package org.naderica.parser.sourcecode.java.standard

import scala.collection.mutable.ListBuffer
import scala.collection.immutable.ListMap

/** A simple data structure to hold a class's signature and its methods. */
class ClassSignature {
  var packageSignature: String = ""
  // var packageName: String = ""
  var signature: String = ""
  val fieldSignatures: ListBuffer[String] = ListBuffer.empty
  val methodSignatures: ListBuffer[String] = ListBuffer.empty
  val innerClasses: ListBuffer[ClassSignature] = ListBuffer.empty

  override def toString: String = {
    val sb = new StringBuilder

    sb.append(s"Class Signature:\n $packageSignature \n $signature\n")

    if (methodSignatures.nonEmpty) {
      sb.append("\tMethods:\n")
      methodSignatures.foreach { method =>
        sb.append(s"\t    - $method\n")
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
      innerClasses.foreach { innerclass =>
        sb.append("\t- " + innerclass.toString + "\n")
      }
    }
    sb.toString
  }

  /** Produce a JSON representation of the ClassSignature.
    *
    * <pre> { "package:" : "<package_name>", "className:" : "<class_name>",
    * "fields:" : [<field_list>], "methods:" : [ <method_list> ],
    * "innerClasses:" : [ { "package:" : "<inner_class_package>", "className:" :
    * "<inner_class_name>", "fields:" : [ <inner_class_field_list> ], "methods:"
    * : [ <inner_class_method_list> ], "innerClasses:" : [ ] } ] } </pre>
    */

  def toJson(): String = {

    val packageJson = s""""package": "$packageSignature""""
    val signatureJson = s""""signature": "$signature""""

    val methodsJson =
      methodSignatures.map(m => entry(m)).mkString("[", ",", "]")
    val fieldsJson =
      fieldSignatures.map(m => entry(m)).mkString("[", ",", "]")

    val innerClassJson =
      innerClasses.map(((_.toJson()))).mkString("[", ",", "]")

    val cleanedPackage = entry(packageSignature)
    val cleanedSignature = entry(signature)

    val finalJson = ListMap(
      "package:" -> cleanedPackage,
      "className:" -> cleanedSignature,
      "fields:" -> fieldsJson,
      "methods:" -> methodsJson,
      "innerClasses:" -> innerClassJson
    )

    finalJson
      .map { case (k, v) => s""""$k": $v""" }
      .mkString("{\n  ", ",\n  ", "\n}")

  }

  private def wrapInQuotes(s: String): String = "\"" + s + "\""

  private def removeControlChars(s: String): String =
    s.replaceAll("[\\n\\t\\r\\f\\u0008]", "")
      .replaceAll("\\s{2,}", " ") // Replace multiple spaces with a single space
      .trim // Remove leading and trailing spaces

  private def entry(s: String): String = wrapInQuotes(removeControlChars(s))

  private def escapeJsonString(s: String): String =
    s.flatMap {
      case '"' => "\""
      case c   => c.toString
    }

  private val escapeQuotes: String => String = _.replace("\"", "\\\"")

}
