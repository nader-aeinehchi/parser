package org.naderica.parser.sourcecode.java20.standard.grammar

case class ClassInstanceCreationExpression(
    unqualifiedClassInstanceCreationExpression: UnqualifiedClassInstanceCreationExpression,
    primary: Option[Primary]
)
