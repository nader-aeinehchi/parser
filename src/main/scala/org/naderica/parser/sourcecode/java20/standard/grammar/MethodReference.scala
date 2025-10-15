package org.naderica.parser.sourcecode.java20.standard.grammar

case class MethodReference(
    expressionName: Option[ExpressionName],
    primary: Option[Primary],
    referenceType: Option[ReferenceType],
    super_ : Option[String], // "super"
    typeName: Option[TypeName], // TypeName.super
    classType: Option[ClassType],
    arrayType: Option[ArrayType],
    typeArguments: Option[TypeArguments],
    identifier: Option[Identifier],
    new_ : Option[String] // "new"
)
