plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")

    compileOnly(project(":nms:common"))
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.reobfJar {
    enabled = false
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "paperweight-mappings-namespace" to "mojang"
        )
    }
}