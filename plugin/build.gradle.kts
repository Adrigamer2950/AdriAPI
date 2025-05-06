@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm")
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly(libs.paper.api)

    implementation(project(":core"))
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

bukkit {
    name = "AdriAPI"
    main = "me.adrigamer2950.adriapi.plugin.AdriAPI"
    apiVersion = "1.17"
    author = properties["author"] as String?
    description = properties["description"] as String?
    website = "https://github.com/Adrigamer2950/AdriAPI"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    foliaSupported = true
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "paperweight-mappings-namespace" to "mojang"
        )
    }

    enabled = false
}

tasks.named<ShadowJar>("shadowJar") {
    dependsOn(":core:shadowJar")

    archiveClassifier.set("")
    archiveVersion.set(version as String)

    dependencies {
        relocate("com.alessiodp.libby", "me.adrigamer2950.adriapi.lib.libby")

        relocate("kotlin", "me.adrigamer2950.adriapi.lib.kotlin")

        exclude("com/cryptomorin/xseries/**")
    }
}
