@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.papermc.hangarpublishplugin.model.Platforms
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm")
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.hangar.publish)
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

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "adriapi"
    versionNumber = version as String
    versionName = "${rootProject.name} $version"
    versionType = "release"
    uploadFile.set(project(":plugin").tasks.getByName<org.gradle.jvm.tasks.Jar>("shadowJar").archiveFile)
    gameVersions.set(
        listOf(
            "1.17",
            "1.17.1",
            "1.18",
            "1.18.1",
            "1.18.2",
            "1.19",
            "1.19.1",
            "1.19.2",
            "1.19.3",
            "1.19.4",
            "1.20",
            "1.20.1",
            "1.20.2",
            "1.20.3",
            "1.20.4",
            "1.20.5",
            "1.20.6",
            "1.21",
            "1.21.1",
            "1.21.2",
            "1.21.3",
            "1.21.4",
            "1.21.5",
            "1.21.6"
        )
    )
    loaders.set(
        listOf(
            "folia",
            "paper",
            "purpur"
        )
    )
    syncBodyFrom = rootProject.file("README.md").readText()
}

hangarPublish {
    publications.register("plugin") {
        version.set(project.version as String)
        channel.set("Release")
        id.set("AdriAPI")
        apiKey.set(System.getenv("HANGAR_API_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                jar.set(project(":plugin").tasks.getByName<org.gradle.jvm.tasks.Jar>("shadowJar").archiveFile)

                val versions: List<String> = listOf("1.17-1.21.6")
                platformVersions.set(versions)
            }
        }
    }
}
