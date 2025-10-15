package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait InterfaceMemberDeclaration
case class ConstantDeclaration(
    constantModifiers: List[ConstantModifier],
    unannType: UnannType,
    variableDeclaratorList: VariableDeclaratorList
) extends InterfaceMemberDeclaration

case class InterfaceMethodDeclaration(
    interfaceMethodModifiers: List[InterfaceMethodModifier],
    methodHeader: MethodHeader,
    methodBody: MethodBody
) extends InterfaceMemberDeclaration

case class InterfaceClassDeclaration(classDeclaration: ClassDeclaration)
    extends InterfaceMemberDeclaration
case class InterfaceInterfaceDeclaration(
    interfaceDeclaration: InterfaceDeclaration
) extends InterfaceMemberDeclaration
