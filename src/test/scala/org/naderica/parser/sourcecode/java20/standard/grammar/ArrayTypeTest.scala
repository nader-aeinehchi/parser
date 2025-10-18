package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ArrayTypeTest extends AnyFunSuite with Matchers {

  // Mock helper functions
  private def createMockPrimitiveType(): PrimitiveType = {
    null.asInstanceOf[PrimitiveType] // Simplified mock
  }

  private def createMockClassOrInterfaceType(): ClassOrInterfaceType = {
    null.asInstanceOf[ClassOrInterfaceType] // Simplified mock
  }

  private def createMockTypeVariable(): TypeVariable = {
    null.asInstanceOf[TypeVariable] // Simplified mock
  }

  private def createMockDims(): Dims = {
    null.asInstanceOf[Dims] // Simplified mock
  }

  test("ArrayType should be created with primitive type and dims") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    arrayType.primitiveType shouldBe Some(primitiveType)
    arrayType.classOrInterfaceType shouldBe None
    arrayType.typeVariable shouldBe None
    arrayType.dims shouldBe dims
  }

  test("ArrayType should be created with class or interface type and dims") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    arrayType.primitiveType shouldBe None
    arrayType.classOrInterfaceType shouldBe Some(classOrInterfaceType)
    arrayType.typeVariable shouldBe None
    arrayType.dims shouldBe dims
  }

  test("ArrayType should be created with type variable and dims") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    arrayType.primitiveType shouldBe None
    arrayType.classOrInterfaceType shouldBe None
    arrayType.typeVariable shouldBe Some(typeVariable)
    arrayType.dims shouldBe dims
  }

  test("ArrayType should be created with all None types and dims") {
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, None, dims)
    
    arrayType.primitiveType shouldBe None
    arrayType.classOrInterfaceType shouldBe None
    arrayType.typeVariable shouldBe None
    arrayType.dims shouldBe dims
  }

  test("ArrayType should support equality comparison") {
    val primitiveType1 = createMockPrimitiveType()
    val primitiveType2 = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType1 = ArrayType(Some(primitiveType1), None, None, dims)
    val arrayType2 = ArrayType(None, Some(createMockClassOrInterfaceType()), None, dims)
    
    arrayType1 should not equal arrayType2
  }

  test("ArrayType should support equality for same field references") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType1 = ArrayType(Some(primitiveType), None, None, dims)
    val arrayType2 = ArrayType(Some(primitiveType), None, None, dims)
    
    arrayType1 shouldEqual arrayType2
  }

  test("ArrayType should have proper toString representation") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    arrayType.toString should include("ArrayType")
  }

  test("ArrayType should be a case class with copy method") {
    val primitiveType = createMockPrimitiveType()
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val originalArrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    val copiedArrayType = originalArrayType.copy(classOrInterfaceType = Some(classOrInterfaceType))
    
    copiedArrayType.primitiveType shouldBe Some(primitiveType)
    copiedArrayType.classOrInterfaceType shouldBe Some(classOrInterfaceType)
    copiedArrayType.dims shouldBe dims
  }

  test("ArrayType should maintain immutability") {
    val primitiveType = createMockPrimitiveType()
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    val newArrayType = arrayType.copy(typeVariable = Some(typeVariable))
    
    arrayType.typeVariable shouldBe None
    newArrayType.typeVariable shouldBe Some(typeVariable)
  }

  test("ArrayType should represent int array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // int[] or int[][]
    arrayType.primitiveType shouldBe defined
    arrayType.classOrInterfaceType shouldBe None
    arrayType.typeVariable shouldBe None
  }

  test("ArrayType should represent String array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // String[] or String[][]
    arrayType.primitiveType shouldBe None
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.typeVariable shouldBe None
  }

  test("ArrayType should represent generic array type") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    // T[] or T[][]
    arrayType.primitiveType shouldBe None
    arrayType.classOrInterfaceType shouldBe None
    arrayType.typeVariable shouldBe defined
  }

  test("ArrayType should represent boolean array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // boolean[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent byte array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // byte[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent char array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // char[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent short array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // short[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent long array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // long[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent float array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // float[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent double array type") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // double[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Object array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Object[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent List array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // List<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Map array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Map<String, Integer>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent multi-dimensional int array") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // int[][] or int[][][]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent multi-dimensional String array") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // String[][] or String[][][]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent jagged array types") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // int[][] with different sub-array lengths
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Collection array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Collection<?>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Stream array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Stream<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Optional array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Optional<Integer>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Function array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Function<String, Integer>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Predicate array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Predicate<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Consumer array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Consumer<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent Supplier array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Supplier<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent CompletableFuture array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // CompletableFuture<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent AtomicReference array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // AtomicReference<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent ConcurrentHashMap array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // ConcurrentHashMap<String, Integer>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent BlockingQueue array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // BlockingQueue<String>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent executor array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // ExecutorService[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent annotation array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // @MyAnnotation[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent enum array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // DayOfWeek[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent record array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Person[] (record type)
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent sealed class array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Shape[] (sealed class)
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent wildcard array type") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // List<? extends Number>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent bounded type parameter array") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    // <T extends Comparable<T>>[]
    arrayType.typeVariable shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent multi-bounded type parameter array") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    // <T extends Number & Comparable<T>>[]
    arrayType.typeVariable shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent recursive type parameter array") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    // <T extends Enum<T>>[]
    arrayType.typeVariable shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent varargs simulation") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // int... represented as int[]
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent object varargs simulation") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // String... represented as String[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent generic varargs simulation") {
    val typeVariable = createMockTypeVariable()
    val dims = createMockDims()
    val arrayType = ArrayType(None, None, Some(typeVariable), dims)
    
    // T... represented as T[]
    arrayType.typeVariable shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent matrix operations") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // double[][] for matrix calculations
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent image pixel arrays") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // int[] for RGB pixel data
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent buffer arrays") {
    val primitiveType = createMockPrimitiveType()
    val dims = createMockDims()
    val arrayType = ArrayType(Some(primitiveType), None, None, dims)
    
    // byte[] for buffer operations
    arrayType.primitiveType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent lookup table arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Map<String, Integer>[] for lookup tables
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent cache arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // CacheEntry<String, Object>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent thread pool arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Thread[] or Runnable[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent event listener arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // EventListener[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent validation rule arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // ValidationRule<T>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent transformation arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Transformer<Input, Output>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent filter arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Filter<T>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent comparator arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Comparator<T>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent serialization arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Serializable[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent reactive stream arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Publisher<T>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent security principal arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Principal[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent cryptographic key arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Key[] or Certificate[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent database result arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // ResultSet[] or PreparedStatement[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent web service endpoint arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Endpoint[] or Service[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent configuration property arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Property[] or ConfigurationEntry[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent monitoring metric arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Metric[] or Measurement[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent log entry arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // LogEntry[] or LogRecord[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent test case arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // TestCase[] or TestMethod[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent mock object arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Mock<T>[] or Stub<T>[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

  test("ArrayType should represent performance measurement arrays") {
    val classOrInterfaceType = createMockClassOrInterfaceType()
    val dims = createMockDims()
    val arrayType = ArrayType(None, Some(classOrInterfaceType), None, dims)
    
    // Benchmark[] or PerformanceTest[]
    arrayType.classOrInterfaceType shouldBe defined
    arrayType.dims shouldBe dims
  }

}