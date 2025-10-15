package org.naderica.parser.sourcecode.java20.standard.grammar

case class UnannClassType(
      typeIdentifier: TypeIdentifier,
      typeArguments: Option[TypeArguments],
      nestedUnannClassType: Option[UnannClassType]
  )