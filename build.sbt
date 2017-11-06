val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq("-deprecation", "-feature", "-encoding", "utf-8")
)

lazy val `pairs-client` = project.in(file("client")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings).
  settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.3"
    ),
    scalaJSUseMainModuleInitializer := true
  )

lazy val `pairs-server` = project.in(file("server")).
  settings(commonSettings).
  settings(Revolver.settings).
  settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
      "com.typesafe.akka" %% "akka-http" % "10.0.10",
      "com.typesafe.akka" %% "akka-actor" % "2.5.4",
      "com.typesafe.akka" %% "akka-stream" % "2.5.4"
    )
  )
