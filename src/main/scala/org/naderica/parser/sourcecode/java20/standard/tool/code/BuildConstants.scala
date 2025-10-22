package org.naderica.parser.sourcecode.java20.standard.tool.code

trait EnumNameHelper {
  // the raw enum identifier, e.g. "SOURCE_CODE" or "LOCAL"
  def rawName: String = this.toString

  // PascalCase (capitalize each part): "SOURCE_CODE" -> "SourceCode", "LOCAL" -> "Local"
  def toCamelCase: String =
    rawName
      .split('_')
      .map(part => part.toLowerCase.capitalize)
      .mkString

  // lowerCamelCase: "SOURCE_CODE" -> "sourceCode"
  def toLowerCamelCase: String = {
    val parts = rawName.split('_').map(_.toLowerCase)
    parts.headOption match {
      case Some(h) => h + parts.drop(1).map(_.capitalize).mkString
      case None    => ""
    }
  }

  // Readable spaced form: "SOURCE_CODE" -> "Source Code"
  def toReadable: String =
    rawName.split('_').map(_.toLowerCase.capitalize).mkString(" ")

  // legacy name method kept for compatibility: returns enum type name by default
  def name: String = this.getClass.getSimpleName.replace("$", "")
}

enum BuildEnvironment extends EnumNameHelper {
  case LOCAL
  case DEVELOPMENT
  case STAGING
  case PRODUCTION
  case TESTING
  case OTHER
}

enum BuildVariant extends EnumNameHelper {
  case DEBUG
  case RELEASE
  case SNAPSHOT
  case BETA
  case STABLE
  case EXPERIMENTAL
}

enum BuildMode extends EnumNameHelper {

  /** Builds a new code project from scratch.
    */
  case NEW

  /** Adds additional code to an existing code.
    */
  case INCREMENTAL

  /** Rebuilds the entire code.
    */
  case REBUILD

  /** Cleans up all the generated code.
    */
  case CLEAN
}

enum BuildArtificat extends EnumNameHelper {
  case APPLICATION
  case LIBRARY
  case COMPONENT
  case SERVICE
  case SOURCE_CODE
  case TEST_CODE
  case OTHER
}

enum BuildUnitOfWork extends EnumNameHelper {
  case PACKAGE
  case MODULE
  case CLASS
  case INTERFACE
  case TRAIT
  case ENUM
  case OBJECT
  case FUNCTION
  case FILE
  case METHOD
  case VARIABLE
  case TYPE
}

enum BuildTool extends EnumNameHelper {
  case MAVEN
  case GRADLE
  case SBT
  case ANT
  case MAKE
  case CMAKE
  case ARTIFICIAL_INTELLIGENCE
  case OTHER
}

enum BuildSystem extends EnumNameHelper {
  case JENKINS
  case TRAVIS_CI
  case CIRCLE_CI
  case GITHUB_ACTIONS
  case GITLAB_CI
  case OTHER
}

enum BuildOptimization extends EnumNameHelper {
  case SPEED
  case SIZE
  case DEBUGGING
  case BALANCED
  case OTHER
}

enum BuildDistribution extends EnumNameHelper {
  case INTERNAL
  case PUBLIC
  case PRIVATE
  case OPEN_SOURCE
  case ENTERPRISE
  case OTHER
}

enum BuildStatus extends EnumNameHelper {
  case SUCCESS
  case FAILURE
  case IN_PROGRESS
  case CANCELED
  case PENDING
  case OTHER
}

enum BuildLanguage(val fileExtension: String) extends EnumNameHelper {
  case JAVA extends BuildLanguage("java")
  case SCALA extends BuildLanguage("scala")
  case KOTLIN extends BuildLanguage("kotlin")
  case JAVA_SCRIPT extends BuildLanguage("js")
  case TYPE_SCRIPT extends BuildLanguage("ts")
  case PYTHON extends BuildLanguage("py")
  case RUBY extends BuildLanguage("rb")
  case C_SHARP extends BuildLanguage("cs")
  case C_PLUS_PLUS extends BuildLanguage("cpp")
  case OTHER extends BuildLanguage("txt")
}
