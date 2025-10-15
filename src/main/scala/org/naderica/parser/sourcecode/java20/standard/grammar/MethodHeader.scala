package org.naderica.parser.sourcecode.java20.standard.grammar

case class MethodHeader(
      typeParameters: Option[TypeParameters],
      result: Result,
      methodDeclarator: MethodDeclarator,
      throwsClause: Option[ThrowsT]
  )