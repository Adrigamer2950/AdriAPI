plugins {
    @Suppress("SpellCheckingInspection")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "AdriAPI"

include("folia", "plugin", "api")
