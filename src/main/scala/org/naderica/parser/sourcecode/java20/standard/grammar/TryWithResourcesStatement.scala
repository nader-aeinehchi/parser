package org.naderica.parser.sourcecode.java20.standard.grammar

// Try-with-resources
case class TryWithResourcesStatement(
    resourceSpecification: ResourceSpecification,
    block: Block,
    catches: Option[Catches],
    finallyBlock: Option[FinallyBlock]
)
