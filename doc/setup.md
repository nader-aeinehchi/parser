# Setup the environment
Firstly, let us see how the environment should be set up.

We are using SBT as the build system.  Follow these steps:

1. Create the folder `project`
2. Create the file `project/build.properties` with the following content:

```
sbt.version=1.10.7
```

3. Create the file `project/sbt-antlr4` with the following content:

```
addSbtPlugin("com.simplytyped" % "sbt-antlr4" % "0.8.3")
```

4. Create a `build.sbt`file like below. Note the lines:

```
"org.antlr" % "antlr4-runtime" % "4.13.2"
```
and 
```
.enablePlugins(Antlr4Plugin)
```

Here is a full example:
```
lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "org.naderica",
        scalaVersion := "3.6.3"
        // name := "parser"
      )
    ),
    name := "parser",
    // Compile / compileOrder := CompileOrder.Mixed,
    resolvers ++= Seq(
      Resolver.mavenLocal, // Looks for dependencies in your local Maven repository (~/.m2/repository)
      "Maven Central" at "https://repo1.maven.org/maven2/" // The primary public Maven repository
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
        "org.antlr" % "antlr4-runtime" % "4.13.2"
      )
    },
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
  )
  .enablePlugins(Antlr4Plugin)
```

5. Download lexer and grammar files for your choice of programming language (.g4).  Here, I have downloaded for Java: `Java20Lexer.g4` and `Java20Parser.g4`

Create a folder called `src/main/antlr4`.  You may copy the lexer and parser files directly in this folder. But I recommend you to create a package structure of your choice, copy the lexer and parser filer into the package and the then modify the header of lexer and parser files:

```
mkdir -p src/main/src/main/antlr4/org/naderica/parser/sourcecode/ast/java/standard
```

and then add the following "header" line to both the lexer and the parser files:
```
lexer grammar Java20Lexer;

@header {
package org.naderica.parser.sourcecode.ast.java.standard;
}
```

Now, you can run `sbt compile` to generate both source and class files for the lexer and the parser.

6. Having followed the steps above, you can import the auto-generated lexer and parser in your code with:

lexer grammar Java20Lexer;

```
import org.naderica.parser.sourcecode.ast.java.standard.*;
```

or more specifically like:

```
import org.naderica.parser.sourcecode.ast.java.standard.Java20Parser;
import org.naderica.parser.sourcecode.ast.java.standard.Java20ParserBaseVisitor;
```

