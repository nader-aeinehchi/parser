package org.naderica.parser.sourcecode.java20.standard.mapper
import java.util.List as JList
import scala.jdk.CollectionConverters._
import scala.util.{Try, Success, Failure}

import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser.*
import org.naderica.parser.sourcecode.java20.standard.grammar.PackageDeclaration
import org.naderica.parser.sourcecode.java20.standard.grammar.PackageName
import org.naderica.parser.sourcecode.java20.standard.grammar.Identifier

// ===============================
// SUPPORTING MAPPERS
// ===============================

object PackageMapper {
  def toPackageDeclaration(
      ctx: PackageDeclarationContext
  ): PackageDeclaration = {
    val packageModifiers = ctx
      .packageModifier()
      .asScala
      .toList
      .map(ModifierMapper.toPackageModifier)

    val packageName = ctx
      .identifier()
      .asScala
      .map(x =>
        val m_identifier =
          Identifier(x.getText)
        m_identifier
      )
      .toList

    val packageNameObj = PackageName(packageName)

    PackageDeclaration(
      packageModifiers = packageModifiers,
      packageName = packageNameObj
    )
  }

}
