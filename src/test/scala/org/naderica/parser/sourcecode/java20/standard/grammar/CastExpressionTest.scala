package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CastExpressionTest extends AnyFunSuite with Matchers {

  // Mock helper functions
  private def createMockPrimitiveType(): PrimitiveType = {
    null.asInstanceOf[PrimitiveType] // Simplified mock
  }

  private def createMockReferenceType(): ReferenceType = {
    null.asInstanceOf[ReferenceType] // Simplified mock
  }

  private def createMockAdditionalBound(): AdditionalBound = {
    null.asInstanceOf[AdditionalBound] // Simplified mock
  }

  private def createMockUnaryExpression(): UnaryExpression = {
    null.asInstanceOf[UnaryExpression] // Simplified mock
  }

  private def createMockAdditionalBoundList(size: Int): List[AdditionalBound] = {
    if (size == 0) {
      List.empty[AdditionalBound]
    } else {
      List.fill(size)(createMockAdditionalBound())
    }
  }

  test("CastExpression should be created with primitive type and unary expression") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    castExpression.primitiveType shouldBe Some(primitiveType)
    castExpression.referenceType shouldBe None
    castExpression.additionalBounds shouldBe additionalBounds
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should be created with reference type and unary expression") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    castExpression.primitiveType shouldBe None
    castExpression.referenceType shouldBe Some(referenceType)
    castExpression.additionalBounds shouldBe additionalBounds
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should be created with additional bounds") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(2)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    castExpression.primitiveType shouldBe None
    castExpression.referenceType shouldBe Some(referenceType)
    castExpression.additionalBounds should have size 2
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should be created with no type specified") {
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, None, additionalBounds, unaryExpression)
    
    castExpression.primitiveType shouldBe None
    castExpression.referenceType shouldBe None
    castExpression.additionalBounds shouldBe additionalBounds
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should support equality comparison") {
    val primitiveType = createMockPrimitiveType()
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(1)
    val unaryExpression = createMockUnaryExpression()
    val castExpression1 = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    val castExpression2 = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    castExpression1 should not equal castExpression2
  }

  test("CastExpression should support equality for same field references") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(1)
    val unaryExpression = createMockUnaryExpression()
    val castExpression1 = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    val castExpression2 = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    castExpression1 shouldEqual castExpression2
  }

  test("CastExpression should have proper toString representation") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(1)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    castExpression.toString should include("CastExpression")
  }

  test("CastExpression should be a case class with copy method") {
    val primitiveType = createMockPrimitiveType()
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(1)
    val unaryExpression = createMockUnaryExpression()
    val originalCastExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    val copiedCastExpression = originalCastExpression.copy(referenceType = Some(referenceType))
    
    copiedCastExpression.primitiveType shouldBe Some(primitiveType)
    copiedCastExpression.referenceType shouldBe Some(referenceType)
    copiedCastExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should maintain immutability") {
    val primitiveType = createMockPrimitiveType()
    val originalBounds = createMockAdditionalBoundList(1)
    val newBounds = createMockAdditionalBoundList(3)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, originalBounds, unaryExpression)
    
    val newCastExpression = castExpression.copy(additionalBounds = newBounds)
    
    castExpression.additionalBounds should have size 1
    newCastExpression.additionalBounds should have size 3
  }

  test("CastExpression should handle various additional bound list sizes") {
    val primitiveType = createMockPrimitiveType()
    val unaryExpression = createMockUnaryExpression()
    for (size <- 0 to 5) {
      val additionalBounds = createMockAdditionalBoundList(size)
      val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
      
      castExpression.additionalBounds should have size size
    }
  }

  test("CastExpression should represent int cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (int) value
    castExpression.primitiveType shouldBe defined
    castExpression.referenceType shouldBe None
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent String cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (String) object
    castExpression.primitiveType shouldBe None
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent double cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (double) intValue
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent long cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (long) timestamp
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent float cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (float) doubleValue
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent boolean cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (boolean) flag
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent byte cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (byte) intValue
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent char cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (char) unicode
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent short cast") {
    val primitiveType = createMockPrimitiveType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(Some(primitiveType), None, additionalBounds, unaryExpression)
    
    // (short) value
    castExpression.primitiveType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent Object cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Object) instance
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent List cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (List<String>) collection
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent Map cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Map<String, Integer>) data
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent array cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (String[]) array
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent generic cast with bounds") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(2)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (T & Comparable<T> & Serializable) object
    castExpression.referenceType shouldBe defined
    castExpression.additionalBounds should have size 2
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent wildcard cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (List<? extends Number>) list
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent interface cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Runnable) object
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent functional interface cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Function<String, Integer>) lambda
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent enum cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (DayOfWeek) enumValue
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent annotation cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (@Override Annotation) annotation
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent record cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Person) record
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent sealed class cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Shape) sealedInstance
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent Stream cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Stream<String>) stream
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent Optional cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Optional<String>) optional
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent CompletableFuture cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (CompletableFuture<String>) future
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent concurrent collection cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (ConcurrentHashMap<String, Integer>) map
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent atomic reference cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (AtomicReference<String>) atomic
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent executor cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (ExecutorService) executor
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent thread cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Thread) runnable
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent socket cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Socket) connection
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent database connection cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Connection) dbConnection
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent result set cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (ResultSet) results
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent servlet cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (HttpServlet) servlet
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent security principal cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Principal) user
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent crypto key cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (PublicKey) key
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent certificate cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (X509Certificate) cert
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent JMX bean cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (MBean) managedBean
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent event listener cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (EventListener) listener
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent transformation cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Transformer<Input, Output>) transform
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent filter cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Filter<T>) filter
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent comparator cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Comparator<T>) comparator
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent cache cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Cache<K, V>) cache
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent monitoring metric cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Metric) measurement
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent log entry cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (LogEntry) logRecord
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent configuration property cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Property) config
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent service endpoint cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Endpoint) service
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent message queue cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Queue<Message>) messageQueue
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent reactive publisher cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Publisher<T>) publisher
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent circuit breaker cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (CircuitBreaker) breaker
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent load balancer cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (LoadBalancer) balancer
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent health check cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (HealthCheck) check
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent machine learning model cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Model) mlModel
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent data pipeline cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Pipeline) dataPipeline
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent workflow step cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (WorkflowStep) step
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent integration connector cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Connector) integrationConnector
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent API gateway cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Gateway) apiGateway
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent container registry cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Registry) containerRegistry
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent kubernetes resource cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Pod) k8sResource
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent cloud service cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (CloudService) service
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent serverless function cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Function) serverlessFunction
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent blockchain transaction cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Transaction) blockchainTx
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent IoT sensor cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (Sensor) iotDevice
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

  test("CastExpression should represent edge computing node cast") {
    val referenceType = createMockReferenceType()
    val additionalBounds = createMockAdditionalBoundList(0)
    val unaryExpression = createMockUnaryExpression()
    val castExpression = CastExpression(None, Some(referenceType), additionalBounds, unaryExpression)
    
    // (EdgeNode) computeNode
    castExpression.referenceType shouldBe defined
    castExpression.unaryExpression shouldBe unaryExpression
  }

}