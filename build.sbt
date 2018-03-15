name := "DgMongoTest"

version := "1.0"

scalaVersion := "2.11.7"

val ScalatraVersion = "2.5.0"


resolvers += "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
  "org.mongodb.spark" %% "mongo-spark-connector" % "2.0.0",
  "org.apache.spark" %% "spark-core" % "2.0.0",
  "org.apache.spark" %% "spark-sql" % "2.0.0",
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.5",
  "org.json4s" %% "json4s-jackson" % "3.5.1",
  "org.json4s" %% "json4s-native" % "3.5.1",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016"

)


Seq(webSettings :_*)



    