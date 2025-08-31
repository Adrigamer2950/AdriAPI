@file:Suppress("VulnerableLibrariesLocal")

import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
    id("java")
}

val versionIsBeta = (properties["version"] as String).toDefaultLowerCase().contains("beta")

allprojects {
    apply(plugin = "java")

    group = "me.devadri"
    version = properties["version"] as String +
            if (versionIsBeta)
                "-${getGitCommitHash()}"
            else ""

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "Sonatype Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
    }

    dependencies {
        compileOnly(rootProject.libs.jetbrains.annotations)
    }
}

tasks.named<Jar>("jar") {
    enabled = false
}

fun getGitCommitHash(): String {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
        .redirectErrorStream(true)
        .start()

    return process.inputStream.bufferedReader().readText().trim()
}