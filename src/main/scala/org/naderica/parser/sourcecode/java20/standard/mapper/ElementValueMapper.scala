package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}

import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ConditionalAndExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ConditionalExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ConditionalOrExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ElementValue
import org.naderica.parser.sourcecode.java20.standard.grammar.ElementValueArrayInitializer
import org.naderica.parser.sourcecode.java20.standard.grammar.ElementValueList
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationElementValue
import org.naderica.parser.sourcecode.java20.standard.grammar.AssignmentExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.Expression
import org.naderica.parser.sourcecode.java20.standard.grammar.InclusiveOrExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ExclusiveOrExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.AndExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.EqualityExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.RelationalExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ShiftExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.AdditiveExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.MultiplicativeExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.UnaryExpression
import org.naderica.parser.sourcecode.java20.standard.grammar.ConditionalExpressionElementValue

object ElementValueMapper {
  def toElementValue(ctx: ElementValueContext): ElementValue = {
    if (ctx.conditionalExpression() != null) {
      // Element value is a conditional expression
      ConditionalExpressionElementValue(
        ExpressionMapper.toConditionalExpression(ctx.conditionalExpression())
      )
    } else if (ctx.elementValueArrayInitializer() != null) {
      // Element value is an array initializer - Fix: directly create the case class
      ElementValueArrayInitializer(
        Option(ctx.elementValueArrayInitializer().elementValueList())
          .map(toElementValueList)
      )
    } else if (ctx.annotation() != null) {
      // Element value is an annotation
      AnnotationElementValue(
        AnnotationMapper.toAnnotation(ctx.annotation())
      )
    } else {
      throw new IllegalArgumentException("Unknown element value type")
    }
  }

  def toElementValueList(ctx: ElementValueListContext): ElementValueList = {
    val elementValues = ctx.elementValue().asScala.toList.map(toElementValue)
    ElementValueList(elementValues)
  }
}
