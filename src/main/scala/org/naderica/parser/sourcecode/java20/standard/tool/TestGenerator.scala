package org.naderica.parser.sourcecode.java20.standard.tool

import scala.util.{Try, Success, Failure}
import java.nio.file.{Files, Paths}

class TestGenerator(
    val services: Services,
    val mainPath: String,
    val testPath: String,
    val apiKey: String,
    val batchSize: Int = 5,
    val maxRetries: Int = 3,
    val timeoutSeconds: Int = 300
) {

  def processAll(): Int = {
    services.logger.info("Starting fully autonomous test generation process...")

    val untested = discoverUntested(services, mainPath, testPath)
    if (untested.isEmpty) {
      services.logger.info("No untested classes found. All classes have tests!")
      return 0
    }

    services.logger.info(s"Found ${untested.size} classes without tests")
    services.logger.info("Listing untested classes:")
    untested.foreach(uc => println(uc))

    val groups = untested.grouped(batchSize).toSeq

    var totalSuccess: Int = 0
    val failedBuf = scala.collection.mutable.ListBuffer.empty[String]

    for ((group, idx) <- groups.zipWithIndex) {
      services.logger.info(
        s"Processing batch ${idx + 1}/${groups.size}: ${group.size} classes"
      )
      val (succeeded, failedInGroup) = processGroup(
        group,
        services,
        mainPath,
        testPath,
        apiKey,
        maxRetries,
        timeoutSeconds
      )
      totalSuccess += succeeded
      failedBuf ++= failedInGroup
    }

    val totalFailed: Vector[String] = failedBuf.toVector

    services.logger.info(
      s"Completed processing. Success: $totalSuccess, Failed: ${totalFailed.size}"
    )
    if (totalFailed.nonEmpty)
      services.logger.warn(s"Failed classes: ${totalFailed.mkString(", ")}")
    totalSuccess
  }

  private def discoverUntested(
      services: Services,
      mainPath: String,
      testPath: String
  ): Seq[String] = {
    services.fileFinder.untestedClasses(mainPath, testPath)
  }

// private def processGroup(
// 		group: Seq[String],
// 		services: Services,
// 		mainPath: String,
// 		testPath: String,
// 		apiKey: String,
// 		maxRetries: Int,
// 		timeoutSeconds: Int
// ): (Int, Vector[String]) = {
// 	group.foldLeft((0, Vector.empty[String])) { case ((suc, fail), className) =>
// 		if (
// 			processClass(
// 				className,
// 				services,
// 				mainPath,
// 				testPath,
// 				apiKey,
// 				maxRetries,
// 				timeoutSeconds
// 			)
// 		) (suc + 1, fail)
// 		else (suc, fail :+ className)
// 	}
// }

  private def processGroup(
      group: Seq[String],
      services: Services,
      mainPath: String,
      testPath: String,
      apiKey: String,
      maxRetries: Int,
      timeoutSeconds: Int
  ): (Int, Vector[String]) = {
    var successCount = 0
    val failedBuf = scala.collection.mutable.ListBuffer.empty[String]
    for (className <- group) {
      val singleTestGenerator = new SingleTestGenerator(
        className,
        services,
        mainPath,
        testPath,
        apiKey,
        maxRetries,
        timeoutSeconds
      )

      val ok = singleTestGenerator.generate()
      if (ok) successCount += 1
      else failedBuf += className
    }
    (successCount, failedBuf.toVector)
  }

}

// val (totalSuccess, totalFailed) =
//   groups.zipWithIndex.foldLeft((0, Vector.empty[String])) {
//     case ((accSuccess, accFailed), (group, idx)) =>
//       services.logger.info(
//         s"Processing batch ${idx + 1}/${groups.size}: ${group.size} classes"
//       )
//       val (succeeded, failedInGroup) = processGroup(
//         group,
//         services,
//         mainPath,
//         testPath,
//         apiKey,
//         maxRetries,
//         timeoutSeconds
//       )
//       (accSuccess + succeeded, accFailed ++ failedInGroup)
//   }
