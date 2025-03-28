@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.papermc.hangarpublishplugin.model.Platforms
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import xyz.jpenilla.runpaper.task.RunServer
import xyz.jpenilla.runtask.task.AbstractRun
import java.io.ByteArrayOutputStream

plugins {
    kotlin("jvm")
    id("java")
    id("maven-publish")
    alias(libs.plugins.minotaur)
    alias(libs.plugins.run.server)
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
}

val versionIsBeta = (properties["version"] as String).toDefaultLowerCase().contains("beta")

group = "me.adrigamer2950"
version = properties["version"] as String +
        if (versionIsBeta)
            "-${getGitCommitHash()}"
        else ""
val targetJavaVersion = (properties["java-version"] as String).toInt()

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "AlessioDP"
            url = uri("https://repo.alessiodp.com/releases")
        }
    }

    dependencies {
        compileOnly(rootProject.libs.jetbrains.annotations)
    }

    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":api"))

    implementation(project(":plugin"))
}

tasks.named<Jar>("jar") {
    enabled = false
}

fun getJarFile(): File? {
    val jarFile = File("./gh-assets").listFiles()?.firstOrNull { it.name.endsWith(".jar") }
    return jarFile
}

fun getGitCommitHash(): String {
    val byteOut = ByteArrayOutputStream()

    @Suppress("DEPRECATION")
    exec {
        commandLine = "git rev-parse --short HEAD".split(" ")
        standardOutput = byteOut
    }

    return String(byteOut.toByteArray()).trim()
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "adriapi"
    versionNumber = version as String
    versionName = rootProject.name + " " + version
    versionType = "release"
    uploadFile.set(getJarFile())
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
            "1.21.4"
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
                jar.set(getJarFile())

                val versions: List<String> = listOf("1.17-1.21.4")
                platformVersions.set(versions)
            }
        }
    }
}

tasks.named<RunServer>("runServer").configure {
    minecraftVersion("1.20.2")

    downloadPlugins {
        // ViaVersion
        hangar("ViaVersion", "5.2.0")
    }
}

tasks.withType(AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
    jvmArgs(
        // Hot Swap
        "-XX:+AllowEnhancedClassRedefinition",

        // Aikar Flags
        "--add-modules=jdk.incubator.vector", "-XX:+UseG1GC", "-XX:+ParallelRefProcEnabled",
        "-XX:MaxGCPauseMillis=200", "-XX:+UnlockExperimentalVMOptions", "-XX:+DisableExplicitGC",
        "-XX:+AlwaysPreTouch", "-XX:G1NewSizePercent=30", "-XX:G1MaxNewSizePercent=40",
        "-XX:G1HeapRegionSize=8M", "-XX:G1ReservePercent=20", "-XX:G1HeapWastePercent=5",
        "-XX:G1MixedGCCountTarget=4", "-XX:InitiatingHeapOccupancyPercent=15",
        "-XX:G1MixedGCLiveThresholdPercent=90", "-XX:G1RSetUpdatingPauseTimePercent=5",
        "-XX:SurvivorRatio=32", "-XX:+PerfDisableSharedMem", "-XX:MaxTenuringThreshold=1",
        "-Dusing.aikars.flags=https://mcflags.emc.gs", "-Daikars.new.flags=true"
    )

}

tasks.named<ShadowJar>("shadowJar") {
    dependsOn(":api:shadowJar")

    archiveClassifier.set("")
    archiveVersion.set(version as String)

    dependencies {
        relocate("net.byteflux.libby", "me.adrigamer2950.adriapi.lib.libby")

        relocate("org.fusesource.jansi", "me.adrigamer2950.adriapi.lib.jansi")

        relocate("kotlin", "me.adrigamer2950.adriapi.lib.kotlin")
    }
}

tasks.named("build") {
    finalizedBy(tasks.named("shadowJar"))
}