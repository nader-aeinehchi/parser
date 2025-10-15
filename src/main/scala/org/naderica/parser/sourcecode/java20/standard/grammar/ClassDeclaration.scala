package org.naderica.parser.sourcecode.java20.standard.grammar

// Class declarations
sealed trait ClassDeclaration
case class NormalClassDeclaration(
    classModifiers: List[ClassModifier],
    typeIdentifier: TypeIdentifier,
    typeParameters: Option[TypeParameters],
    classExtends: Option[ClassExtends],
    classImplements: Option[ClassImplements],
    classPermits: Option[ClassPermits],
    classBody: ClassBody
) extends ClassDeclaration

case class EnumDeclaration(
    classModifiers: List[ClassModifier],
    typeIdentifier: TypeIdentifier,
    classImplements: Option[ClassImplements],
    enumBody: EnumBody
) extends ClassDeclaration

case class RecordDeclaration(
    classModifiers: List[ClassModifier],
    typeIdentifier: TypeIdentifier,
    typeParameters: Option[TypeParameters],
    recordHeader: RecordHeader,
    classImplements: Option[ClassImplements],
    recordBody: RecordBody
) extends ClassDeclaration