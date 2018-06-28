
name := """app"""

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion       = "2.5.3"
  val akkaHttpVersion   = "10.0.9"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "org.scalatest"     %% "scalatest" % "3.0.1" % "test"
  )
}
enablePlugins(DockerPlugin, AssemblyPlugin)

val baseImage = "openjdk:8"

docker <<= (docker dependsOn assembly)
buildOptions in docker := BuildOptions(cache = false, pullBaseImage = BuildOptions.Pull.Always)
dockerfile in docker := {
  val artifact = (assemblyOutputPath in assembly).value
  val baseInstallDir = "/akka-http"
  val artifactTargetPath = s"$baseInstallDir/${artifact.name}"

  new Dockerfile {
    from(baseImage)
    expose(8080)
    env("CONFIG_PATH", s"$baseInstallDir/default.conf")
    env("ARTIFACT_TARGET_PATH", artifactTargetPath)
    env("JAVA_OPTS", "-Xmx2g")
    copy(artifact, artifactTargetPath)
    copy(baseDirectory(_ / "scripts" / "entrypoint.sh").value, file("/entrypoint.sh"))
    entryPoint("/entrypoint.sh")
  }
}
imageNames in docker := Seq(
  ImageName(
    namespace = Some("demo"),
    repository = "akka-sample",
    tag = Some("latest")
  ),
  ImageName(
    namespace = Some("demo"),
    repository = "akka-sample",
    tag = Some(version.value)
  )
)
