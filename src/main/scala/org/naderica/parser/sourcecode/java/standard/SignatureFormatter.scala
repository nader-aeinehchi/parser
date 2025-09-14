package org.naderica.parser.sourcecode.java.standard

import scala.jdk.CollectionConverters._

object SignatureFormatter {
  def asText(visitor: SignatureVisitor): Unit = {
    for (classSignature <- visitor.classSignatures().asScala) {
      println(classSignature)
    }
  }

  def asJson(visitor: SignatureVisitor): Unit = {
    for (classSignature <- visitor.classSignatures().asScala) {
      println(classSignature.toPrettyJson())
    }
  }
}
