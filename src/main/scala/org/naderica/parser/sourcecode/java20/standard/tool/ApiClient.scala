package org.naderica.parser.sourcecode.java20.standard.tool

import scala.util.Try
import java.nio.file.{Files, Paths}

object ApiClient:
  def generateTest(prompt: String, apiKey: String): Try[String] =
    // Use curl via shell for simplicity; this keeps dependencies minimal
    val payload = s"{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"system\",\"content\":\"You are an expert Scala developer.\"},{\"role\":\"user\",\"content\":${escapeJson(prompt)}}],\"max_tokens\":2000,\"temperature\":0.2}"
    val cmd = Seq("curl", "-s", "-X", "POST", "https://api.openai.com/v1/chat/completions", "-H", s"Authorization: Bearer $apiKey", "-H", "Content-Type: application/json", "-d", payload)
    ShellRunner.runCmd(cmd).map(_._2).map { resp =>
      // crude extraction of content
      val marker = "\"content\":\""
      val idx = resp.indexOf(marker)
      if idx >= 0 then
        val start = idx + marker.length
        val end = resp.indexOf("\"", start)
        if end > start then resp.substring(start, end).replaceAll("\\\\n", "\n") else resp
      else resp
    }

  private def escapeJson(s: String): String =
    '"' + s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + '"'

