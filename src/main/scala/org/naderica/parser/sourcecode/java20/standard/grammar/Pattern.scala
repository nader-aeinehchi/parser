package org.naderica.parser.sourcecode.java20.standard.grammar

// Patterns
sealed trait Pattern
case class TypePattern(localVariableDeclaration: LocalVariableDeclaration)
    extends Pattern
