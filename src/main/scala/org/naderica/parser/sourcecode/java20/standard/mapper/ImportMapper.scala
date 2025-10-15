package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.ImportDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier
import org.naderica.parser.sourcecode.java20.standard.grammar.SingleTypeImportDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.TypeImportOnDemandDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.SingleStaticImportDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.StaticImportOnDemandDeclaration

object ImportMapper {
  def toImportDeclaration(
      ctx: ImportDeclarationContext
  ): ImportDeclaration = {
    if (ctx.singleTypeImportDeclaration() != null) {
      toSingleTypeImportDeclaration(ctx.singleTypeImportDeclaration())
    } else if (ctx.typeImportOnDemandDeclaration() != null) {
      toTypeImportOnDemandDeclaration(ctx.typeImportOnDemandDeclaration())
    } else if (ctx.singleStaticImportDeclaration() != null) {
      toSingleStaticImportDeclaration(ctx.singleStaticImportDeclaration())
    } else if (ctx.staticImportOnDemandDeclaration() != null) {
      toStaticImportOnDemandDeclaration(ctx.staticImportOnDemandDeclaration())
    } else {
      throw new IllegalArgumentException("Unknown import declaration type")
    }
  }

  def toSingleTypeImportDeclaration(
      ctx: SingleTypeImportDeclarationContext
  ): SingleTypeImportDeclaration = {
    SingleTypeImportDeclaration(TypeNameMapper.toTypeName(ctx.typeName()))
  }

  def toTypeImportOnDemandDeclaration(
      ctx: TypeImportOnDemandDeclarationContext
  ): TypeImportOnDemandDeclaration = {
    TypeImportOnDemandDeclaration(
      PackageOrTypeNameMapper.toPackageOrTypeName(ctx.packageOrTypeName())
    )
  }

  def toSingleStaticImportDeclaration(
      ctx: SingleStaticImportDeclarationContext
  ): SingleStaticImportDeclaration = {
    SingleStaticImportDeclaration(
      TypeNameMapper.toTypeName(ctx.typeName()),
      Identifier(ctx.identifier().getText)
    )
  }

  def toStaticImportOnDemandDeclaration(
      ctx: StaticImportOnDemandDeclarationContext
  ): StaticImportOnDemandDeclaration = {
    StaticImportOnDemandDeclaration(TypeNameMapper.toTypeName(ctx.typeName()))
  }
}
