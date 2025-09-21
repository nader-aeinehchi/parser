package org.naderica.parser.sourcecode.java.standard

case class JModifier(modifier: JavaAccessModifier = JavaAccessModifier.PRIVATE) {
  override def toString: String = modifier.toModifier
}