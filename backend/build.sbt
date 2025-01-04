ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.0"

ThisBuild / name := "calc-hash-site-backend"

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      // zio/http
      "dev.zio" %% "zio-http" % "3.0.0",

      // hash
      "commons-codec" % "commons-codec" % "1.15"
    )
)
