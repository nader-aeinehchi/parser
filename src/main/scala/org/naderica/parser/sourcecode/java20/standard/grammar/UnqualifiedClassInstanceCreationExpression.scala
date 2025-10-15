package org.naderica.parser.sourcecode.java20.standard.grammar

case class UnqualifiedClassInstanceCreationExpression(
    typeArguments: Option[TypeArguments],
    classOrInterfaceTypeToInstantiate: ClassOrInterfaceTypeToInstantiate,
    argumentList: Option[ArgumentList],
    classBody: Option[ClassBody]
)
