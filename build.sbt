name := "es-geography-data"
version := "0.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "uk.gov.nationalarchives" % "csv-validator-core" % "1.1.5",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

dependencyOverrides += "com.gilt" %% "gfc-semver" % "0.0.5"
