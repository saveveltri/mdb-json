name := """mdb-json"""

version := "1.0"

scalaVersion := "2.11.1"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
"org.scalatest" %% "scalatest" % "2.1.6" % "test",
"com.healthmarketscience.jackcess" % "jackcess" % "2.0.7",
"com.typesafe.play" % "play-json_2.11" % "2.3.7"
)
// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3"

