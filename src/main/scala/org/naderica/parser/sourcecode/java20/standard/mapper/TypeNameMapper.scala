package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeIdentifier
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeName
import org.naderica.parser.sourcecode.java20.standard.grammar.PackageOrTypeName
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier

object TypeNameMapper {
    def toTypeName(ctx: TypeNameContext): TypeName = {
      // According to ANTLR grammar: typeName: packageName (DOT typeIdentifier)?
      // This means we have a packageName, and optionally a typeIdentifier
      
      if (ctx.typeIdentifier() != null) {
        // Case: packageName DOT typeIdentifier (e.g., "java.util.List")
        TypeName(
          typeIdentifier = TypeIdentifier(ctx.typeIdentifier().getText),
          packageOrTypeName = Some(packageNameToPackageOrTypeName(ctx.packageName()))
        )
      } else {
        // Case: just packageName (e.g., "java.util")
        // In this case, the last identifier in the packageName becomes the typeIdentifier
        val packageOrTypeName = packageNameToPackageOrTypeName(ctx.packageName())
        extractLastIdentifierAsType(packageOrTypeName)
      }
    }
    
    private def packageNameToPackageOrTypeName(ctx: PackageNameContext): PackageOrTypeName = {
      val identifier = 
        org.naderica.parser.sourcecode.java20.standard.grammar.Identifier(ctx.identifier().getText)
      val nestedPackageName = Option(ctx.packageName()).map(packageNameToPackageOrTypeName)
      
      PackageOrTypeName(
        identifier = identifier,
        packageOrTypeName = nestedPackageName
      )
    }
    
    private def extractLastIdentifierAsType(packageOrTypeName: PackageOrTypeName): TypeName = {
      packageOrTypeName.packageOrTypeName match {
        case None =>
          // This is the last identifier, use it as typeIdentifier
          TypeName(
            typeIdentifier = TypeIdentifier(packageOrTypeName.identifier.name),
            packageOrTypeName = None
          )
        case Some(nested) =>
          // Recursively extract, and use current identifier as part of package
          val innerTypeName = extractLastIdentifierAsType(nested)
          TypeName(
            typeIdentifier = innerTypeName.typeIdentifier,
            packageOrTypeName = Some(PackageOrTypeName(
              identifier = packageOrTypeName.identifier,
              packageOrTypeName = innerTypeName.packageOrTypeName
            ))
          )
      }
    }
  }