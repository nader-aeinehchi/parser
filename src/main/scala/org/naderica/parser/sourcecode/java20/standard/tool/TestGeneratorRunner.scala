package org.naderica.parser.sourcecode.java20.standard.tool

import sttp.client4.upicklejson.default

case class Configuration(
    mainPath: String,
    testPath: String,
    batchSize: Int = 1,
    maxRetries: Int = 3,
    timeoutSeconds: Int = 300,
    dryRun: Boolean = true
)
object TestGeneratorRunner {

  def main(argv: Array[String]): Unit = {
    val config = getConfigFromArgs(argv)

    val services = Services(
      logger = Logger,
      fileFinder = FileFinder,
      apiClient = ApiClient,
      shellRunner = ShellRunner
    )

    val dryRun = argv.exists(arg => arg == "-n" || arg == "--dry-run")
    if (dryRun) {
      runDryRun(config.mainPath, config.testPath)
      return
    }

    generateTests(services, config)
  }

  private def generateTests(services: Services, config: Configuration): Unit = {

    val apiKey = sys.env.getOrElse("COPILOT_API_KEY", "")
    if (apiKey.isEmpty) {
      Logger.error(
        "COPILOT_API_KEY not set. Export it in your environment and retry."
      )
      System.exit(1)
    }

    /* It is important to pass parameters explicitly to TestGenerator such that
     * each run is independent
     */
    val proc = TestGenerator(
      services = services,
      mainPath = config.mainPath,
      testPath = config.testPath,
      apiKey = apiKey,
      batchSize = config.batchSize,
      maxRetries = config.maxRetries,
      timeoutSeconds = config.timeoutSeconds
    )

    val successes = proc.processAll()
    Logger.info(s"Processed $successes classes")
  }

  private def runDryRun(mainPath: String, testPath: String): Unit = {
    Logger.info(
      "Dry-run mode: listing untested classes (no API calls, no compilation)"
    )
    val untested = FileFinder.untestedClasses(mainPath, testPath)
    if (untested.isEmpty) {
      Logger.info("No untested classes found.")
    } else {
      Logger.info(s"Found ${untested.size} untested classes:")
      untested.foreach(uc => println(uc))
    }
  }

  private def getConfigFromArgs(argv: Array[String]): Configuration = {
    val defaultMainPath =
      "src/main/scala/org/naderica/parser/sourcecode/java20/standard/grammar"
    val defaultTestPath =
      "src/test/scala/org/naderica/parser/sourcecode/java20/standard/grammar"
    val defaultBatchSize = 1
    val defaultMaxRetries = 3
    val defaultTimeoutSeconds = 300

    val mainPath = argv
      .collectFirst {
        case arg if arg.startsWith("-m") => arg.stripPrefix("-m")
      }
      .getOrElse(defaultMainPath)
    val testPath = argv
      .collectFirst {
        case arg if arg.startsWith("-t") => arg.stripPrefix("-t")
      }
      .getOrElse(defaultTestPath)

    val batchSize = argv
      .collectFirst {
        case arg if arg.startsWith("-b") => arg.stripPrefix("-b").toInt
      }
      .getOrElse(defaultBatchSize)
    val maxRetries = argv
      .collectFirst {
        case arg if arg.startsWith("-r") => arg.stripPrefix("-r").toInt
      }
      .getOrElse(defaultMaxRetries)
    val timeoutSeconds = argv
      .collectFirst {
        case arg if arg.startsWith("-t") => arg.stripPrefix("-t").toInt
      }
      .getOrElse(defaultTimeoutSeconds)

    val dryRun = argv.exists(arg => arg == "-n" || arg == "--dry-run")

    Configuration(
      mainPath = mainPath,
      testPath = testPath,
      batchSize = 1,
      maxRetries = 3,
      timeoutSeconds = 300,
      dryRun = dryRun
    )
  }
}
