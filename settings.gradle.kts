pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20"
    }
}

plugins {
    @Suppress("SpellCheckingInspection")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "AdriAPI"

include("core", "core:folia", "plugin")
