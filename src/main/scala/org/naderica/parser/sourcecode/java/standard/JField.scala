package org.naderica.parser.sourcecode.java.standard

case class JField(
    name: String,
    fieldType: String,
    signature: String,
    modifier: JModifier = JModifier.PRIVATE
) {
//   override def toString: String = s"${modifier.toString} $fieldType $name"
  override def toString: String = signature
}
