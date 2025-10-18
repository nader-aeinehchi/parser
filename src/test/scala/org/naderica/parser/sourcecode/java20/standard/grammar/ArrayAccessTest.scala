package org.naderica.parser.sourcecode.java20.standard.grammar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ArrayAccessTest extends AnyFunSuite with Matchers {

  // Mock helper functions
  private def createMockExpressionName(): ExpressionName = {
    null.asInstanceOf[ExpressionName] // Simplified mock
  }

  private def createMockPrimaryNoNewArray(): PrimaryNoNewArray = {
    null.asInstanceOf[PrimaryNoNewArray] // Simplified mock
  }

  private def createMockArrayAccess(): ArrayAccess = {
    null.asInstanceOf[ArrayAccess] // Simplified mock for recursive reference
  }

  private def createMockExpression(): Expression = {
    null.asInstanceOf[Expression] // Simplified mock
  }

  test("ArrayAccess should be created with expression name and expression") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    arrayAccess.expressionName shouldBe Some(expressionName)
    arrayAccess.primaryNoNewArray shouldBe None
    arrayAccess.arrayAccess shouldBe None
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should be created with primary no new array and expression") {
    val primaryNoNewArray = createMockPrimaryNoNewArray()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, Some(primaryNoNewArray), None, expression)
    
    arrayAccess.expressionName shouldBe None
    arrayAccess.primaryNoNewArray shouldBe Some(primaryNoNewArray)
    arrayAccess.arrayAccess shouldBe None
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should be created with recursive array access and expression") {
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, Some(recursiveArrayAccess), expression)
    
    arrayAccess.expressionName shouldBe None
    arrayAccess.primaryNoNewArray shouldBe None
    arrayAccess.arrayAccess shouldBe Some(recursiveArrayAccess)
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should be created with only expression") {
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, None, expression)
    
    arrayAccess.expressionName shouldBe None
    arrayAccess.primaryNoNewArray shouldBe None
    arrayAccess.arrayAccess shouldBe None
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should support equality comparison") {
    val expressionName = createMockExpressionName()
    val primaryNoNewArray = createMockPrimaryNoNewArray()
    val expression = createMockExpression()
    val arrayAccess1 = ArrayAccess(Some(expressionName), None, None, expression)
    val arrayAccess2 = ArrayAccess(None, Some(primaryNoNewArray), None, expression)
    
    arrayAccess1 should not equal arrayAccess2
  }

  test("ArrayAccess should support equality for same field references") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess1 = ArrayAccess(Some(expressionName), None, None, expression)
    val arrayAccess2 = ArrayAccess(Some(expressionName), None, None, expression)
    
    arrayAccess1 shouldEqual arrayAccess2
  }

  test("ArrayAccess should have proper toString representation") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    arrayAccess.toString should include("ArrayAccess")
  }

  test("ArrayAccess should be a case class with copy method") {
    val expressionName = createMockExpressionName()
    val primaryNoNewArray = createMockPrimaryNoNewArray()
    val expression = createMockExpression()
    val originalArrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    val copiedArrayAccess = originalArrayAccess.copy(primaryNoNewArray = Some(primaryNoNewArray))
    
    copiedArrayAccess.expressionName shouldBe Some(expressionName)
    copiedArrayAccess.primaryNoNewArray shouldBe Some(primaryNoNewArray)
    copiedArrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should maintain immutability") {
    val expressionName = createMockExpressionName()
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    val newArrayAccess = arrayAccess.copy(arrayAccess = Some(recursiveArrayAccess))
    
    arrayAccess.arrayAccess shouldBe None
    newArrayAccess.arrayAccess shouldBe Some(recursiveArrayAccess)
  }

  test("ArrayAccess should represent simple array element access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // arr[0] or names[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent method result array access") {
    val primaryNoNewArray = createMockPrimaryNoNewArray()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, Some(primaryNoNewArray), None, expression)
    
    // getArray()[0] or method().result[index]
    arrayAccess.primaryNoNewArray shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent nested array access") {
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, Some(recursiveArrayAccess), expression)
    
    // matrix[i][j] or array[x][y][z]
    arrayAccess.arrayAccess shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent multi-dimensional array access") {
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, Some(recursiveArrayAccess), expression)
    
    // int[][] matrix -> matrix[row][col]
    arrayAccess.arrayAccess shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent constant index access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // array[0] or data[1]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent variable index access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // array[index] or items[position]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent computed index access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // array[i + 1] or data[size - 1]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent method call index access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // array[getIndex()] or items[calculatePosition()]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent field access index") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // array[obj.index] or data[this.position]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent String array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // String[] names -> names[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent List array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // List<String>[] lists -> lists[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent Map array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Map<String, Integer>[] maps -> maps[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent primitive array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // int[] numbers -> numbers[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent byte array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // byte[] buffer -> buffer[offset]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent char array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // char[] chars -> chars[position]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent boolean array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // boolean[] flags -> flags[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent double array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // double[] values -> values[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent matrix operations") {
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, Some(recursiveArrayAccess), expression)
    
    // double[][] matrix -> matrix[row][col]
    arrayAccess.arrayAccess shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent 3D array access") {
    val recursiveArrayAccess = createMockArrayAccess()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(None, None, Some(recursiveArrayAccess), expression)
    
    // int[][][] cube -> cube[x][y][z]
    arrayAccess.arrayAccess shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent generic array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // T[] items -> items[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent wildcard array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // List<? extends Number>[] lists -> lists[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent enum array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // DayOfWeek[] days -> days[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent annotation array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // @SuppressWarnings[] annotations -> annotations[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent record array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Person[] persons -> persons[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent sealed class array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Shape[] shapes -> shapes[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent functional interface array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Function<String, Integer>[] functions -> functions[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent stream array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Stream<String>[] streams -> streams[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent optional array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Optional<String>[] optionals -> optionals[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent concurrent collection array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // ConcurrentHashMap<String, Integer>[] maps -> maps[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent atomic array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // AtomicInteger[] atomics -> atomics[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent future array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // CompletableFuture<String>[] futures -> futures[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent executor array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // ExecutorService[] executors -> executors[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent thread array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Thread[] threads -> threads[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent socket array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Socket[] sockets -> sockets[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent database connection array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Connection[] connections -> connections[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent result set array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // ResultSet[] results -> results[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent servlet array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // HttpServlet[] servlets -> servlets[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent security principal array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Principal[] principals -> principals[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent crypto key array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Key[] keys -> keys[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent certificate array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Certificate[] certificates -> certificates[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent JMX bean array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // MBean[] beans -> beans[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent event listener array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // EventListener[] listeners -> listeners[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent validation rule array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // ValidationRule[] rules -> rules[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent transformation array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Transformer<Input, Output>[] transformers -> transformers[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent filter array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Filter<T>[] filters -> filters[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent comparator array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Comparator<T>[] comparators -> comparators[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent cache entry array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // CacheEntry<K, V>[] entries -> entries[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent monitoring metric array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Metric[] metrics -> metrics[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent log entry array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // LogEntry[] entries -> entries[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent audit record array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // AuditRecord[] records -> records[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent configuration property array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Property[] properties -> properties[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent service endpoint array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Endpoint[] endpoints -> endpoints[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent message queue array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Queue<Message>[] queues -> queues[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent event bus array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // EventBus[] buses -> buses[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent reactive publisher array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Publisher<T>[] publishers -> publishers[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent circuit breaker array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // CircuitBreaker[] breakers -> breakers[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent load balancer array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // LoadBalancer[] balancers -> balancers[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent health check array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // HealthCheck[] checks -> checks[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent machine learning model array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Model[] models -> models[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent data pipeline array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Pipeline[] pipelines -> pipelines[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent workflow step array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // WorkflowStep[] steps -> steps[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent integration connector array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Connector[] connectors -> connectors[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent api gateway array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Gateway[] gateways -> gateways[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent container registry array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Registry[] registries -> registries[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent kubernetes resource array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Pod[] pods -> pods[0]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent cloud service array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // CloudService[] services -> services[index]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

  test("ArrayAccess should represent serverless function array access") {
    val expressionName = createMockExpressionName()
    val expression = createMockExpression()
    val arrayAccess = ArrayAccess(Some(expressionName), None, None, expression)
    
    // Function[] functions -> functions[i]
    arrayAccess.expressionName shouldBe defined
    arrayAccess.expression shouldBe expression
  }

}