package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ModuleDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.ModuleName
import org.naderica.parser.sourcecode.java20.standard.grammar.ModuleDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.RequiresDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.ExportsDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.OpensDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.UsesDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.ProvidesDirective
import org.naderica.parser.sourcecode.java20.standard.grammar.RequiresModifier
import org.naderica.parser.sourcecode.java20.standard.grammar.TransitiveRequiresModifier
import org.naderica.parser.sourcecode.java20.standard.grammar.StaticRequiresModifier
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier
import org.naderica.parser.sourcecode.java20.standard.grammar.PackageName

object ModuleMapper {
  def toModuleDeclaration(ctx: ModuleDeclarationContext): ModuleDeclaration = {
    import scala.jdk.CollectionConverters._
    
    val moduleModifiers = ctx.annotation().asScala.map(AnnotationMapper.toAnnotation).toList
    val open = if (ctx.OPEN() != null) Some("open") else None
    val moduleName = toModuleName(ctx.identifier().asScala.toList)
    val moduleDirectives = ctx.moduleDirective().asScala.map(toModuleDirective).toList

    ModuleDeclaration(
      moduleModifiers = moduleModifiers,
      open = open,
      moduleName = moduleName,
      moduleDirectives = moduleDirectives
    )
  }

  def toModuleName(identifiers: List[IdentifierContext]): ModuleName = {
    ModuleName(
      identifiers = identifiers.map(id => Identifier(id.getText))
    )
  }

  def toModuleDirective(ctx: ModuleDirectiveContext): ModuleDirective = {
    import scala.jdk.CollectionConverters._
    
    if (ctx.REQUIRES() != null) {
      // requires directive
      val requiresModifiers = ctx.requiresModifier().asScala.map(toRequiresModifier).toList
      val moduleName = toModuleNameFromContext(ctx.moduleName(0))
      
      RequiresDirective(
        requiresModifiers = requiresModifiers,
        moduleName = moduleName
      )
    } else if (ctx.EXPORTS() != null) {
      // exports directive
      val packageName = toPackageName(ctx.packageName())
      val moduleNames = if (ctx.TO() != null) {
        Some(ctx.moduleName().asScala.map(toModuleNameFromContext).toList)
      } else {
        None
      }
      
      ExportsDirective(
        packageName = packageName,
        moduleNames = moduleNames
      )
    } else if (ctx.OPENS() != null) {
      // opens directive
      val packageName = toPackageName(ctx.packageName())
      val moduleNames = if (ctx.TO() != null) {
        Some(ctx.moduleName().asScala.map(toModuleNameFromContext).toList)
      } else {
        None
      }
      
      OpensDirective(
        packageName = packageName,
        moduleNames = moduleNames
      )
    } else if (ctx.USES() != null) {
      // uses directive
      val typeName = TypeNameMapper.toTypeName(ctx.typeName(0))
      
      UsesDirective(typeName = typeName)
    } else if (ctx.PROVIDES() != null) {
      // provides directive
      val typeName = TypeNameMapper.toTypeName(ctx.typeName(0))
      val withTypeNames = ctx.typeName().asScala.drop(1).map(TypeNameMapper.toTypeName).toList
      
      ProvidesDirective(
        typeName = typeName,
        withTypeNames = withTypeNames
      )
    } else {
      throw new IllegalArgumentException("Unknown module directive type")
    }
  }

  def toPackageName(ctx: PackageNameContext): PackageName = {
    def collectIdentifiers(ctx: PackageNameContext): List[Identifier] = {
      val currentId = Identifier(ctx.identifier().getText)
      if (ctx.packageName() != null) {
        collectIdentifiers(ctx.packageName()) :+ currentId
      } else {
        List(currentId)
      }
    }
    
    PackageName(identifiers = collectIdentifiers(ctx))
  }

  def toModuleNameFromContext(ctx: ModuleNameContext): ModuleName = {
    def collectIdentifiers(ctx: ModuleNameContext): List[Identifier] = {
      val currentId = Identifier(ctx.identifier().getText)
      if (ctx.moduleName() != null) {
        collectIdentifiers(ctx.moduleName()) :+ currentId
      } else {
        List(currentId)
      }
    }
    
    ModuleName(identifiers = collectIdentifiers(ctx))
  }

  def toRequiresModifier(ctx: RequiresModifierContext): RequiresModifier = {
    if (ctx.TRANSITIVE() != null) {
      TransitiveRequiresModifier
    } else if (ctx.STATIC() != null) {
      StaticRequiresModifier
    } else {
      throw new IllegalArgumentException("Unknown requires modifier")
    }
  }
}