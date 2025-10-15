package org.naderica.parser.sourcecode.java20.standard.grammar

case class MethodDeclarator(
      unqualifiedMethodIdentifier: UnqualifiedMethodIdentifier,
      receiverParameter: Option[ReceiverParameter],
      formalParameterList: Option[FormalParameterList],
      dims: Option[Dims]
  )