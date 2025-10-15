package org.naderica.parser.sourcecode.java20.standard.grammar

case class ReceiverParameter(
      annotations: List[Annotation],
      unannType: UnannType,
      identifier: Option[Identifier]
  )