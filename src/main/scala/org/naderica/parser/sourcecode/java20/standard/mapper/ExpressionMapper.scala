package org.naderica.parser.sourcecode.java20.standard.mapper

import scala.jdk.CollectionConverters.*

import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.{IntegerLiteral => IntLit, FloatingPointLiteral => FloatLit, BooleanLiteral => BoolLit, CharacterLiteral => CharLit, StringLiteral => StrLit, TextBlock => TxtBlock, NullLiteral => NullLit, Identifier => Id, *}

object ExpressionMapper {

  def toExpression(ctx: ExpressionContext): Expression = {
    if (ctx.lambdaExpression() != null) {
      toLambdaExpression(ctx.lambdaExpression())
    } else if (ctx.assignmentExpression() != null) {
      toAssignmentExpression(ctx.assignmentExpression())
    } else {
      throw new IllegalArgumentException("Unknown expression type")
    }
  }

  def toAssignmentExpression(
      ctx: AssignmentExpressionContext
  ): AssignmentExpression = {
    if (ctx.assignment() != null) {
      AssignmentExpression(
        conditionalExpression = None,
        assignment = Some(toAssignment(ctx.assignment()))
      )
    } else if (ctx.conditionalExpression() != null) {
      AssignmentExpression(
        conditionalExpression =
          Some(toConditionalExpression(ctx.conditionalExpression())),
        assignment = None
      )
    } else {
      throw new IllegalArgumentException("Unknown assignment expression type")
    }
  }

  def toConditionalExpression(
      ctx: ConditionalExpressionContext
  ): ConditionalExpression = {
    val conditionalOrExpression = toConditionalOrExpression(
      ctx.conditionalOrExpression()
    )
    val trueExpression = Option(ctx.expression()).map(toExpression)
    val falseExpression =
      Option(ctx.conditionalExpression()).map(toConditionalExpression)

    ConditionalExpression(
      conditionalOrExpression = conditionalOrExpression,
      trueExpression = trueExpression,
      falseExpression = falseExpression
    )
  }

  def toConditionalOrExpression(
      ctx: ConditionalOrExpressionContext
  ): ConditionalOrExpression = {
    val conditionalAndExpression = toConditionalAndExpression(
      ctx.conditionalAndExpression()
    )
    
    // Handle chained conditional OR operations
    val additionalExpressions = if (ctx.conditionalOrExpression() != null && ctx.OR() != null) {
      val rightExpression = toConditionalAndExpression(ctx.conditionalOrExpression().conditionalAndExpression())
      List(rightExpression)
    } else {
      List.empty[ConditionalAndExpression]
    }

    ConditionalOrExpression(
      conditionalAndExpression = conditionalAndExpression,
      additionalExpressions = additionalExpressions
    )
  }

  def toConditionalAndExpression(
      ctx: ConditionalAndExpressionContext
  ): ConditionalAndExpression = {
    val inclusiveOrExpression = toInclusiveOrExpression(
      ctx.inclusiveOrExpression()
    )
    
    // Handle chained conditional AND operations
    val additionalExpressions = if (ctx.conditionalAndExpression() != null && ctx.AND() != null) {
      val rightExpression = toInclusiveOrExpression(ctx.conditionalAndExpression().inclusiveOrExpression())
      List(rightExpression)
    } else {
      List.empty[InclusiveOrExpression]
    }

    ConditionalAndExpression(
      inclusiveOrExpression = inclusiveOrExpression,
      additionalExpressions = additionalExpressions
    )
  }

  def toInclusiveOrExpression(
      ctx: InclusiveOrExpressionContext
  ): InclusiveOrExpression = {
    val exclusiveOrExpression = toExclusiveOrExpression(
      ctx.exclusiveOrExpression()
    )
    
    // Handle chained inclusive OR operations
    val additionalExpressions = if (ctx.inclusiveOrExpression() != null && ctx.BITOR() != null) {
      val rightExpression = toExclusiveOrExpression(ctx.inclusiveOrExpression().exclusiveOrExpression())
      List(rightExpression)
    } else {
      List.empty[ExclusiveOrExpression]
    }

    InclusiveOrExpression(
      exclusiveOrExpression = exclusiveOrExpression,
      additionalExpressions = additionalExpressions
    )
  }

  def toExclusiveOrExpression(
      ctx: ExclusiveOrExpressionContext
  ): ExclusiveOrExpression = {
    val andExpression = toAndExpression(ctx.andExpression())
    
    // Handle chained exclusive OR operations
    val additionalExpressions = if (ctx.exclusiveOrExpression() != null && ctx.CARET() != null) {
      val rightExpression = toAndExpression(ctx.exclusiveOrExpression().andExpression())
      List(rightExpression)
    } else {
      List.empty[AndExpression]
    }

    ExclusiveOrExpression(
      andExpression = andExpression,
      additionalExpressions = additionalExpressions
    )
  }

  def toAndExpression(ctx: AndExpressionContext): AndExpression = {
    val equalityExpression = toEqualityExpression(ctx.equalityExpression())
    
    // Handle chained AND operations
    val additionalExpressions = if (ctx.andExpression() != null && ctx.BITAND() != null) {
      val rightExpression = toEqualityExpression(ctx.andExpression().equalityExpression())
      List(rightExpression)
    } else {
      List.empty[EqualityExpression]
    }

    AndExpression(
      equalityExpression = equalityExpression,
      additionalExpressions = additionalExpressions
    )
  }

  def toEqualityExpression(
      ctx: EqualityExpressionContext
  ): EqualityExpression = {
    val relationalExpression = toRelationalExpression(
      ctx.relationalExpression()
    )
    
    // Handle chained equality comparisons
    val additionalComparisons = if (ctx.equalityExpression() != null) {
      val operator = if (ctx.EQUAL() != null) {
        "=="
      } else if (ctx.NOTEQUAL() != null) {
        "!="
      } else {
        "==" // Default to equality if unclear
      }
      val rightExpression = toRelationalExpression(ctx.equalityExpression().relationalExpression())
      List((operator, rightExpression))
    } else {
      List.empty[(String, RelationalExpression)]
    }

    EqualityExpression(
      relationalExpression = relationalExpression,
      additionalComparisons = additionalComparisons
    )
  }

  def toRelationalExpression(
      ctx: RelationalExpressionContext
  ): RelationalExpression = {
    val shiftExpression = toShiftExpression(ctx.shiftExpression())
    
    // Handle chained relational comparisons
    val additionalComparisons = if (ctx.relationalExpression() != null) {
      val comparison = if (ctx.LT() != null) {
        LessThanComparison(toShiftExpression(ctx.relationalExpression().shiftExpression()))
      } else if (ctx.GT() != null) {
        GreaterThanComparison(toShiftExpression(ctx.relationalExpression().shiftExpression()))
      } else if (ctx.LE() != null) {
        LessEqualComparison(toShiftExpression(ctx.relationalExpression().shiftExpression()))
      } else if (ctx.GE() != null) {
        GreaterEqualComparison(toShiftExpression(ctx.relationalExpression().shiftExpression()))
      } else if (ctx.INSTANCEOF() != null) {
        InstanceOfComparison(TypeMapper.toReferenceType(ctx.referenceType()))
      } else {
        // Default to less than if unclear
        LessThanComparison(toShiftExpression(ctx.relationalExpression().shiftExpression()))
      }
      List(comparison)
    } else {
      List.empty[RelationalComparison]
    }

    RelationalExpression(
      shiftExpression = shiftExpression,
      additionalComparisons = additionalComparisons
    )
  }

  def toShiftExpression(ctx: ShiftExpressionContext): ShiftExpression = {
    val additiveExpression = toAdditiveExpression(ctx.additiveExpression())
    
    // Handle chained shift operations
    val shifts = if (ctx.shiftExpression() != null) {
      // Detect shift operation type based on LT and GT tokens
      val ltTokens = ctx.LT().size()
      val gtTokens = ctx.GT().size()
      
      val operation = if (ltTokens == 2 && gtTokens == 0) {
        // << (left shift)
        ShiftOperation.LeftShift
      } else if (ltTokens == 0 && gtTokens == 2) {
        // >> (right shift)
        ShiftOperation.RightShift
      } else if (ltTokens == 0 && gtTokens == 3) {
        // >>> (unsigned right shift)
        ShiftOperation.UnsignedRightShift
      } else {
        // Default to left shift if unclear
        ShiftOperation.LeftShift
      }
      List(operation)
    } else {
      List.empty[ShiftOperation]
    }

    ShiftExpression(
      additiveExpression = additiveExpression,
      shifts = shifts
    )
  }

  def toAdditiveExpression(
      ctx: AdditiveExpressionContext
  ): AdditiveExpression = {
    val multiplicativeExpression = toMultiplicativeExpression(
      ctx.multiplicativeExpression()
    )
    
    // Handle chained additive operations
    val operations = if (ctx.additiveExpression() != null) {
      val operation = if (ctx.ADD() != null) {
        Addition(toMultiplicativeExpression(ctx.additiveExpression().multiplicativeExpression()))
      } else if (ctx.SUB() != null) {
        Subtraction(toMultiplicativeExpression(ctx.additiveExpression().multiplicativeExpression()))
      } else {
        // Should not happen but handle gracefully
        Addition(toMultiplicativeExpression(ctx.additiveExpression().multiplicativeExpression()))
      }
      List(operation)
    } else {
      List.empty[AdditiveOperation]
    }

    AdditiveExpression(
      multiplicativeExpression = multiplicativeExpression,
      operations = operations
    )
  }

  def toMultiplicativeExpression(
      ctx: MultiplicativeExpressionContext
  ): MultiplicativeExpression = {
    val unaryExpression = toUnaryExpression(ctx.unaryExpression())
    
    // Handle chained multiplicative operations
    val operations = if (ctx.multiplicativeExpression() != null) {
      val operation = if (ctx.MUL() != null) {
        Multiplication(toUnaryExpression(ctx.multiplicativeExpression().unaryExpression()))
      } else if (ctx.DIV() != null) {
        Division(toUnaryExpression(ctx.multiplicativeExpression().unaryExpression()))
      } else if (ctx.MOD() != null) {
        Modulo(toUnaryExpression(ctx.multiplicativeExpression().unaryExpression()))
      } else {
        // Should not happen but handle gracefully
        Multiplication(toUnaryExpression(ctx.multiplicativeExpression().unaryExpression()))
      }
      List(operation)
    } else {
      List.empty[MultiplicativeOperation]
    }

    MultiplicativeExpression(
      unaryExpression = unaryExpression,
      operations = operations
    )
  }

  def toUnaryExpression(ctx: UnaryExpressionContext): UnaryExpression = {
    if (ctx.preIncrementExpression() != null) {
      PreIncrementExpression(
        toUnaryExpression(ctx.preIncrementExpression().unaryExpression())
      )
    } else if (ctx.preDecrementExpression() != null) {
      PreDecrementExpression(
        toUnaryExpression(ctx.preDecrementExpression().unaryExpression())
      )
    } else if (ctx.unaryExpressionNotPlusMinus() != null) {
      toUnaryExpressionNotPlusMinus(ctx.unaryExpressionNotPlusMinus())
    } else if (ctx.ADD() != null) {
      UnaryPlusExpression(toUnaryExpression(ctx.unaryExpression()))
    } else if (ctx.SUB() != null) {
      UnaryMinusExpression(toUnaryExpression(ctx.unaryExpression()))
    } else {
      throw new IllegalArgumentException("Unknown unary expression type")
    }
  }

  def toPreIncrementExpression(
      ctx: PreIncrementExpressionContext
  ): PreIncrementExpression = {
    PreIncrementExpression(toUnaryExpression(ctx.unaryExpression()))
  }

  def toPreDecrementExpression(
      ctx: PreDecrementExpressionContext
  ): PreDecrementExpression = {
    PreDecrementExpression(toUnaryExpression(ctx.unaryExpression()))
  }

  def toUnaryExpressionNotPlusMinus(
      ctx: UnaryExpressionNotPlusMinusContext
  ): UnaryExpressionNotPlusMinus = {
    if (ctx.postfixExpression() != null) {
      UnaryExpressionNotPlusMinus(
        postfixExpression = Some(toPostfixExpression(ctx.postfixExpression())),
        bitwiseComplementExpression = None,
        logicalComplementExpression = None,
        castExpression = None
      )
    } else if (ctx.TILDE() != null) {
      UnaryExpressionNotPlusMinus(
        postfixExpression = None,
        bitwiseComplementExpression =
          Some(toUnaryExpression(ctx.unaryExpression())),
        logicalComplementExpression = None,
        castExpression = None
      )
    } else if (ctx.BANG() != null) {
      UnaryExpressionNotPlusMinus(
        postfixExpression = None,
        bitwiseComplementExpression = None,
        logicalComplementExpression =
          Some(toUnaryExpression(ctx.unaryExpression())),
        castExpression = None
      )
    } else if (ctx.castExpression() != null) {
      UnaryExpressionNotPlusMinus(
        postfixExpression = None,
        bitwiseComplementExpression = None,
        logicalComplementExpression = None,
        castExpression = Some(toCastExpression(ctx.castExpression()))
      )
    } else {
      // Default case - create empty UnaryExpressionNotPlusMinus
      UnaryExpressionNotPlusMinus(
        postfixExpression = None,
        bitwiseComplementExpression = None,
        logicalComplementExpression = None,
        castExpression = None
      )
    }
  }

  def toPostfixExpression(ctx: PostfixExpressionContext): PostfixExpression = {
    if (ctx.primary() != null) {
      PostfixExpression(
        PfE(
          primary = Some(toPrimary(ctx.primary())),
          expressionName = None,
          postIncrementExpression = None,
          postDecrementExpression = None
        )
      )
    } else if (ctx.expressionName() != null) {
      PostfixExpression(
        PfE(
          primary = None,
          expressionName = Some(toExpressionName(ctx.expressionName())),
          postIncrementExpression = None,
          postDecrementExpression = None
        )
      )
    } else {
      // Default case - create empty PfE
      PostfixExpression(
        PfE(
          primary = None,
          expressionName = None,
          postIncrementExpression = None,
          postDecrementExpression = None
        )
      )
    }
  }

  def toPrimary(ctx: PrimaryContext): Primary = {
    if (ctx.primaryNoNewArray() != null) {
      toPrimaryNoNewArray(ctx.primaryNoNewArray())
    } else if (ctx.arrayCreationExpression() != null) {
      toArrayCreationExpression(ctx.arrayCreationExpression())
    } else {
      throw new IllegalArgumentException("Unknown primary expression")
    }
  }

  def toPNNA(ctx: PNNAContext): PNNA = {
    // Parse the pNNA context based on which alternative it represents
    // This is the postfix operations part that builds on top of base expressions
    
    if (ctx.unqualifiedClassInstanceCreationExpression() != null) {
      // .unqualifiedClassInstanceCreationExpression pNNA?
      PNNA(
        literal = None,
        classLiteral = None, 
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = Some(ClassInstanceCreationExpression(
          unqualifiedClassInstanceCreationExpression = toUnqualifiedClassInstanceCreationExpression(ctx.unqualifiedClassInstanceCreationExpression()),
          primary = None
        )),
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.identifier() != null && ctx.argumentList() != null) {
      // .typeArguments? identifier '(' argumentList? ')' pNNA?
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None, 
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = Some(MethodInvocation(
          methodName = None,
          typeName = None,
          expressionName = None,
          primary = None,
          super_ = None,
          typeArguments = Option(ctx.typeArguments()).map(TypeMapper.toTypeArguments),
          unqualifiedMethodIdentifier = UnqualifiedMethodIdentifier(ctx.identifier().getText),
          argumentList = Option(ctx.argumentList()).map(toArgumentList)
        )),
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.identifier() != null && ctx.argumentList() == null) {
      // .identifier pNNA?
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = Some(FieldAccess(
          primary = None,
          super_ = None,
          typeName = None,
          identifier = Id(ctx.identifier().getText)
        )),
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.expression() != null) {
      // '[' expression ']' pNNA?
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = Some(ArrayAccess(
          expressionName = None,
          primaryNoNewArray = None,
          arrayAccess = None,
          expression = toExpression(ctx.expression())
        )),
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else {
      // Handle other cases or return empty PNNA
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    }
  }

  def toPrimaryNoNewArray(ctx: PrimaryNoNewArrayContext): PrimaryNoNewArray = {
    // Build the base PNNA object based on which primary alternative is present
    val basePNNA = if (ctx.literal() != null) {
      PNNA(
        literal = Some(toLiteral(ctx.literal())),
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.classLiteral() != null) {
      PNNA(
        literal = None,
        classLiteral = Some(toClassLiteral(ctx.classLiteral())),
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.getText.contains("this") && ctx.typeName() != null) {
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = Some(TypeNameMapper.toTypeName(ctx.typeName())),
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.getText.contains("this")) {
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = Some("this"),
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.expression() != null) {
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = Some(toExpression(ctx.expression())),
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else if (ctx.unqualifiedClassInstanceCreationExpression() != null) {
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = Some(ClassInstanceCreationExpression(
          unqualifiedClassInstanceCreationExpression = toUnqualifiedClassInstanceCreationExpression(ctx.unqualifiedClassInstanceCreationExpression()),
          primary = None
        )),
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    } else {
      PNNA(
        literal = None,
        classLiteral = None,
        this_ = None,
        typeName = None,
        parenthesizedExpression = None,
        classInstanceCreationExpression = None,
        fieldAccess = None,
        arrayAccess = None,
        methodInvocation = None,
        methodReference = None,
        switchExpression = None
      )
    }

    // If there's a pNNA suffix, we need to recursively process it
    // For now, just use the base PNNA - the recursive postfix operations would be handled separately
    PrimaryNoNewArray(pnna = basePNNA)
  }

  // Additional helper methods - implement based on your needs
  def toLiteral(ctx: LiteralContext): Literal = {
    if (ctx.IntegerLiteral() != null) {
      IntLit(ctx.IntegerLiteral().getText)
    } else if (ctx.FloatingPointLiteral() != null) {
      FloatLit(ctx.FloatingPointLiteral().getText)
    } else if (ctx.BooleanLiteral() != null) {
      BoolLit(ctx.BooleanLiteral().getText == "true")
    } else if (ctx.CharacterLiteral() != null) {
      CharLit(ctx.CharacterLiteral().getText)
    } else if (ctx.StringLiteral() != null) {
      StrLit(ctx.StringLiteral().getText)
    } else if (ctx.TextBlock() != null) {
      TxtBlock(ctx.TextBlock().getText)
    } else if (ctx.NullLiteral() != null) {
      NullLit
    } else {
      throw new IllegalArgumentException("Unknown literal type")
    }
  }

  def toClassLiteral(ctx: ClassLiteralContext): ClassLiteral = {
    // Count the dimensions by counting LBRACK tokens
    val dimensions = ctx.LBRACK().size()
    val dims = if (dimensions > 0) {
      Some(Dims(
        annotations = List.fill(dimensions)(List.empty[Annotation]), // Empty annotation lists for each dimension
        count = dimensions
      ))
    } else {
      None
    }

    ClassLiteral(
      typeName = Option(ctx.typeName()).map(TypeNameMapper.toTypeName),
      numericType = Option(ctx.numericType()).map(toNumericType),
      boolean = if (ctx.BOOLEAN() != null) Some("boolean") else None,
      void = if (ctx.VOID() != null) Some("void") else None,
      dims = dims
    )
  }

  def toNumericType(ctx: NumericTypeContext): NumericType = {
    NumericType(
      integralType = Option(ctx.integralType()).map(_.getText),
      floatingPointType = Option(ctx.floatingPointType()).map(_.getText)
    )
  }

  def toExpressionName(ctx: ExpressionNameContext): ExpressionName = {
    ExpressionName(
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText),
      ambiguousName = Option(ctx.ambiguousName()).map(toAmbiguousName)
    )
  }

  def toAmbiguousName(ctx: AmbiguousNameContext): AmbiguousName = {
    AmbiguousName(
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText),
      ambiguousName = Option(ctx.ambiguousName()).map(toAmbiguousName)
    )
  }

  def toPostIncrementExpression(
      ctx: PostIncrementExpressionContext
  ): PostIncrementExpression = {
    PostIncrementExpression(toPostfixExpression(ctx.postfixExpression()))
  }

  def toPostDecrementExpression(
      ctx: PostDecrementExpressionContext
  ): PostDecrementExpression = {
    PostDecrementExpression(toPostfixExpression(ctx.postfixExpression()))
  }

  def toCastExpression(ctx: CastExpressionContext): CastExpression = {
    CastExpression(
      primitiveType = Option(ctx.primitiveType()).map(TypeMapper.toPrimitiveType),
      referenceType = Option(ctx.referenceType()).map(TypeMapper.toReferenceType),
      additionalBounds = Option(ctx.additionalBound()).map(_.asScala.map(TypeMapper.toAdditionalBound).toList).getOrElse(List.empty),
      unaryExpression = toUnaryExpression(ctx.unaryExpression())
    )
  }

  def toArrayCreationExpression(
      ctx: ArrayCreationExpressionContext
  ): ArrayCreationExpression = {
    if (ctx.arrayCreationExpressionWithoutInitializer() != null) {
      ArrayCreationExpression(
        arrayCreationExpressionWithoutInitializer = Some(
          toArrayCreationExpressionWithoutInitializer(ctx.arrayCreationExpressionWithoutInitializer())
        ),
        arrayCreationExpressionWithInitializer = None
      )
    } else if (ctx.arrayCreationExpressionWithInitializer() != null) {
      ArrayCreationExpression(
        arrayCreationExpressionWithoutInitializer = None,
        arrayCreationExpressionWithInitializer = Some(
          toArrayCreationExpressionWithInitializer(ctx.arrayCreationExpressionWithInitializer())
        )
      )
    } else {
      throw new IllegalArgumentException("Unknown array creation expression type")
    }
  }

  def toArrayCreationExpressionWithoutInitializer(
      ctx: ArrayCreationExpressionWithoutInitializerContext
  ): ArrayCreationExpressionWithoutInitializer = {
    ArrayCreationExpressionWithoutInitializer(
      primitiveType = Option(ctx.primitiveType()).map(TypeMapper.toPrimitiveType),
      classOrInterfaceType = Option(ctx.classType()).map(classType => 
        ClassOrInterfaceType(Some(TypeMapper.toClassType(classType)), None)
      ),
      dimExprs = toDimExprs(ctx.dimExprs()),
      dims = Option(ctx.dims()).map(TypeMapper.toDims)
    )
  }

  def toArrayCreationExpressionWithInitializer(
      ctx: ArrayCreationExpressionWithInitializerContext
  ): ArrayCreationExpressionWithInitializer = {
    ArrayCreationExpressionWithInitializer(
      primitiveType = Option(ctx.primitiveType()).map(TypeMapper.toPrimitiveType),
      classOrInterfaceType = Option(ctx.classOrInterfaceType()).map(TypeMapper.toClassOrInterfaceType),
      dims = TypeMapper.toDims(ctx.dims()),
      arrayInitializer = toArrayInitializer(ctx.arrayInitializer())
    )
  }

  def toDimExprs(ctx: DimExprsContext): DimExprs = {
    DimExprs(
      ctx.dimExpr().asScala.map(toDimExpr).toList
    )
  }

  def toDimExpr(ctx: DimExprContext): DimExpr = {
    DimExpr(
      annotations = Option(ctx.annotation()).map(_.asScala.map(AnnotationMapper.toAnnotation).toList).getOrElse(List.empty),
      expression = toExpression(ctx.expression())
    )
  }

  def toArrayInitializer(ctx: ArrayInitializerContext): ArrayInitializer = {
    ArrayInitializer(
      variableInitializerList = Option(ctx.variableInitializerList()).map(toVariableInitializerList)
    )
  }

  def toVariableInitializerList(ctx: VariableInitializerListContext): VariableInitializerList = {
    VariableInitializerList(
      ctx.variableInitializer().asScala.map(toVariableInitializer).toList
    )
  }

  def toVariableInitializer(ctx: VariableInitializerContext): VariableInitializer = {
    if (ctx.expression() != null) {
      ExpressionVariableInitializer(toExpression(ctx.expression()))
    } else if (ctx.arrayInitializer() != null) {
      toArrayInitializer(ctx.arrayInitializer())
    } else {
      throw new IllegalArgumentException("Unknown variable initializer type")
    }
  }

  def toClassInstanceCreationExpression(
      ctx: ClassInstanceCreationExpressionContext
  ): ClassInstanceCreationExpression = {
    ClassInstanceCreationExpression(
      unqualifiedClassInstanceCreationExpression = toUnqualifiedClassInstanceCreationExpression(
        ctx.unqualifiedClassInstanceCreationExpression()
      ),
      primary = Option(ctx.primary()).map(toPrimary)
    )
  }

  def toUnqualifiedClassInstanceCreationExpression(
      ctx: UnqualifiedClassInstanceCreationExpressionContext
  ): UnqualifiedClassInstanceCreationExpression = {
    UnqualifiedClassInstanceCreationExpression(
      typeArguments = Option(ctx.typeArguments()).map(TypeMapper.toTypeArguments),
      classOrInterfaceTypeToInstantiate = toClassOrInterfaceTypeToInstantiate(
        ctx.classOrInterfaceTypeToInstantiate()
      ),
      argumentList = Option(ctx.argumentList()).map(toArgumentList),
      classBody = Option(ctx.classBody()).map(ClassBodyMapper.toClassBody)
    )
  }

  def toClassOrInterfaceTypeToInstantiate(
      ctx: ClassOrInterfaceTypeToInstantiateContext
  ): ClassOrInterfaceTypeToInstantiate = {
    import scala.jdk.CollectionConverters._
    
    // Handle nested types by checking if there are multiple identifiers
    val identifiers = ctx.identifier().asScala.toList
    
    // Recursively build nested type structure for any depth
    def buildNestedType(identifierList: List[org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.IdentifierContext], depth: Int): Option[ClassOrInterfaceTypeToInstantiate] = {
      if (depth >= identifierList.length) {
        None
      } else {
        Some(ClassOrInterfaceTypeToInstantiate(
          annotations = List.empty, // Nested types inherit annotations from parent
          typeIdentifier = TypeIdentifier(identifierList(depth).getText),
          typeArgumentsOrDiamond = if (depth == 0) Option(ctx.typeArgumentsOrDiamond()).map(toTypeArgumentsOrDiamond) else None, // Only outermost has type arguments
          nestedType = buildNestedType(identifierList, depth + 1)
        ))
      }
    }
    
    val nestedType = if (identifiers.length > 1) {
      buildNestedType(identifiers, 1)
    } else {
      None
    }
    
    ClassOrInterfaceTypeToInstantiate(
      annotations = Option(ctx.annotation()).map(_.asScala.map(AnnotationMapper.toAnnotation).toList).getOrElse(List.empty),
      typeIdentifier = TypeIdentifier(identifiers.head.getText),
      typeArgumentsOrDiamond = Option(ctx.typeArgumentsOrDiamond()).map(toTypeArgumentsOrDiamond),
      nestedType = nestedType
    )
  }

  def toArgumentList(ctx: ArgumentListContext): ArgumentList = {
    ArgumentList(
      ctx.expression().asScala.map(toExpression).toList
    )
  }

  def toTypeArgumentsOrDiamond(ctx: TypeArgumentsOrDiamondContext): TypeArgumentsOrDiamond = {
    if (ctx.typeArguments() != null) {
      TypeArgumentsOrDiamondWithArgs(TypeMapper.toTypeArguments(ctx.typeArguments()))
    } else if (ctx.OACA() != null) {
      DiamondOperator // Diamond operator <>
    } else {
      throw new IllegalArgumentException("Unknown type arguments or diamond type")
    }
  }

  def toFieldAccess(ctx: FieldAccessContext): FieldAccess = {
    FieldAccess(
      primary = Option(ctx.primary()).map(toPrimary),
      super_ = if (ctx.SUPER() != null) Some("super") else None,
      typeName = Option(ctx.typeName()).map(TypeNameMapper.toTypeName),
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText)
    )
  }

  def toArrayAccess(ctx: ArrayAccessContext): ArrayAccess = {
    ArrayAccess(
      expressionName = Option(ctx.expressionName()).map(toExpressionName),
      primaryNoNewArray = Option(ctx.primaryNoNewArray()).map(toPrimaryNoNewArray),
      arrayAccess = None, // Recursive access not directly available in this context
      expression = toExpression(ctx.expression())
    )
  }

  def toMethodInvocation(ctx: MethodInvocationContext): MethodInvocation = {
    MethodInvocation(
      methodName = Option(ctx.methodName()).map(toMethodName),
      typeName = Option(ctx.typeName()).map(TypeNameMapper.toTypeName),
      expressionName = Option(ctx.expressionName()).map(toExpressionName),
      primary = Option(ctx.primary()).map(toPrimary),
      super_ = if (ctx.SUPER() != null) Some("super") else None,
      typeArguments = Option(ctx.typeArguments()).map(TypeMapper.toTypeArguments),
      unqualifiedMethodIdentifier = UnqualifiedMethodIdentifier(ctx.identifier().getText),
      argumentList = Option(ctx.argumentList()).map(toArgumentList)
    )
  }

  def toMethodName(ctx: MethodNameContext): MethodName = {
    MethodName(
      unqualifiedMethodIdentifier = toUnqualifiedMethodIdentifier(ctx.unqualifiedMethodIdentifier())
    )
  }

  def toUnqualifiedMethodIdentifier(ctx: UnqualifiedMethodIdentifierContext): UnqualifiedMethodIdentifier = {
    UnqualifiedMethodIdentifier(ctx.Identifier().getText)
  }

  def toMethodReference(ctx: MethodReferenceContext): MethodReference = {
    MethodReference(
      expressionName = Option(ctx.expressionName()).map(toExpressionName),
      primary = Option(ctx.primary()).map(toPrimary),
      referenceType = Option(ctx.referenceType()).map(TypeMapper.toReferenceType),
      super_ = if (ctx.SUPER() != null) Some("super") else None,
      typeName = Option(ctx.typeName()).map(TypeNameMapper.toTypeName),
      classType = Option(ctx.classType()).map(TypeMapper.toClassType),
      arrayType = Option(ctx.arrayType()).map(TypeMapper.toArrayType),
      typeArguments = Option(ctx.typeArguments()).map(TypeMapper.toTypeArguments),
      identifier = Option(ctx.identifier()).map(id => org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(id.getText)),
      new_ = if (ctx.NEW() != null) Some("new") else None
    )
  }

  def toSwitchExpression(ctx: SwitchExpressionContext): SwitchExpression = {
    SwitchExpression(
      expression = toExpression(ctx.expression()),
      switchBlock = toSwitchBlock(ctx.switchBlock())
    )
  }

  def toSwitchBlock(ctx: SwitchBlockContext): SwitchBlock = {
    import scala.jdk.CollectionConverters._
    SwitchBlock(
      switchRules = ctx.switchRule().asScala.map(toSwitchRule).toList,
      switchBlockStatementGroups = ctx.switchBlockStatementGroup().asScala.map(toSwitchBlockStatementGroup).toList
    )
  }

  def toSwitchRule(ctx: SwitchRuleContext): SwitchRule = {
    SwitchRule(
      switchLabel = toSwitchLabel(ctx.switchLabel()),
      expression = Option(ctx.expression()).map(toExpression),
      block = Option(ctx.block()).map(toBlock),
      throwStatement = Option(ctx.throwStatement()).map(toThrowStatement)
    )
  }

  def toSwitchBlockStatementGroup(ctx: SwitchBlockStatementGroupContext): SwitchBlockStatementGroup = {
    import scala.jdk.CollectionConverters._
    
    // Handle multiple switch labels by combining them into a single SwitchLabel
    // In Java, multiple labels like "case 1: case 2: case 3:" are allowed for a single statement group
    val switchLabels = ctx.switchLabel().asScala.toList
    val combinedSwitchLabel = if (switchLabels.nonEmpty) {
      // Combine all case constants from multiple labels into a single SwitchLabel
      val allCaseConstants = switchLabels.flatMap { labelCtx =>
        Option(labelCtx.caseConstant()).map(_.asScala.map(toCaseConstant).toList).getOrElse(List.empty)
      }
      
      // Check if any label is a default label
      val hasDefault = switchLabels.exists(_.DEFAULT() != null)
      
      SwitchLabel(
        caseConstants = if (allCaseConstants.nonEmpty) Some(allCaseConstants) else None,
        defaultLabel = if (hasDefault) Some("default") else None
      )
    } else {
      SwitchLabel(None, None) // Empty switch label
    }
    
    SwitchBlockStatementGroup(
      switchLabel = combinedSwitchLabel,
      blockStatements = toBlockStatements(ctx.blockStatements())
    )
  }

  def toSwitchLabel(ctx: SwitchLabelContext): SwitchLabel = {
    import scala.jdk.CollectionConverters._
    SwitchLabel(
      caseConstants = Option(ctx.caseConstant()).map(_.asScala.map(toCaseConstant).toList),
      defaultLabel = if (ctx.DEFAULT() != null) Some("default") else None
    )
  }

  def toBlock(ctx: BlockContext): Block = {
    Block(
      blockStatements = Option(ctx.blockStatements()).map(toBlockStatements)
    )
  }

  def toBlockStatements(ctx: BlockStatementsContext): BlockStatements = {
    import scala.jdk.CollectionConverters._
    BlockStatements(
      blockStatements = ctx.blockStatement().asScala.map(toBlockStatement).toList
    )
  }

  def toBlockStatement(ctx: BlockStatementContext): BlockStatement = {
    if (ctx.statement() != null) {
      val statementCtx = ctx.statement()
      
      // Check if it's an expression statement within a statement without trailing substatement
      if (statementCtx.statementWithoutTrailingSubstatement() != null) {
        val stmtWithoutTrailing = statementCtx.statementWithoutTrailingSubstatement()
        
        if (stmtWithoutTrailing.expressionStatement() != null) {
          // Handle expression statements with proper StatementExpression mapping
          val exprStmt = stmtWithoutTrailing.expressionStatement()
          StatementBlockStatement(
            statement = ExpressionStatement(
              statementExpression = toStatementExpression(exprStmt.statementExpression())
            )
          )
        } else {
          // Map the statement without trailing substatement to proper Statement types
          StatementBlockStatement(
            statement = toStatementWithoutTrailingSubstatement(stmtWithoutTrailing)
          )
        }
      } else if (statementCtx.labeledStatement() != null) {
        // Handle labeled statements
        val labeledCtx = statementCtx.labeledStatement()
        StatementBlockStatement(
          statement = LabeledStatement(
            identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(labeledCtx.identifier().getText),
            statement = toStatement(labeledCtx.statement())
          )
        )
      } else if (statementCtx.ifThenStatement() != null) {
        // Handle if-then statements
        val ifCtx = statementCtx.ifThenStatement()
        StatementBlockStatement(
          statement = IfThenStatement(
            expression = toExpression(ifCtx.expression()),
            statement = toStatement(ifCtx.statement())
          )
        )
      } else if (statementCtx.ifThenElseStatement() != null) {
        // Handle if-then-else statements  
        val ifElseCtx = statementCtx.ifThenElseStatement()
        StatementBlockStatement(
          statement = IfThenElseStatement(
            expression = toExpression(ifElseCtx.expression()),
            thenStatement = toStatementNoShortIf(ifElseCtx.statementNoShortIf()),
            elseStatement = toStatement(ifElseCtx.statement())
          )
        )
      } else if (statementCtx.whileStatement() != null) {
        // Handle while statements
        val whileCtx = statementCtx.whileStatement()
        StatementBlockStatement(
          statement = WhileStatement(
            expression = toExpression(whileCtx.expression()),
            statement = toStatement(whileCtx.statement())
          )
        )
      } else if (statementCtx.forStatement() != null) {
        // Handle for statements
        val forCtx = statementCtx.forStatement()
        StatementBlockStatement(
          statement = ForStatement(
            basicForStatement = Option(forCtx.basicForStatement()).map(toBasicForStatement),
            enhancedForStatement = Option(forCtx.enhancedForStatement()).map(toEnhancedForStatement)
          )
        )
      } else {
        // Fallback for any unhandled statement types
        StatementBlockStatement(
          statement = ExpressionStatement(
            statementExpression = StatementExpression(
              assignment = None,
              preIncrementExpression = None,
              postIncrementExpression = None,
              preDecrementExpression = None,
              postDecrementExpression = None,
              methodInvocation = None,
              classInstanceCreationExpression = None
            )
          )
        )
      }
    } else if (ctx.localVariableDeclarationStatement() != null) {
      // Enhanced implementation for local variable declarations
      LocalVariableDeclarationStatement(
        localVariableDeclaration = toLocalVariableDeclaration(ctx.localVariableDeclarationStatement().localVariableDeclaration())
      )
    } else if (ctx.localClassOrInterfaceDeclaration() != null) {
      // Enhanced implementation for local class/interface declarations
      toLocalClassOrInterfaceDeclaration(ctx.localClassOrInterfaceDeclaration())
    } else {
      throw new IllegalArgumentException("Unknown block statement type")
    }
  }

  def toThrowStatement(ctx: ThrowStatementContext): ThrowStatement = {
    ThrowStatement(expression = toExpression(ctx.expression()))
  }

  def toCaseConstant(ctx: CaseConstantContext): CaseConstant = {
    ExpressionCaseConstant(
      conditionalExpression = toConditionalExpression(ctx.conditionalExpression())
    )
  }

  def toLambdaExpression(ctx: LambdaExpressionContext): LambdaExpression = {
    LambdaExpression(
      lambdaParameters = toLambdaParameters(ctx.lambdaParameters()),
      lambdaBody = toLambdaBody(ctx.lambdaBody())
    )
  }

  def toLambdaParameters(ctx: LambdaParametersContext): LambdaParameters = {
    LambdaParameters(
      identifier = Option(ctx.identifier()).map(id => org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(id.getText)),
      formalParameterList = None, // Not supported by ANTLR grammar - only lambdaParameterList or identifier
      lambdaParameterList = Option(ctx.lambdaParameterList()).map(toLambdaParameterList)
    )
  }

  def toLambdaParameterList(ctx: LambdaParameterListContext): LambdaParameterList = {
    import scala.jdk.CollectionConverters._
    LambdaParameterList(
      lambdaParameters = ctx.lambdaParameter().asScala.map(toLambdaParameter).toList
    )
  }

  def toLambdaParameter(ctx: LambdaParameterContext): LambdaParameter = {
    import scala.jdk.CollectionConverters._
    LambdaParameter(
      variableModifiers = ctx.variableModifier().asScala.map(ModifierMapper.toVariableModifier).toList,
      lambdaParameterType = toLambdaParameterType(ctx.lambdaParameterType()),
      variableDeclaratorId = toVariableDeclaratorId(ctx.variableDeclaratorId())
    )
  }

  def toLambdaParameterType(ctx: LambdaParameterTypeContext): LambdaParameterType = {
    if (ctx.VAR() != null) {
      VarLambdaParameterType
    } else {
      UnannLambdaParameterType(TypeMapper.toUnannType(ctx.unannType()))
    }
  }

  def toVariableDeclaratorId(ctx: VariableDeclaratorIdContext): VariableDeclaratorId = {
    VariableDeclaratorId(
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText),
      dims = Option(ctx.dims()).map(TypeMapper.toDims)
    )
  }

  def toLambdaBody(ctx: LambdaBodyContext): LambdaBody = {
    if (ctx.expression() != null) {
      ExpressionLambdaBody(toExpression(ctx.expression()))
    } else if (ctx.block() != null) {
      BlockLambdaBody(BlockMapper.toBlock(ctx.block()))
    } else {
      throw new IllegalArgumentException("Unknown lambda body type")
    }
  }

  def toAssignment(ctx: AssignmentContext): Assignment = {
    Assignment(
      leftHandSide = toLeftHandSide(ctx.leftHandSide()),
      assignmentOperator = toAssignmentOperator(ctx.assignmentOperator()),
      expression = toExpression(ctx.expression())
    )
  }

  def toLeftHandSide(ctx: LeftHandSideContext): LeftHandSide = {
    LeftHandSide(
      expressionName = Option(ctx.expressionName()).map(toExpressionName),
      fieldAccess = Option(ctx.fieldAccess()).map(toFieldAccess),
      arrayAccess = Option(ctx.arrayAccess()).map(toArrayAccess)
    )
  }

  def toAssignmentOperator(
      ctx: AssignmentOperatorContext
  ): AssignmentOperator = {
    if (ctx.ASSIGN() != null) AssignmentOperator.Assign
    else if (ctx.MUL_ASSIGN() != null) AssignmentOperator.MultiplyAssign
    else if (ctx.DIV_ASSIGN() != null) AssignmentOperator.DivideAssign
    else if (ctx.MOD_ASSIGN() != null) AssignmentOperator.ModuloAssign
    else if (ctx.ADD_ASSIGN() != null) AssignmentOperator.PlusAssign
    else if (ctx.SUB_ASSIGN() != null) AssignmentOperator.MinusAssign
    else if (ctx.LSHIFT_ASSIGN() != null) AssignmentOperator.LeftShiftAssign
    else if (ctx.RSHIFT_ASSIGN() != null) AssignmentOperator.RightShiftAssign
    else if (ctx.URSHIFT_ASSIGN() != null)
      AssignmentOperator.UnsignedRightShiftAssign
    else if (ctx.AND_ASSIGN() != null) AssignmentOperator.BitwiseAndAssign
    else if (ctx.XOR_ASSIGN() != null) AssignmentOperator.BitwiseXorAssign
    else if (ctx.OR_ASSIGN() != null) AssignmentOperator.BitwiseOrAssign
    else throw new IllegalArgumentException("Unknown assignment operator")
  }

  def toStatementExpression(ctx: StatementExpressionContext): StatementExpression = {
    StatementExpression(
      assignment = Option(ctx.assignment()).map(toAssignment),
      preIncrementExpression = Option(ctx.preIncrementExpression()).map(toPreIncrementExpression),
      postIncrementExpression = Option(ctx.postIncrementExpression()).map(toPostIncrementExpression),
      preDecrementExpression = Option(ctx.preDecrementExpression()).map(toPreDecrementExpression),
      postDecrementExpression = Option(ctx.postDecrementExpression()).map(toPostDecrementExpression),
      methodInvocation = Option(ctx.methodInvocation()).map(toMethodInvocation),
      classInstanceCreationExpression = Option(ctx.classInstanceCreationExpression()).map(toClassInstanceCreationExpression)
    )
  }

  def toLocalVariableDeclaration(ctx: LocalVariableDeclarationContext): LocalVariableDeclaration = {
    LocalVariableDeclaration(
      variableModifiers = Option(ctx.variableModifier()).map(_.asScala.map(ModifierMapper.toVariableModifier).toList).getOrElse(List.empty),
      localVariableType = toLocalVariableType(ctx.localVariableType()),
      variableDeclaratorList = VariableMapper.toVariableDeclaratorList(ctx.variableDeclaratorList())
    )
  }

  def toLocalVariableType(ctx: LocalVariableTypeContext): LocalVariableType = {
    if (ctx.VAR() != null) {
      VarLocalVariableType
    } else if (ctx.unannType() != null) {
      UnannLocalVariableType(unannType = TypeMapper.toUnannType(ctx.unannType()))
    } else {
      throw new IllegalArgumentException("Unknown local variable type")
    }
  }

  def toLocalClassOrInterfaceDeclaration(ctx: LocalClassOrInterfaceDeclarationContext): LocalClassOrInterfaceDeclaration = {
    LocalClassOrInterfaceDeclaration(
      classDeclaration = Option(ctx.classDeclaration()).map(DeclarationMapper.toClassDeclaration),
      normalInterfaceDeclaration = Option(ctx.normalInterfaceDeclaration()).map(DeclarationMapper.toNormalInterfaceDeclaration)
    )
  }

  // Additional helper methods for the enhanced block statement mapping
  def toStatementWithoutTrailingSubstatement(ctx: StatementWithoutTrailingSubstatementContext): Statement = {
    if (ctx.expressionStatement() != null) {
      // Handle expression statements - this is the main case that works
      ExpressionStatement(
        statementExpression = toStatementExpression(ctx.expressionStatement().statementExpression())
      )
    } else if (ctx.assertStatement() != null) {
      // Handle assert statements with proper expression handling
      val assertCtx = ctx.assertStatement()
      val expressions = assertCtx.expression()
      AssertStatement(
        expression = toExpression(expressions.get(0)), // First expression 
        assertMessage = if (expressions.size() > 1) 
          Some(toExpression(expressions.get(1))) 
        else 
          None
      )
    } else if (ctx.switchStatement() != null) {
      // Handle switch statements
      SwitchStatement(
        expression = toExpression(ctx.switchStatement().expression()),
        switchBlock = toSwitchBlock(ctx.switchStatement().switchBlock())
      )
    } else if (ctx.doStatement() != null) {
      // Handle do-while statements
      DoStatement(
        statement = toStatement(ctx.doStatement().statement()),
        expression = toExpression(ctx.doStatement().expression())
      )
    } else if (ctx.breakStatement() != null) {
      // Handle break statements
      BreakStatement(
        identifier = Option(ctx.breakStatement().identifier()).map(id => 
          org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(id.getText)
        )
      )
    } else if (ctx.continueStatement() != null) {
      // Handle continue statements
      ContinueStatement(
        identifier = Option(ctx.continueStatement().identifier()).map(id => 
          org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(id.getText)
        )
      )
    } else if (ctx.returnStatement() != null) {
      // Handle return statements
      ReturnStatement(
        expression = Option(ctx.returnStatement().expression()).map(toExpression)
      )
    } else if (ctx.throwStatement() != null) {
      // Handle throw statements
      toThrowStatement(ctx.throwStatement())
    } else if (ctx.yieldStatement() != null) {
      // Handle yield statements (Java 14+)
      YieldStatement(
        expression = toExpression(ctx.yieldStatement().expression())
      )
    } else if (ctx.block() != null) {
      // Handle block statements - treat as empty statement for now
      // This is better than creating a malformed ExpressionStatement
      EmptyStatement_
    } else {
      // Handle other complex statement types as empty statements for now
      // This is much better than creating ExpressionStatements with all None fields
      EmptyStatement_
    }
  }

  def toStatement(ctx: StatementContext): Statement = {
    if (ctx.statementWithoutTrailingSubstatement() != null) {
      toStatementWithoutTrailingSubstatement(ctx.statementWithoutTrailingSubstatement())
    } else if (ctx.labeledStatement() != null) {
      val labeledCtx = ctx.labeledStatement()
      LabeledStatement(
        identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(labeledCtx.identifier().getText),
        statement = toStatement(labeledCtx.statement())
      )
    } else if (ctx.ifThenStatement() != null) {
      val ifCtx = ctx.ifThenStatement()
      IfThenStatement(
        expression = toExpression(ifCtx.expression()),
        statement = toStatement(ifCtx.statement())
      )
    } else if (ctx.ifThenElseStatement() != null) {
      val ifElseCtx = ctx.ifThenElseStatement()
      IfThenElseStatement(
        expression = toExpression(ifElseCtx.expression()),
        thenStatement = toStatementNoShortIf(ifElseCtx.statementNoShortIf()),
        elseStatement = toStatement(ifElseCtx.statement())
      )
    } else if (ctx.whileStatement() != null) {
      val whileCtx = ctx.whileStatement()
      WhileStatement(
        expression = toExpression(whileCtx.expression()),
        statement = toStatement(whileCtx.statement())
      )
    } else if (ctx.forStatement() != null) {
      val forCtx = ctx.forStatement()
      ForStatement(
        basicForStatement = Option(forCtx.basicForStatement()).map(toBasicForStatement),
        enhancedForStatement = Option(forCtx.enhancedForStatement()).map(toEnhancedForStatement)
      )
    } else {
      // Fallback for unhandled statement types - use EmptyStatement instead of malformed ExpressionStatement
      EmptyStatement_
    }
  }

  def toStatementNoShortIf(ctx: StatementNoShortIfContext): Statement = {
    // For now, convert to regular statement - may need specific handling
    if (ctx.statementWithoutTrailingSubstatement() != null) {
      toStatementWithoutTrailingSubstatement(ctx.statementWithoutTrailingSubstatement())
    } else {
      // Use EmptyStatement as fallback instead of malformed ExpressionStatement
      EmptyStatement_
    }
  }

  def toBasicForStatement(ctx: BasicForStatementContext): BasicForStatement = {
    BasicForStatement(
      forInit = Option(ctx.forInit()).map(toForInit),
      expression = Option(ctx.expression()).map(toExpression),
      forUpdate = Option(ctx.forUpdate()).map(toForUpdate),
      statement = toStatement(ctx.statement())
    )
  }

  def toEnhancedForStatement(ctx: EnhancedForStatementContext): EnhancedForStatement = {
    val localVarDecl = ctx.localVariableDeclaration()
    
    // Extract the actual identifier from the local variable declaration
    val variableDeclaratorList = localVarDecl.variableDeclaratorList()
    val firstDeclarator = variableDeclaratorList.variableDeclarator().get(0)
    val variableDeclaratorId = firstDeclarator.variableDeclaratorId()
    val identifier = variableDeclaratorId.identifier()
    
    EnhancedForStatement(
      variableModifiers = Option(localVarDecl.variableModifier()).map(_.asScala.map(ModifierMapper.toVariableModifier).toList).getOrElse(List.empty),
      localVariableType = toLocalVariableType(localVarDecl.localVariableType()),
      identifier = org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(identifier.getText),
      expression = toExpression(ctx.expression()),
      statement = toStatement(ctx.statement())
    )
  }

  def toForInit(ctx: ForInitContext): ForInit = {
    if (ctx.statementExpressionList() != null) {
      StatementExpressionListForInit(
        statementExpressionList = toStatementExpressionList(ctx.statementExpressionList())
      )
    } else if (ctx.localVariableDeclaration() != null) {
      LocalVariableForInit(
        localVariableDeclaration = toLocalVariableDeclaration(ctx.localVariableDeclaration())
      )
    } else {
      throw new IllegalArgumentException("Unknown for init type")
    }
  }

  def toForUpdate(ctx: ForUpdateContext): ForUpdate = {
    StatementExpressionListForUpdate(
      statementExpressionList = toStatementExpressionList(ctx.statementExpressionList())
    )
  }

  def toStatementExpressionList(ctx: StatementExpressionListContext): StatementExpressionList = {
    import scala.jdk.CollectionConverters._
    StatementExpressionList(
      statementExpressions = ctx.statementExpression().asScala.map(toStatementExpression).toList
    )
  }

}
