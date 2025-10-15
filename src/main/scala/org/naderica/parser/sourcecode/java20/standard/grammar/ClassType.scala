package org.naderica.parser.sourcecode.java20.standard.grammar

case class ClassType(
      annotations: List[Annotation],
      typeIdentifier: TypeIdentifier,
      typeArguments: Option[TypeArguments],
      nestedClassType: Option[ClassType]
  )