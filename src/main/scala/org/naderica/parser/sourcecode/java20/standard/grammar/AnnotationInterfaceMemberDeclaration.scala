package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait AnnotationInterfaceMemberDeclaration
case class AnnotationInterfaceElementDeclaration(
    annotationInterfaceElementModifiers: List[
      AnnotationInterfaceElementModifier
    ],
    unannType: UnannType,
    identifier: Identifier,
    dims: Option[Dims],
    defaultValue: Option[DefaultValue]
) extends AnnotationInterfaceMemberDeclaration

case class AnnotationConstantDeclaration(
    constantDeclaration: ConstantDeclaration
) extends AnnotationInterfaceMemberDeclaration
case class AnnotationClassDeclaration(classDeclaration: ClassDeclaration)
    extends AnnotationInterfaceMemberDeclaration
case class AnnotationInterfaceDeclaration2(
    interfaceDeclaration: InterfaceDeclaration
) extends AnnotationInterfaceMemberDeclaration
