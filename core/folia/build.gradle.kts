plugins {
    kotlin("jvm")
}

group = "me.adrigamer2950.adriapi"

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly(libs.folia.api)
}

sourceSets.main {
    kotlin.srcDirs("src/main/kotlin")
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}