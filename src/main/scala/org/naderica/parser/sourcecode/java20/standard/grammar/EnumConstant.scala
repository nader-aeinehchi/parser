package org.naderica.parser.sourcecode.java20.standard.grammar

case class EnumConstant(
    enumConstantModifiers: List[EnumConstantModifier],
    identifier: Identifier,
    argumentList: Option[ArgumentList],
    classBody: Option[ClassBody]
)
