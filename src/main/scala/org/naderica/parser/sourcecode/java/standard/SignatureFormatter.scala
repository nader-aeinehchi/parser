package org.naderica.parser.sourcecode.java.standard

import scala.jdk.CollectionConverters._
import play.api.libs.json._

object SignatureFormatter {
  def asText(visitor: SignatureVisitor): Unit = {
    for (classSignature <- visitor.classSignatures().asScala) {
      println(classSignature)
    }
  }

  def asJson(visitor: SignatureVisitor): Unit = {
    for (classSignature <- visitor.classSignatures().asScala) {
      // println(classSignature.toJson())

      val jsValue: JsValue = Json.parse(classSignature.toJson())

      val prettyJsonString: String = Json.prettyPrint(jsValue)
      println(prettyJsonString)
    }
  }
}
