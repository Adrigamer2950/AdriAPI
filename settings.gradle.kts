pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20"
        kotlin("kapt") version "2.1.20"
    }
}

plugins {
    @Suppress("SpellCheckingInspection")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "AdriAPI"

include("api", "api:folia", "plugin")
