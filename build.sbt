import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.8",
  libraryDependencies += scalaTest % Test,
  libraryDependencies += "net.codingwell" %% "scala-guice" % "5.0.2",
  libraryDependencies += "org.mockito" % "mockito-core" % "4.4.0" % "test"
  )

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(
    presentation,
    infrastructure,
    application,
    domainService,
    domainModel,
  )
  .dependsOn(
    presentation,
    infrastructure,
    application,
    domainService,
    domainModel
  )

lazy val presentation = (project in file("presentation"))
  .settings(commonSettings)
  .dependsOn(application)

lazy val infrastructure = (project in file("infrastructure"))
  .settings(commonSettings)
  .dependsOn(domainModel)

lazy val application = (project in file("application"))
  .settings(commonSettings)
  .dependsOn(domainService)
  .dependsOn(domainModel % "test->test;compile->compile")

lazy val domainService = (project in file("domainService"))
  .settings(commonSettings)
  .dependsOn(domainModel % "test->test;compile->compile")

lazy val domainModel = (project in file("domainModel"))
  .settings(commonSettings)

