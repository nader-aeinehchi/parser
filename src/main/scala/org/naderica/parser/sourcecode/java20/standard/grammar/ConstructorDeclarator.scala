package org.naderica.parser.sourcecode.java20.standard.grammar

// Constructor
  case class ConstructorDeclarator(
      typeParameters: Option[TypeParameters],
      simpleTypeName: SimpleTypeName,
      receiverParameter: Option[ReceiverParameter],
      formalParameterList: Option[FormalParameterList]
  )