@file:Suppress("VulnerableLibrariesLocal")

plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(libs.paper.api)
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}