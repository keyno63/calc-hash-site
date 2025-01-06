inThisBuild(
    List(
        version := "0.1.0-SNAPSHOT",
        scalaVersion := "3.6.0",
        name := "calc-hash-site-backend"
    )
)

lazy val root = (project in file("."))
  .settings(
    assembly / mainClass := Some("tokyo.name.maigo.calchash.backend.AppServer"),
    assembly / assemblyJarName := "calc-hash-site-backend.jar",
    libraryDependencies ++= Seq(
      // zio/http
      "dev.zio" %% "zio-http" % "3.0.1",
    )
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}

run / fork := true
