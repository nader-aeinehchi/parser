package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait ClassBodyDeclaration
case class ClassMemberDeclaration(
    fieldDeclaration: Option[FieldDeclaration],
    methodDeclaration: Option[MethodDeclaration],
    classDeclaration: Option[ClassDeclaration],
    interfaceDeclaration: Option[InterfaceDeclaration]
) extends ClassBodyDeclaration

case class InstanceInitializer(block: Block) extends ClassBodyDeclaration
case class StaticInitializer(block: Block) extends ClassBodyDeclaration
case class ConstructorDeclaration(
    constructorModifiers: List[ConstructorModifier],
    constructorDeclarator: ConstructorDeclarator,
    throwsClause: Option[ThrowsT],
    constructorBody: ConstructorBody
) extends ClassBodyDeclaration

sealed trait RecordBodyDeclaration extends ClassBodyDeclaration
case class CompactConstructorDeclaration(
    constructorModifiers: List[ConstructorModifier],
    simpleTypeName: SimpleTypeName,
    constructorBody: ConstructorBody
) extends RecordBodyDeclaration