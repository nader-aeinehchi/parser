package org.naderica.parser.sourcecode.java20.standard.sandbox
import org.naderica.parser.sourcecode.java20.standard.JModifier

trait Delaration {

  /** The entire declaration text as it appears in the source code.
    */
  def text: String

  /** The signature of the declaration, including its name and parameter types.
    * E.g. 'public void myMethod(int param1, String param2)'. E.g. 'package
    * org.example.myapp' E.g. 'public class MyClass extends BaseClass implements
    * MyInterface' E.g. 'private int myField = 42;'
    */
  def signature: String

  /** The name of the declared entity (e.g., class name, method name, field
    * name).
    */
  def name: String

  /** The access modifier of the declaration (e.g., public, private, protected
    * or default that is empty).
    */
  def modifier: JModifier

  /** The kind of declaration (e.g., "package", "class", "interface", "method"
    * or "field").
    */
  def kind: Kind

  /** Denotes the type of the declared entity, e.g. String, Account or void.
    */
  def denotation: String

  override def toString: String = signature

}
