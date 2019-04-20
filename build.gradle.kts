
val version = "1.0.0"
val junitVersion = "5.4.2"

plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.beryx.jlink") version "2.6.6"
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    modules = listOf("javafx.graphics", "javafx.controls", "javafx.swing")
    version = "11.0.1"
}

application {
    mainClassName = "info.Explorer"
    applicationDefaultJvmArgs = listOf("-XX:+FlightRecorder", "-XX:StartFlightRecording=filename=myrecording.jfr", "-Xlint:deprecation")
}

dependencies {
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testCompile("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

// In this section you declare where to find the dependencies of your project
repositories {
    jcenter()
    mavenCentral()
}

jlink {
    addOptions("--compress", "2", "--no-header-files", "--no-man-pages")
     // TODO make variable
    launcher {
        name = "catrescue"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
