val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-deprecation", "-feature", "-encoding", "utf-8")
)

lazy val `pairs-client` = project.in(file("client")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings).
  settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.8.1"
    ),
    persistLauncher in Compile := true,
    persistLauncher in Test := false
  )

lazy val `pairs-server` = project.in(file("server")).
  settings(commonSettings).
  settings(Revolver.settings).
  settings(
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % "1.3.3",
      "io.spray" %% "spray-routing" % "1.3.3",
      "com.typesafe.akka" %% "akka-actor" % "2.3.9"
    )
  )
