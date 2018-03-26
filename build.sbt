name := "proveedores"
 
version := "1.0" 
      
lazy val `proveedores` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  evolutions,
  guice,
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.lightbend.akka" %% "akka-stream-alpakka-sqs" % "0.17"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

playEbeanDebugLevel := 4
