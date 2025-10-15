package org.naderica.parser.sourcecode.java20.standard.grammar

case class PNNA(
    literal: Option[Literal],
    classLiteral: Option[ClassLiteral],
    this_ : Option[String], // "this"
    typeName: Option[TypeName], // TypeName.this
    parenthesizedExpression: Option[Expression],
    classInstanceCreationExpression: Option[ClassInstanceCreationExpression],
    fieldAccess: Option[FieldAccess],
    arrayAccess: Option[ArrayAccess],
    methodInvocation: Option[MethodInvocation],
    methodReference: Option[MethodReference],
    switchExpression: Option[SwitchExpression]
)
