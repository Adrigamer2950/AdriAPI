@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
    kotlin("jvm")
    id("java")
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow)
}

val versionIsBeta = (parent?.properties?.get("version") as String).toDefaultLowerCase().contains("beta")

tasks.register("sourcesJar", Jar::class) {
    from(sourceSets.main.get().kotlin)
    archiveClassifier.set("sources")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

if (project.hasProperty("NEXUS_USERNAME") && project.hasProperty("NEXUS_PASSWORD")) {

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
            create<MavenPublication>("shadow") {
                groupId = rootProject.group as String
                artifactId = rootProject.name
                version = rootProject.version as String

                from(components["shadow"])
                artifact(tasks["sourcesJar"])
                pom {
                    name = rootProject.name
                    description.set(parent?.properties?.get("description") as String)
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

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly(libs.paper.api)

    implementation(libs.jansi)

    api(libs.libby)
    implementation(libs.libby)

    api(project(":api:folia"))
    implementation(project(":api:folia"))

    compileOnly(libs.boosted.yaml)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    dependencies {
        relocate("net.byteflux.libby", "me.adrigamer2950.adriapi.lib.libby")

        relocate("org.fusesource.jansi", "me.adrigamer2950.adriapi.lib.jansi")
    }
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.named("build") {
    finalizedBy(tasks.named("shadowJar"))
}

sourceSets.main {
    java.srcDirs("src/main/java")
    kotlin.srcDirs("src/main/kotlin")
}