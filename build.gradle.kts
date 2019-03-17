
val version = "1.0.0"

plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.beryx.jlink") version "2.6.6"
//    id("com.github.johnrengelman.shadow") version "5.0.0"
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
}

dependencies {
//    testCompile("junit:junit:4.12")
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
