package org.naderica.parser.sourcecode.java20.standard.grammar

// For statements
case class BasicForStatement(
    forInit: Option[ForInit],
    expression: Option[Expression],
    forUpdate: Option[ForUpdate],
    statement: Statement
)
