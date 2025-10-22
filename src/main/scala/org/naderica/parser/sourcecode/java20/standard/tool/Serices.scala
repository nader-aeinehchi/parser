package org.naderica.parser.sourcecode.java20.standard.tool

/** Dependency bundle to avoid direct usage of global singletons inside methods.
  * Pass the required collaborators explicitly to each method.
  */
case class Services(
  logger: Logger.type,
  fileFinder: FileFinder.type,
  apiClient: ApiClient.type,
  shellRunner: ShellRunner.type
)
