@file:Suppress("VulnerableLibrariesLocal")

import xyz.jpenilla.runtask.task.AbstractRun
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import xyz.jpenilla.runpaper.task.RunServer
import io.papermc.hangarpublishplugin.model.Platforms
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import java.io.ByteArrayOutputStream

plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.run.server)
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
}

val versionIsBeta = (properties["version"] as String).toDefaultLowerCase().contains("beta")

val group = "me.adrigamer2950"
val version = properties["version"] as String +
        if (versionIsBeta)
            "-${getGitCommitHash()}"
        else ""

if (project.hasProperty("NEXUS_USERNAME") && project.hasProperty("NEXUS_PASSWORD")) {
    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        repositories {
            maven {
                val baseUrl = "https://repo.devadri.es/repository/"

                url = uri(
                    baseUrl + if (versionIsBeta) "dev" else "releases"
                )
                credentials {
                    username = project.property("NEXUS_USERNAME") as String
                    password = project.property("NEXUS_PASSWORD") as String
                }
            }
        }
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = group
                artifactId = rootProject.name
                version = this.version

                from(components["java"])
                pom {
                    name = rootProject.name
                    description.set(project.properties["description"] as String)
                    url = "https://github.com/Adrigamer2950/AdriAPI"

                    licenses {
                        license {
                            name = "GPL-3.0"
                            url = "https://www.gnu.org/licenses/gpl-3.0.html"
                        }
                    }

                    developers {
                        developer {
                            id = "Adrigamer2950"
                            name = "Adri"
                        }
                    }

                    scm {
                        url = "https://github.com/Adrigamer2950/AdriAPI"
                    }

                    issueManagement {
                        system = "GitHub"
                        url = "https://github.com/Adrigamer2950/AdriAPI/issues"
                    }
                }
            }
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }
}

repositories {
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "AlessioDP"
        url = uri("https://repo.alessiodp.com/releases")
    }
}

dependencies {
    // JetBrains Annotations
    compileOnly(libs.jetbrains.annotations)

    // Paper API
    compileOnly(libs.paper.api)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Ansi
    compileOnly(libs.jansi)
    bukkitLibrary(libs.jansi)

    // Libby
    implementation(libs.libby)

    // Folia module
    implementation(project(":folia"))
}

val targetJavaVersion = 17

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release = targetJavaVersion
        options.encoding = "UTF-8"
    }
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.named<Jar>("jar") {
    enabled = false
}

bukkit {
    name = "AdriAPI"
    main = "me.adrigamer2950.adriapi.AdriAPI"
    apiVersion = "1.17"
    author = properties["author"] as String?
    description = properties["description"] as String?
    website = "https://github.com/Adrigamer2950/AdriAPI"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    foliaSupported = true

    commands {
        register("adriapi") {
            description = "Main command"
            usage = "/adriapi"
        }
    }
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
    versionNumber = version
    versionName = rootProject.name + " " + version
    versionType = "release"
    uploadFile.set(getJarFile())
    gameVersions.set(
        listOf(
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

                val versions: List<String> = listOf("1.18-1.21.4")
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
    archiveClassifier.set("")
    archiveVersion.set(version)

    dependencies {
        relocate("net.byteflux.libby", "me.adrigamer2950.adriapi.lib.libby")

        //relocate("com.iridium.iridiumcolorapi", "me.adrigamer2950.adriapi.libs.iridiumcolorapi")
    }
}

tasks.named("build") {
    finalizedBy(tasks.named("shadowJar"))
}