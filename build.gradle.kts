@file:Suppress("VulnerableLibrariesLocal")

import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import xyz.jpenilla.runpaper.task.RunServer
import xyz.jpenilla.runtask.task.AbstractRun

plugins {
    id("java")
    alias(libs.plugins.run.server)
}

val versionIsBeta = (properties["version"] as String).toDefaultLowerCase().contains("beta")

allprojects {
    apply(plugin = "java")

    group = "me.adrigamer2950"
    version = properties["version"] as String +
            if (versionIsBeta)
                "-${getGitCommitHash()}"
            else ""

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "Sonatype Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
    }

    dependencies {
        compileOnly(rootProject.libs.jetbrains.annotations)
    }
}

tasks.named<Jar>("jar") {
    dependsOn(":plugin:shadowJar")

    doFirst {
        File(layout.buildDirectory.dir("libs").get().asFile, "${rootProject.name}-${version}.jar").delete()
    }

    doLast {
        project(":plugin").tasks.named<Jar>("shadowJar").get().archiveFile.get().asFile.copyTo(
            File(layout.buildDirectory.dir("libs").get().asFile, "${rootProject.name}-${version}.jar"),
            overwrite = true
        )
    }
}

fun getGitCommitHash(): String {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
        .redirectErrorStream(true)
        .start()

    return process.inputStream.bufferedReader().readText().trim()
}

tasks.named<RunServer>("runServer").configure {
    minecraftVersion("1.20.2")

    downloadPlugins {
        // ViaVersion
        hangar("viaversion", "5.3.2")

        // ViaBackwards
        hangar("viabackwards", "5.3.2")
    }
}

tasks.withType(AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs(
        // Debug
        "-Dadriapi.debug=true",

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