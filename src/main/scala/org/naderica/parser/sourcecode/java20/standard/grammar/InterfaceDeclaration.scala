package org.naderica.parser.sourcecode.java20.standard.grammar

// Interface declarations
sealed trait InterfaceDeclaration
case class NormalInterfaceDeclaration(
    interfaceModifiers: List[InterfaceModifier],
    typeIdentifier: TypeIdentifier,
    typeParameters: Option[TypeParameters],
    interfaceExtends: Option[InterfaceExtends],
    interfacePermits: Option[InterfacePermits],
    interfaceBody: InterfaceBody
) extends InterfaceDeclaration

case class AnnotationInterfaceDeclaration(
    interfaceModifiers: List[InterfaceModifier],
    typeIdentifier: TypeIdentifier,
    annotationInterfaceBody: AnnotationInterfaceBody
) extends InterfaceDeclaration
