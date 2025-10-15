package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.*

object ModifierMapper {
    def toClassModifier(ctx: ClassModifierContext): ClassModifier = {
      if (ctx.PUBLIC() != null) ClassModifier.Public
      else if (ctx.PROTECTED() != null) ClassModifier.Protected
      else if (ctx.PRIVATE() != null) ClassModifier.Private
      else if (ctx.ABSTRACT() != null) ClassModifier.Abstract
      else if (ctx.STATIC() != null) ClassModifier.Static
      else if (ctx.FINAL() != null) ClassModifier.Final
      else if (ctx.SEALED() != null) ClassModifier.Sealed
      else if (ctx.NONSEALED() != null) ClassModifier.NonSealed
      else if (ctx.STRICTFP() != null) ClassModifier.Strictfp
      else
        throw new IllegalArgumentException(
          s"Unknown class modifier: ${ctx.getText}"
        )
    }

    def toClassModifier(
        modifiers: JList[ClassModifierContext]
    ): List[ClassModifier] = {
      modifiers.asScala.map(x => toClassModifier(x)).toList
    }

    def toInterfaceModifier(
        ctx: InterfaceModifierContext
    ): InterfaceModifier = {
      if (ctx.PUBLIC() != null) InterfaceModifier.Public
      else if (ctx.PROTECTED() != null) InterfaceModifier.Protected
      else if (ctx.PRIVATE() != null) InterfaceModifier.Private
      else if (ctx.ABSTRACT() != null) InterfaceModifier.Abstract
      else if (ctx.STATIC() != null) InterfaceModifier.Static
      else if (ctx.SEALED() != null) InterfaceModifier.Sealed
      else if (ctx.NONSEALED() != null) InterfaceModifier.NonSealed
      else if (ctx.STRICTFP() != null) InterfaceModifier.Strictfp
      else
        throw new IllegalArgumentException(
          s"Unknown interface modifier: ${ctx.getText}"
        )
    }

    def toInterfaceModifier(
        modifiers: JList[InterfaceModifierContext]
    ): List[InterfaceModifier] = {
      modifiers.asScala.map(x => toInterfaceModifier(x)).toList
    }

    def toFieldModifier(ctx: FieldModifierContext): FieldModifier = {
      if (ctx.PUBLIC() != null) FieldModifier.Public
      else if (ctx.PROTECTED() != null) FieldModifier.Protected
      else if (ctx.PRIVATE() != null) FieldModifier.Private
      else if (ctx.STATIC() != null) FieldModifier.Static
      else if (ctx.FINAL() != null) FieldModifier.Final
      else if (ctx.TRANSIENT() != null) FieldModifier.Transient
      else if (ctx.VOLATILE() != null) FieldModifier.Volatile
      else
        throw new IllegalArgumentException(
          s"Unknown field modifier: ${ctx.getText}"
        )
    }

    def toFieldModifier(
        modifiers: JList[FieldModifierContext]
    ): List[FieldModifier] = {
      modifiers.asScala.map(x => toFieldModifier(x)).toList
    }

    def toMethodModifier(ctx: MethodModifierContext): MethodModifier = {
      if (ctx.PUBLIC() != null) MethodModifier.Public
      else if (ctx.PROTECTED() != null) MethodModifier.Protected
      else if (ctx.PRIVATE() != null) MethodModifier.Private
      else if (ctx.ABSTRACT() != null) MethodModifier.Abstract
      else if (ctx.STATIC() != null) MethodModifier.Static
      else if (ctx.FINAL() != null) MethodModifier.Final
      else if (ctx.SYNCHRONIZED() != null) MethodModifier.Synchronized
      else if (ctx.NATIVE() != null) MethodModifier.Native
      else if (ctx.STRICTFP() != null) MethodModifier.Strictfp
      else
        throw new IllegalArgumentException(
          s"Unknown method modifier: ${ctx.getText}"
        )
    }

    def toMethodModifier(
        modifiers: JList[MethodModifierContext]
    ): List[MethodModifier] = {
      modifiers.asScala.map(x => toMethodModifier(x)).toList
    }

    def toConstructorModifier(
        ctx: ConstructorModifierContext
    ): ConstructorModifier = {
      if (ctx.PUBLIC() != null) ConstructorModifier.Public
      else if (ctx.PROTECTED() != null) ConstructorModifier.Protected
      else if (ctx.PRIVATE() != null) ConstructorModifier.Private
      else
        throw new IllegalArgumentException(
          s"Unknown constructor modifier: ${ctx.getText}"
        )
    }

    def toConstructorModifier(
        modifiers: JList[ConstructorModifierContext]
    ): List[ConstructorModifier] = {
      modifiers.asScala.map(x => toConstructorModifier(x)).toList
    }

    def toConstantModifier(ctx: ConstantModifierContext): ConstantModifier = {
      if (ctx.PUBLIC() != null) ConstantModifier.Public
      else if (ctx.STATIC() != null) ConstantModifier.Static
      else if (ctx.FINAL() != null) ConstantModifier.Final
      else
        throw new IllegalArgumentException(
          s"Unknown constant modifier: ${ctx.getText}"
        )
    }

    def toConstantModifier(
        modifiers: JList[ConstantModifierContext]
    ): List[ConstantModifier] = {
      modifiers.asScala.map(x => toConstantModifier(x)).toList
    }

    def toInterfaceMethodModifier(
        ctx: InterfaceMethodModifierContext
    ): InterfaceMethodModifier = {
      if (ctx.PUBLIC() != null) InterfaceMethodModifier.Public
      else if (ctx.PRIVATE() != null) InterfaceMethodModifier.Private
      else if (ctx.ABSTRACT() != null) InterfaceMethodModifier.Abstract
      else if (ctx.DEFAULT() != null) InterfaceMethodModifier.Default
      else if (ctx.STATIC() != null) InterfaceMethodModifier.Static
      else if (ctx.STRICTFP() != null) InterfaceMethodModifier.Strictfp
      else
        throw new IllegalArgumentException(
          s"Unknown interface method modifier: ${ctx.getText}"
        )
    }

    def toInterfaceMethodModifier(
        modifiers: JList[InterfaceMethodModifierContext]
    ): List[InterfaceMethodModifier] = {
      modifiers.asScala.map(x => toInterfaceMethodModifier(x)).toList
    }

    def toVariableModifier(ctx: VariableModifierContext): VariableModifier = {
      if (ctx.FINAL() != null) VariableModifier.Final
      else
        throw new IllegalArgumentException(
          s"Unknown variable modifier: ${ctx.getText}"
        )
    }

    def toVariableModifier(
        modifiers: JList[VariableModifierContext]
    ): List[VariableModifier] = {
      modifiers.asScala.map(x => toVariableModifier(x)).toList
    }

    def toPackageModifier(ctx: PackageModifierContext): PackageModifier = {
      PackageModifier(AnnotationMapper.toAnnotation(ctx.annotation()))
    }

    def toPackageModifier(
        modifiers: JList[PackageModifierContext]
    ): List[PackageModifier] = {
      modifiers.asScala.map(x => toPackageModifier(x)).toList
    }

    def toEnumConstantModifier(
        ctx: EnumConstantModifierContext
    ): EnumConstantModifier = {
      EnumConstantModifier.Annotation // Only annotations allowed
    }

    def toEnumConstantModifier(
        modifiers: JList[EnumConstantModifierContext]
    ): List[EnumConstantModifier] = {
      modifiers.asScala.map(x => toEnumConstantModifier(x)).toList
    }

    def toRecordComponentModifier(
        ctx: RecordComponentModifierContext
    ): RecordComponentModifier = {
      RecordComponentModifier.Annotation // Only annotations allowed
    }

    def toRecordComponentModifier(
        modifiers: JList[RecordComponentModifierContext]
    ): List[RecordComponentModifier] = {
      modifiers.asScala.map(x => toRecordComponentModifier(x)).toList
    }

    def toTypeParameterModifier(
        ctx: TypeParameterModifierContext
    ): TypeParameterModifier = {
      TypeParameterModifier.Annotation // Only annotations allowed
    }

    def toTypeParameterModifier(
        modifiers: JList[TypeParameterModifierContext]
    ): List[TypeParameterModifier] = {
      modifiers.asScala.map(x => toTypeParameterModifier(x)).toList
    }

    def toAnnotationInterfaceElementModifier(
        ctx: AnnotationInterfaceElementModifierContext
    ): AnnotationInterfaceElementModifier = {
      if (ctx.PUBLIC() != null) AnnotationInterfaceElementModifier.Public
      else if (ctx.ABSTRACT() != null)
        AnnotationInterfaceElementModifier.Abstract
      else
        throw new IllegalArgumentException(
          s"Unknown annotation interface element modifier: ${ctx.getText}"
        )
    }

    def toAnnotationInterfaceElementModifier(
        modifiers: JList[AnnotationInterfaceElementModifierContext]
    ): List[AnnotationInterfaceElementModifier] = {
      modifiers.asScala.map(x => toAnnotationInterfaceElementModifier(x)).toList
    }
  }