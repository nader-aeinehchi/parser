package org.naderica.parser.sourcecode.java20.standard.grammar

// Package and imports
  case class PackageDeclaration(
      packageModifiers: List[PackageModifier],
      packageName: PackageName
  )