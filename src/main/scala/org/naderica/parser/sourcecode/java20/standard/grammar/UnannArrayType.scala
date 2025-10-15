package org.naderica.parser.sourcecode.java20.standard.grammar

case class UnannArrayType(
      unannPrimitiveType: Option[UnannPrimitiveType],
      unannClassOrInterfaceType: Option[UnannClassOrInterfaceType],
      unannTypeVariable: Option[UnannTypeVariable],
      dims: Dims
  )