package org.naderica.parser.sourcecode.java20.standard.grammar

sealed trait WildcardBounds
case class ExtendsWildcardBounds(referenceType: ReferenceType)
    extends WildcardBounds
case class SuperWildcardBounds(referenceType: ReferenceType)
    extends WildcardBounds
