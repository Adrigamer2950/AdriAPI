@file:Suppress("VulnerableLibrariesLocal")

import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.minotaur)
    alias(libs.plugins.run.server)
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
}

val versionIsBeta = (parent?.properties?.get("version") as String).toDefaultLowerCase().contains("beta")

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
                groupId = rootProject.group as String
                artifactId = rootProject.name
                version = this.version

                from(components["java"])
                artifact(tasks["shadowJar"])
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
    compileOnly(libs.paper.api)

    compileOnly(libs.jansi)

    compileOnly(libs.libby)

    compileOnly(project(":folia"))
}