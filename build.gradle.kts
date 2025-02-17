//file:noinspection GroovyAssignabilityCheck
//file:noinspection GroovyAccessibility
import xyz.jpenilla.runtask.task.AbstractRun

plugins {
    id 'java'
    id "maven-publish"
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.run.server)
    alias(libs.plugins.shadow)
}

group = 'me.adrigamer2950'
version = properties.get("version") + (
        project.hasProperty("BUILD_NUMBER") ?
                (project.property("BUILD_NUMBER") == "0" ? "" : ("-b" + project.property("BUILD_NUMBER")))
                : ""
)
author = properties.get("author")

if (project.hasProperty("NEXUS_USERNAME") && project.hasProperty("NEXUS_PASSWORD")) {
    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        repositories {
            maven {
                def baseUrl = "https://repo.devadri.es/repository/"

                url = uri(
                        baseUrl + (project.hasProperty("BUILD_NUMBER") ? "dev" : "releases")
                )
                credentials {
                    username = project.property("NEXUS_USERNAME")
                    password = project.property("NEXUS_PASSWORD")
                }
            }
        }
        publications {
            maven(MavenPublication) {
                groupId = this.group
                artifactId = rootProject.name
                version = this.version

                from components.java

                pom {
                    name = rootProject.name
                    description = this.description
                    url = "https://github.com/Adrigamer2950/AdriAPI"

                    licenses {
                        license {
                            name = 'GPL-3.0'
                            url = 'https://www.gnu.org/licenses/gpl-3.0.html'
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
                        system = 'GitHub'
                        url = 'https://github.com/Adrigamer2950/AdriAPI/issues'
                    }
                }
            }
        }
    }
}

repositories {
    mavenCentral()
    maven {
        name = "sonatype"
        url = "https://oss.sonatype.org/content/groups/public/"
    }
    maven {
        url = "https://repo.papermc.io/repository/maven-public/"
    }
    maven {
        name = "AlessioDP"
        url = "https://repo.alessiodp.com/releases"
    }
}

dependencies {
    // JetBrains Annotations
    compileOnly libs.jetbrains.annotations

    // Folia API
    //noinspection VulnerableLibrariesLocal
    compileOnly libs.folia.api

    // Lombok
    compileOnly libs.lombok
    annotationProcessor libs.lombok

    // Ansi
    compileOnly libs.jansi

    // Adventure API
    compileOnly libs.adventure.platform.bukkit
    bukkitLibrary libs.adventure.platform.bukkit

    // Libby
    implementation libs.libby
}

def targetJavaVersion = 17

tasks {
    tasks.withType(JavaCompile).configureEach {
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
            options.release = targetJavaVersion
            options.encoding = 'UTF-8'
        }
    }

    java {
        def javaVersion = JavaVersion.toVersion(targetJavaVersion)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }
    }

    bukkit {
        name = "AdriAPI"
        version = version
        main = "me.adrigamer2950.adriapi.AdriAPI"
        apiVersion = "1.18"
        author = author
        description = this.description
        website = "https://github.com/Adrigamer2950/AdriAPI"
        load = 'STARTUP'
        foliaSupported = true

        commands {
            adriapi {
                description = "Main command"
                usage = "/adriapi"
            }
        }
    }

    modrinth {
        token = System.getenv("MODRINTH_TOKEN")
        projectId = "adriapi"
        versionNumber = version
        versionName = rootProject.name + " " + version
        versionType = "release"
        uploadFile = jar
        gameVersions = [
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
        ]
        loaders = [
                "bukkit",
                "folia",
                "paper",
                "purpur",
                "spigot"
        ]
        syncBodyFrom = rootProject.file("README.md").text
    }

    runServer {
        minecraftVersion("1.20.2")

        downloadPlugins {
            // ViaVersion
            hangar("ViaVersion", "5.2.0")
        }
    }

    tasks.withType(AbstractRun).configureEach {
        javaLauncher = javaToolchains.launcherFor {
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

    shadowJar {
        archiveClassifier.set("")

        dependencies {
            relocate "net.byteflux.libby", "me.adrigamer2950.adriapi.lib.libby"

            //relocate "com.iridium.iridiumcolorapi", "me.adrigamer2950.adriapi.libs.iridiumcolorapi"
        }
    }

    build {
        finalizedBy("shadowJar")
    }
}