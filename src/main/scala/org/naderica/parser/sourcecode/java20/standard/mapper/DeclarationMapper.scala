package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.OrdinaryCompilationUnit
import org.naderica.parser.sourcecode.java20.standard.grammar.ModularCompilationUnit
import org.naderica.parser.sourcecode.java20.standard.grammar.TopLevelClassOrInterfaceDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.ClassDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.NormalClassDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.EnumDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.RecordDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.InterfaceDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.NormalInterfaceDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.AnnotationInterfaceDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.MethodDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.FieldDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.ConstructorDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeIdentifier

// ===============================
// DECLARATION MAPPERS
// ===============================

  object DeclarationMapper {

    // Compilation Unit mappers
    def toOrdinaryCompilationUnit(
        ctx: OrdinaryCompilationUnitContext
    ): OrdinaryCompilationUnit = {
      val packageDeclaration =
        Option(ctx.packageDeclaration()).map(PackageMapper.toPackageDeclaration)
      val importDeclarations = ctx
        .importDeclaration()
        .asScala
        .toList
        .map(ImportMapper.toImportDeclaration)
      val topLevelDeclarations = ctx
        .topLevelClassOrInterfaceDeclaration()
        .asScala
        .toList
        .map(toTopLevelClassOrInterfaceDeclaration)

      OrdinaryCompilationUnit(
        packageDeclaration = packageDeclaration,
        importDeclarations = importDeclarations,
        topLevelClassOrInterfaceDeclarations = topLevelDeclarations
      )
    }

    def toModularCompilationUnit(
        ctx: ModularCompilationUnitContext
    ): ModularCompilationUnit = {
      val importDeclarations = ctx
        .importDeclaration()
        .asScala
        .toList
        .map(ImportMapper.toImportDeclaration)
      val moduleDeclaration =
        ModuleMapper.toModuleDeclaration(ctx.moduleDeclaration())

      ModularCompilationUnit(
        importDeclarations = importDeclarations,
        moduleDeclaration = moduleDeclaration
      )
    }

    def toTopLevelClassOrInterfaceDeclaration(
        ctx: TopLevelClassOrInterfaceDeclarationContext
    ): TopLevelClassOrInterfaceDeclaration = {
      TopLevelClassOrInterfaceDeclaration(
        classDeclaration =
          Option(ctx.classDeclaration()).map(toClassDeclaration),
        interfaceDeclaration =
          Option(ctx.interfaceDeclaration()).map(toInterfaceDeclaration)
      )
    }

    // Class declaration mappers
    def toClassDeclaration(ctx: ClassDeclarationContext): ClassDeclaration = {
      if (ctx.normalClassDeclaration() != null) {
        toNormalClassDeclaration(ctx.normalClassDeclaration())
      } else if (ctx.enumDeclaration() != null) {
        toEnumDeclaration(ctx.enumDeclaration())
      } else if (ctx.recordDeclaration() != null) {
        toRecordDeclaration(ctx.recordDeclaration())
      } else {
        throw new IllegalArgumentException("Unknown class declaration type")
      }
    }

    def toNormalClassDeclaration(
        ctx: NormalClassDeclarationContext
    ): NormalClassDeclaration = {
      val modifiers =
        ctx.classModifier().asScala.toList.map(ModifierMapper.toClassModifier)
      val typeParameters =
        Option(ctx.typeParameters()).map(TypeMapper.toTypeParameters)
      val classExtends =
        // Option(ctx.classExtends()).map(ClassExtendsMapper.toClassExtends)
        Option(ctx.classExtends()).map(TypeMapper.toClassExtends)
      val classImplements = Option(ctx.classImplements())
        .map(ClassImplementsMapper.toClassImplements)
      val classPermits =
        Option(ctx.classPermits()).map(ClassPermitsMapper.toClassPermits)
      val classBody = ClassBodyMapper.toClassBody(ctx.classBody())
      val name = ctx.typeIdentifier().getText

      NormalClassDeclaration(
        classModifiers = modifiers,
        typeIdentifier = TypeIdentifier(name),
        typeParameters = typeParameters,
        classExtends = classExtends,
        classImplements = classImplements,
        classPermits = classPermits,
        classBody = classBody
      )
    }

    def toEnumDeclaration(ctx: EnumDeclarationContext): EnumDeclaration = {
      val modifiers =
        ctx.classModifier().asScala.toList.map(ModifierMapper.toClassModifier)
      val classImplements = Option(ctx.classImplements())
        .map(ClassImplementsMapper.toClassImplements)
      val enumBody = EnumMapper.toEnumBody(ctx.enumBody())
      val name = ctx.typeIdentifier().getText

      EnumDeclaration(
        classModifiers = modifiers,
        typeIdentifier = TypeIdentifier(name),
        classImplements = classImplements,
        enumBody = enumBody
      )
    }

    def toRecordDeclaration(
        ctx: RecordDeclarationContext
    ): RecordDeclaration = {
      val modifiers =
        ctx.classModifier().asScala.toList.map(ModifierMapper.toClassModifier)
      val typeParameters =
        Option(ctx.typeParameters()).map(TypeMapper.toTypeParameters)
      val recordHeader = RecordMapper.toRecordHeader(ctx.recordHeader())
      val classImplements = Option(ctx.classImplements())
        .map(ClassImplementsMapper.toClassImplements)
      val recordBody = RecordMapper.toRecordBody(ctx.recordBody())
      val name = ctx.typeIdentifier().getText

      RecordDeclaration(
        classModifiers = modifiers,
        typeIdentifier = TypeIdentifier(name),
        typeParameters = typeParameters,
        recordHeader = recordHeader,
        classImplements = classImplements,
        recordBody = recordBody
      )
    }

    // Interface declaration mappers
    def toInterfaceDeclaration(
        ctx: InterfaceDeclarationContext
    ): InterfaceDeclaration = {
      if (ctx.normalInterfaceDeclaration() != null) {
        toNormalInterfaceDeclaration(ctx.normalInterfaceDeclaration())
      } else if (ctx.annotationInterfaceDeclaration() != null) {
        toAnnotationInterfaceDeclaration(ctx.annotationInterfaceDeclaration())
      } else {
        throw new IllegalArgumentException("Unknown interface declaration type")
      }
    }

    def toNormalInterfaceDeclaration(
        ctx: NormalInterfaceDeclarationContext
    ): NormalInterfaceDeclaration = {
      val modifiers = ctx
        .interfaceModifier()
        .asScala
        .toList
        .map(ModifierMapper.toInterfaceModifier)
      val typeParameters =
        Option(ctx.typeParameters()).map(TypeMapper.toTypeParameters)
      val interfaceExtends = Option(ctx.interfaceExtends())
        // .map(InterfaceExtendsMapper.toInterfaceExtends)
        .map(InterfaceExtendsMapper.toInterfaceExtends)
      val interfacePermits = Option(ctx.interfacePermits())
        .map(InterfacePermitsMapper.toInterfacePermits)
      val interfaceBody =
        InterfaceBodyMapper.toInterfaceBody(ctx.interfaceBody())
      val name = ctx.typeIdentifier().getText

      NormalInterfaceDeclaration(
        interfaceModifiers = modifiers,
        typeIdentifier = TypeIdentifier(name),
        typeParameters = typeParameters,
        interfaceExtends = interfaceExtends,
        interfacePermits = interfacePermits,
        interfaceBody = interfaceBody
      )
    }

    def toAnnotationInterfaceDeclaration(
        ctx: AnnotationInterfaceDeclarationContext
    ): AnnotationInterfaceDeclaration = {
      val modifiers = ctx
        .interfaceModifier()
        .asScala
        .toList
        .map(ModifierMapper.toInterfaceModifier)
      val annotationInterfaceBody =
        AnnotationInterfaceBodyMapper.toAnnotationInterfaceBody(
          ctx.annotationInterfaceBody()
        )
      val name = ctx.typeIdentifier().getText

      AnnotationInterfaceDeclaration(
        interfaceModifiers = modifiers,
        typeIdentifier = TypeIdentifier(name),
        annotationInterfaceBody = annotationInterfaceBody
      )
    }

    // Method declaration mapper
    def toMethodDeclaration(
        ctx: MethodDeclarationContext
    ): MethodDeclaration = {
      val methodModifiers =
        ctx.methodModifier().asScala.toList.map(ModifierMapper.toMethodModifier)
      val methodHeader = MethodMapper.toMethodHeader(ctx.methodHeader())
      val methodBody = MethodMapper.toMethodBody(ctx.methodBody())

      MethodDeclaration(
        methodModifiers = methodModifiers,
        methodHeader = methodHeader,
        methodBody = methodBody
      )
    }

    // Field declaration mapper
    def toFieldDeclaration(ctx: FieldDeclarationContext): FieldDeclaration = {
      val fieldModifiers =
        ctx.fieldModifier().asScala.toList.map(ModifierMapper.toFieldModifier)
      val unannType = TypeMapper.toUnannType(ctx.unannType())
      val variableDeclaratorList =
        VariableMapper.toVariableDeclaratorList(ctx.variableDeclaratorList())

      FieldDeclaration(
        fieldModifiers = fieldModifiers,
        unannType = unannType,
        variableDeclaratorList = variableDeclaratorList
      )
    }

    // Constructor declaration mapper
    def toConstructorDeclaration(
        ctx: ConstructorDeclarationContext
    ): ConstructorDeclaration = {
      val constructorModifiers = ctx
        .constructorModifier()
        .asScala
        .toList
        .map(ModifierMapper.toConstructorModifier)
      val constructorDeclarator =
        ConstructorMapper.toConstructorDeclarator(ctx.constructorDeclarator())
      val throwsClause = Option(ctx.throwsT()).map(ThrowsMapper.toThrowsT)
      val constructorBody =
        ConstructorMapper.toConstructorBody(ctx.constructorBody())

      ConstructorDeclaration(
        constructorModifiers = constructorModifiers,
        constructorDeclarator = constructorDeclarator,
        throwsClause = throwsClause,
        constructorBody = constructorBody
      )
    }
  }