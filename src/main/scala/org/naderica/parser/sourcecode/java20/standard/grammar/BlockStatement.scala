package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait BlockStatement
case class LocalClassOrInterfaceDeclaration(
    classDeclaration: Option[ClassDeclaration],
    normalInterfaceDeclaration: Option[NormalInterfaceDeclaration]
) extends BlockStatement

case class LocalVariableDeclarationStatement(
    localVariableDeclaration: LocalVariableDeclaration
) extends BlockStatement
case class StatementBlockStatement(statement: Statement) extends BlockStatement
