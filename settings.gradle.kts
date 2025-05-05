pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20"
        id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    }
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    @Suppress("SpellCheckingInspection")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "AdriAPI"

include("core", "folia", "plugin", ":nms:common")

listOf("1_17_R1", "1_18_R1", "1_18_R2", "1_19_R1", "1_19_R2", "1_19_R3").forEach {
    include(":nms:$it")
}
