package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ClassPermitsTest extends AnyFunSuite with Matchers {

  test("ClassPermits should create instance with empty type name list") {
    val classPermits = ClassPermits(List.empty)
    classPermits.typeNames shouldBe empty
  }

}