package org.naderica.parser.sourcecode.java20.standard.grammar

case class ArrayType(
      primitiveType: Option[PrimitiveType],
      classOrInterfaceType: Option[ClassOrInterfaceType],
      typeVariable: Option[TypeVariable],
      dims: Dims
  )