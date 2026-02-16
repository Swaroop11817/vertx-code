import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.gradleup.shadow") version "9.2.2"
}

group = "com.swaroop.udemy"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "5.0.7"
val junitJupiterVersion = "5.9.1"
val jacksonVersion = "2.18.2"

val mainVerticleName = "com.swaroop.udemy.vertx_starter.MainVerticle"
val launcherClassName = "io.vertx.launcher.application.VertxApplication"
//val launcherClassName = "io.vertx.core.Launcher"

application {
  mainClass.set(launcherClassName)
}




dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-core")
  implementation("io.vertx:vertx-launcher-application")
  implementation("org.apache.logging.log4j:log4j-api")
  implementation("org.apache.logging.log4j:log4j-core")
  implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.1")
  implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Class" to launcherClassName,"Main-Verticle" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf(mainVerticleName)
}

//tasks.withType<JavaExec> {
//  // Use a system property instead of arguments for consistency with the IDE
//  systemProperty("vertx.main-verticle", mainVerticleName)
//}
