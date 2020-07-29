name := "akkademy-db"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "test",
  "org.projectlombok" % "lombok" % "1.18.12" % "compile",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.10" % "test"
)

//正式发布时排除配置文件。如果作为客户端库，实际上只需要message&exception package
mappings in (Compile, packageBin) ~= { _.filterNot { case (_, name) =>
 Seq("application.conf").contains(name)
}}
