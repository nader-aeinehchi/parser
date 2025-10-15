package org.naderica.parser.sourcecode.java20.standard.sandbox

enum Kind {
  case PACKAGE, CLASS, INTERFACE, ENUM, METHOD, FIELD, CONSTRUCTOR, ANNOTATION

  override def toString: String = this match {
    case PACKAGE     => "package"
    case CLASS       => "class"
    case INTERFACE   => "interface"
    case ENUM        => "enum"
    case METHOD      => "method"
    case FIELD       => "field"
    case CONSTRUCTOR => "constructor"
    case ANNOTATION  => "annotation"
  }
}