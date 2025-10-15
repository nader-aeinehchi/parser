package org.naderica.parser.sourcecode.java20.standard.grammar

case class TypeParameter(
      typeParameterModifiers: List[TypeParameterModifier],
      typeIdentifier: TypeIdentifier,
      typeBound: Option[TypeBound]
  )