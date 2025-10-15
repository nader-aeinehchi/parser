package org.naderica.parser.sourcecode.java20.standard.grammar

// float, double

// Reference types
case class ClassOrInterfaceType(
    classType: Option[ClassType],
    interfaceType: Option[InterfaceType]
)
