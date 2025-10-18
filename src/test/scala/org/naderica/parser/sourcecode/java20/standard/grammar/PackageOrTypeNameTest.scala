package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PackageOrTypeNameTest extends AnyFunSuite with Matchers {

  test("PackageOrTypeName should be created with identifier and no nested name") {
    // Given
    val identifier = Identifier("String")
    val packageOrTypeName = None

    // When
    val packageOrType = PackageOrTypeName(identifier, packageOrTypeName)

    // Then
    packageOrType.identifier shouldBe identifier
    packageOrType.packageOrTypeName shouldBe None
    packageOrType.identifier.name shouldBe "String"
  }

  test("PackageOrTypeName should be created with nested package or type name") {
    // Given
    val outerIdentifier = Identifier("java")
    val innerIdentifier = Identifier("lang")
    val innerPackageOrType = PackageOrTypeName(innerIdentifier, None)
    val outerPackageOrType = PackageOrTypeName(outerIdentifier, Some(innerPackageOrType))

    // When & Then
    outerPackageOrType.identifier.name shouldBe "java"
    outerPackageOrType.packageOrTypeName shouldBe Some(innerPackageOrType)
    outerPackageOrType.packageOrTypeName.get.identifier.name shouldBe "lang"
    outerPackageOrType.packageOrTypeName.get.packageOrTypeName shouldBe None
  }

  test("PackageOrTypeName should support equality comparison") {
    // Given
    val identifier1 = Identifier("util")
    val identifier2 = Identifier("util")
    val identifier3 = Identifier("io")
    
    val packageOrType1 = PackageOrTypeName(identifier1, None)
    val packageOrType2 = PackageOrTypeName(identifier2, None)
    val packageOrType3 = PackageOrTypeName(identifier3, None)

    // When & Then
    packageOrType1 shouldBe packageOrType2
    packageOrType1 should not be packageOrType3
    packageOrType1.hashCode() shouldBe packageOrType2.hashCode()
  }

  test("PackageOrTypeName should handle deep nesting") {
    // Given - Creating java.util.concurrent.atomic
    val atomicId = Identifier("atomic")
    val atomicPackage = PackageOrTypeName(atomicId, None)
    
    val concurrentId = Identifier("concurrent")
    val concurrentPackage = PackageOrTypeName(concurrentId, Some(atomicPackage))
    
    val utilId = Identifier("util")
    val utilPackage = PackageOrTypeName(utilId, Some(concurrentPackage))
    
    val javaId = Identifier("java")
    val javaPackage = PackageOrTypeName(javaId, Some(utilPackage))

    // When & Then
    javaPackage.identifier.name shouldBe "java"
    javaPackage.packageOrTypeName.get.identifier.name shouldBe "util"
    javaPackage.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "concurrent"
    javaPackage.packageOrTypeName.get.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "atomic"
    javaPackage.packageOrTypeName.get.packageOrTypeName.get.packageOrTypeName.get.packageOrTypeName shouldBe None
  }

  test("PackageOrTypeName should have proper toString representation") {
    // Given
    val identifier = Identifier("List")
    val packageOrTypeName = PackageOrTypeName(identifier, None)

    // When
    val stringRepresentation = packageOrTypeName.toString

    // Then
    stringRepresentation should include("PackageOrTypeName")
    stringRepresentation should include("List")
  }

  test("PackageOrTypeName case class should support pattern matching") {
    // Given
    val innerIdentifier = Identifier("String")
    val innerPackage = PackageOrTypeName(innerIdentifier, None)
    val outerIdentifier = Identifier("lang")
    val outerPackage = PackageOrTypeName(outerIdentifier, Some(innerPackage))

    // When & Then
    outerPackage match {
      case PackageOrTypeName(Identifier(name), Some(PackageOrTypeName(Identifier(innerName), None))) =>
        name shouldBe "lang"
        innerName shouldBe "String"
      case _ =>
        fail("Pattern matching failed")
    }
  }

  test("PackageOrTypeName should work with copy method") {
    // Given
    val original = PackageOrTypeName(Identifier("original"), None)
    val newIdentifier = Identifier("modified")
    val nestedPackage = PackageOrTypeName(Identifier("nested"), None)

    // When
    val copiedIdentifier = original.copy(identifier = newIdentifier)
    val copiedWithNested = original.copy(packageOrTypeName = Some(nestedPackage))

    // Then
    original.identifier.name shouldBe "original"
    original.packageOrTypeName shouldBe None

    copiedIdentifier.identifier.name shouldBe "modified"
    copiedIdentifier.packageOrTypeName shouldBe None

    copiedWithNested.identifier.name shouldBe "original"
    copiedWithNested.packageOrTypeName.get.identifier.name shouldBe "nested"
  }

  test("PackageOrTypeName should handle simple type names") {
    // Given
    val simpleTypes = List("Object", "String", "Integer", "Boolean", "List", "Map")

    // When & Then
    simpleTypes.foreach { typeName =>
      val packageOrType = PackageOrTypeName(Identifier(typeName), None)
      packageOrType.identifier.name shouldBe typeName
      packageOrType.packageOrTypeName shouldBe None
    }
  }

  test("PackageOrTypeName should handle package hierarchies") {
    // Given - Creating com.example.myapp
    val myappId = Identifier("myapp")
    val myappPackage = PackageOrTypeName(myappId, None)
    
    val exampleId = Identifier("example")
    val examplePackage = PackageOrTypeName(exampleId, Some(myappPackage))
    
    val comId = Identifier("com")
    val comPackage = PackageOrTypeName(comId, Some(examplePackage))

    // When & Then
    comPackage.identifier.name shouldBe "com"
    comPackage.packageOrTypeName.get.identifier.name shouldBe "example"
    comPackage.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "myapp"
  }

  test("PackageOrTypeName should distinguish between different structures") {
    // Given
    val simpleType = PackageOrTypeName(Identifier("String"), None)
    val qualifiedType = PackageOrTypeName(Identifier("java"), Some(PackageOrTypeName(Identifier("String"), None)))

    // When & Then
    simpleType should not be qualifiedType
    simpleType.identifier.name shouldBe "String"
    simpleType.packageOrTypeName shouldBe None
    
    qualifiedType.identifier.name shouldBe "java"
    qualifiedType.packageOrTypeName.get.identifier.name shouldBe "String"
  }
}