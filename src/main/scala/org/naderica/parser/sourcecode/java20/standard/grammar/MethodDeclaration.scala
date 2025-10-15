package org.naderica.parser.sourcecode.java20.standard.grammar

// Method declaration
case class MethodDeclaration(
    methodModifiers: List[MethodModifier],
    methodHeader: MethodHeader,
    methodBody: MethodBody
)
