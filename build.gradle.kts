plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.0"
    id("org.jetbrains.intellij.platform") version "2.18.1"
}

group = "com.example.sendcoderef"
version = "0.1.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea("2025.3.6")
        bundledPlugin("org.jetbrains.plugins.terminal")
    }
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

intellijPlatform {
    pluginConfiguration {
        name = "Send Code Reference to Terminal"
        version = project.version.toString()
        ideaVersion {
            sinceBuild = "253"
        }
    }
}
