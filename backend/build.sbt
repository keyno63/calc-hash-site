ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.0"

ThisBuild / name := "calc-hash-site-backend"

lazy val root = (project in file("."))
  .settings(
    assembly / mainClass := Some("tokyo.name.maigo.calchash.backend.GreetingServer"),
    libraryDependencies ++= Seq(
      // zio/http
      "dev.zio" %% "zio-http" % "3.0.1",

      // hash
      "commons-codec" % "commons-codec" % "1.17.1"
    )
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}
