package org.naderica.parser.sourcecode.java.standard

import java.util.{ArrayList, List, Stack}
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.ParserRuleContext
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.ast.java.standard.Java20ParserBaseVisitor

enum JModifier {
  case PUBLIC, PROTECTED, PRIVATE, PACKAGE_PRIVATE

  def toCamelCase(s: String): String =
    s.toLowerCase.split("_").toList match
      case head :: tail => head + tail.map(_.capitalize).mkString
      case Nil          => s

  // def fieldName: String = this match
  //   case PUBLIC          => "PUBLIC"
  //   case PROTECTED       => "PROTECTED"
  //   case PRIVATE         => "PRIVATE"
  //   case PACKAGE_PRIVATE => "PACKAGE_PRIVATE"

  override def toString: String = this match {
    case PUBLIC          => "public"
    case PROTECTED       => "protected"
    case PRIVATE         => "private"
    case PACKAGE_PRIVATE => "" // package-private has no explicit modifier
  }

  /** All modifiers that are equal or less restrictive than this one (including
    * this one itself)
    *
    * @return
    */
  def higherLevels: Set[JModifier] = this match {
    case PRIVATE         => Set(PRIVATE, PACKAGE_PRIVATE, PROTECTED, PUBLIC)
    case PACKAGE_PRIVATE => Set(PACKAGE_PRIVATE, PROTECTED, PUBLIC)
    case PROTECTED       => Set(PROTECTED, PUBLIC)
    case PUBLIC          => Set(PUBLIC)
  }

  /** All modifiers that are equal or more restrictive than this one (including
    * this one itself)
    *
    * @return
    */
  def lowerLevels: Set[JModifier] = this match {
    case PRIVATE         => Set(PRIVATE)
    case PACKAGE_PRIVATE => Set(PRIVATE, PACKAGE_PRIVATE)
    case PROTECTED       => Set(PRIVATE, PACKAGE_PRIVATE, PROTECTED)
    case PUBLIC          => Set(PRIVATE, PACKAGE_PRIVATE, PROTECTED, PUBLIC)
  }

  def isLessRestrictiveThan(other: JModifier): Boolean =
    this.lowerLevels.contains(other)

  def isMoreRestrictiveThan(other: JModifier): Boolean =
    this.higherLevels.contains(other)

}
