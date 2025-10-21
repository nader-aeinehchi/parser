package org.naderica.parser.sourcecode.java20.standard.tool

object TestProcessorRunner extends App:
  val apiKey = sys.env.getOrElse("COPILOT_API_KEY", "")
  if apiKey.isEmpty then
    Logger.error("COPILOT_API_KEY not set. Export it in your environment and retry.")
    System.exit(1)
  
  val proc = TestProcessor(apiKey = apiKey)
  val successes = proc.processAll()
  Logger.info(s"Processed $successes classes")
