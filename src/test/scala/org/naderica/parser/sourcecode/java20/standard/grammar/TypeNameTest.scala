package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypeNameTest extends AnyFunSuite with Matchers {

  test("TypeName should be created with just a TypeIdentifier") {
    // Given
    val typeIdentifier = TypeIdentifier("String")
    val packageOrTypeName = None

    // When
    val typeName = TypeName(typeIdentifier, packageOrTypeName)

    // Then
    typeName.typeIdentifier shouldBe typeIdentifier
    typeName.packageOrTypeName shouldBe None
    typeName.typeIdentifier.name shouldBe "String"
  }

  test("TypeName should be created with TypeIdentifier and PackageOrTypeName") {
    // Given
    val typeIdentifier = TypeIdentifier("String")
    val packageOrTypeName = Some(PackageOrTypeName(Identifier("lang"), 
      Some(PackageOrTypeName(Identifier("java"), None))))

    // When
    val typeName = TypeName(typeIdentifier, packageOrTypeName)

    // Then
    typeName.typeIdentifier.name shouldBe "String"
    typeName.packageOrTypeName.get.identifier.name shouldBe "lang"
    typeName.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "java"
  }

  test("TypeName should support equality comparison") {
    // Given
    val typeId1 = TypeIdentifier("Integer")
    val typeId2 = TypeIdentifier("Integer")
    val typeId3 = TypeIdentifier("Boolean")
    
    val typeName1 = TypeName(typeId1, None)
    val typeName2 = TypeName(typeId2, None)
    val typeName3 = TypeName(typeId3, None)

    // When & Then
    typeName1 shouldBe typeName2
    typeName1 should not be typeName3
    typeName1.hashCode() shouldBe typeName2.hashCode()
  }

  test("TypeName should handle primitive types") {
    // Given
    val primitiveTypes = List("int", "double", "boolean", "char", "byte", "short", "long", "float")

    // When & Then
    primitiveTypes.foreach { primitive =>
      val typeName = TypeName(TypeIdentifier(primitive), None)
      typeName.typeIdentifier.name shouldBe primitive
      typeName.packageOrTypeName shouldBe None
    }
  }

  test("TypeName should handle fully qualified types") {
    // Given - java.util.List
    val typeIdentifier = TypeIdentifier("List")
    val utilPackage = PackageOrTypeName(Identifier("util"), None)
    val javaPackage = PackageOrTypeName(Identifier("java"), Some(utilPackage))
    val packageOrTypeName = Some(javaPackage)

    // When
    val typeName = TypeName(typeIdentifier, packageOrTypeName)

    // Then
    typeName.typeIdentifier.name shouldBe "List"
    typeName.packageOrTypeName.get.identifier.name shouldBe "java"
    typeName.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "util"
  }

  test("TypeName should handle simple reference types") {
    // Given
    val referenceTypes = List("String", "Object", "Thread", "File")

    // When & Then
    referenceTypes.foreach { refType =>
      val typeName = TypeName(TypeIdentifier(refType), None)
      typeName.typeIdentifier.name shouldBe refType
      typeName.packageOrTypeName shouldBe None
    }
  }

  test("TypeName should handle generic types") {
    // Given
    val genericTypes = List("List", "Map", "Set", "Optional")

    // When & Then
    genericTypes.foreach { genericType =>
      val typeName = TypeName(TypeIdentifier(genericType), None)
      typeName.typeIdentifier.name shouldBe genericType
      typeName.packageOrTypeName shouldBe None
    }
  }

  test("TypeName should have proper toString representation") {
    // Given
    val typeName = TypeName(TypeIdentifier("ArrayList"), None)

    // When
    val stringRepresentation = typeName.toString

    // Then
    stringRepresentation should include("TypeName")
    stringRepresentation should include("ArrayList")
  }

  test("TypeName case class should support pattern matching") {
    // Given
    val packageOrType = PackageOrTypeName(Identifier("util"), None)
    val typeName = TypeName(TypeIdentifier("HashMap"), Some(packageOrType))

    // When & Then
    typeName match {
      case TypeName(TypeIdentifier(name), Some(PackageOrTypeName(Identifier(packageName), None))) =>
        name shouldBe "HashMap"
        packageName shouldBe "util"
      case _ =>
        fail("Pattern matching failed")
    }
  }

  test("TypeName should work with copy method") {
    // Given
    val original = TypeName(TypeIdentifier("Original"), None)
    val newTypeIdentifier = TypeIdentifier("Modified")
    val newPackage = Some(PackageOrTypeName(Identifier("com"), None))

    // When
    val copiedType = original.copy(typeIdentifier = newTypeIdentifier)
    val copiedPackage = original.copy(packageOrTypeName = newPackage)

    // Then
    original.typeIdentifier.name shouldBe "Original"
    original.packageOrTypeName shouldBe None

    copiedType.typeIdentifier.name shouldBe "Modified"
    copiedType.packageOrTypeName shouldBe None

    copiedPackage.typeIdentifier.name shouldBe "Original"
    copiedPackage.packageOrTypeName.get.identifier.name shouldBe "com"
  }

  test("TypeName should distinguish qualified vs unqualified types") {
    // Given
    val unqualifiedType = TypeName(TypeIdentifier("String"), None)
    val qualifiedType = TypeName(TypeIdentifier("String"), 
      Some(PackageOrTypeName(Identifier("lang"), None)))

    // When & Then
    unqualifiedType should not be qualifiedType
    unqualifiedType.typeIdentifier.name shouldBe "String"
    unqualifiedType.packageOrTypeName shouldBe None

    qualifiedType.typeIdentifier.name shouldBe "String"
    qualifiedType.packageOrTypeName.get.identifier.name shouldBe "lang"
  }

  test("TypeName should handle nested class types") {
    // Given - OuterClass.InnerClass
    val typeIdentifier = TypeIdentifier("InnerClass")
    val outerClassPackage = PackageOrTypeName(Identifier("OuterClass"), None)

    // When
    val typeName = TypeName(typeIdentifier, Some(outerClassPackage))

    // Then
    typeName.typeIdentifier.name shouldBe "InnerClass"
    typeName.packageOrTypeName.get.identifier.name shouldBe "OuterClass"
  }

  test("TypeName should handle custom package hierarchies") {
    // Given - com.example.myapp.MyClass
    val typeIdentifier = TypeIdentifier("MyClass")
    val myappPackage = PackageOrTypeName(Identifier("myapp"), None)
    val examplePackage = PackageOrTypeName(Identifier("example"), Some(myappPackage))
    val comPackage = PackageOrTypeName(Identifier("com"), Some(examplePackage))

    // When
    val typeName = TypeName(typeIdentifier, Some(comPackage))

    // Then
    typeName.typeIdentifier.name shouldBe "MyClass"
    typeName.packageOrTypeName.get.identifier.name shouldBe "com"
    typeName.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "example"
    typeName.packageOrTypeName.get.packageOrTypeName.get.packageOrTypeName.get.identifier.name shouldBe "myapp"
  }
}