lazy val root = (project in file("."))
  .enablePlugins(Antlr4Gen)
  .settings(
    inThisBuild(
      List(
        organization := "org.naderica",
        scalaVersion := "3.6.3"
      )
    ),
    name := "parser",
    resolvers ++= Seq(
      Resolver.mavenLocal,
      "Maven Central" at "https://repo1.maven.org/maven2/"
    ),
    libraryDependencies ++= {
      val junitV = "4.13.2"
      val junitInterfaceV = "0.11"
      Seq(
        "org.scalameta" %% "scalameta" % "4.13.7",
        "com.typesafe.play" %% "play-json" % "2.10.1",
        "junit" % "junit" % junitV % Test,
        "com.novocode" % "junit-interface" % junitInterfaceV % Test,
        "org.scalatest" %% "scalatest" % "latest.integration" % Test,
        "org.scalameta" %% "munit" % "latest.integration" % Test,
        "org.json" % "json" % "latest.integration",
        "org.scala-lang" %% "toolkit" % "0.7.0",
        "org.antlr" % "antlr4-runtime" % "4.13.2",
        "org.antlr" % "antlr4" % "4.13.2"
      )
    },
    Compile / unmanagedSourceDirectories ++= {
      val managed = (Compile / sourceManaged).value / "antlr4"
      Seq(managed)
    },
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
  )