
plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.7"
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

    //compile("org.openjfx:javafx-base:11")
    //compile("org.openjfx:javafx-graphics:11")
//    testCompile("junit:junit:4.12")
}

// In this section you declare where to find the dependencies of your project
repositories {
    jcenter()
    mavenCentral()
}
