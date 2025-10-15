package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait RequiresModifier
case object TransitiveRequiresModifier extends RequiresModifier
case object StaticRequiresModifier extends RequiresModifier
