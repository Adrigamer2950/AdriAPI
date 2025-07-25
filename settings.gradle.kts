pluginManagement {
    plugins {
        kotlin("jvm") version "2.2.0"
        id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    }
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    @Suppress("SpellCheckingInspection")
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "AdriAPI"

include("core", "folia", "plugin", "nms")

listOf(
    "common",
    "1_17_R1",
    "1_18_R1",
    "1_18_R2",
    "1_19_R1",
    "1_19_R2",
    "1_19_R3",
    "1_20_R1",
    "1_20_R2",
    "1_20_R3",
    "1_20_R4",
    "1_21_R1",
    "1_21_R2",
    "1_21_R3",
    "1_21_R4",
).forEach {
    include(":nms:$it")
}
