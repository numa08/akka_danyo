name := """akka-dejiko"""

version := "1.0"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3"

scalaVersion := "2.10.4"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.6"

libraryDependencies ++= Seq(
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
)
