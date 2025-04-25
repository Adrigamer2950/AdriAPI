@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import com.squareup.kotlinpoet.*

plugins {
    kotlin("jvm")
    id("java")
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlinpoet)
    }
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

    compileOnly(libs.jansi)

    api(libs.libby)
    implementation(libs.libby)

    api(project(":folia"))
    implementation(project(":folia"))

    compileOnly(libs.boosted.yaml)

    compileOnly(libs.reflections)

    compileOnly(libs.kotlinpoet)

    api(libs.xseries)
    compileOnly(libs.xseries)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.mockbukkit)
    testImplementation(libs.slf4j.jdk14)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    dependencies {
        relocate("com.alessiodp.libby", "me.adrigamer2950.adriapi.lib.libby")
    }
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.named("build") {
    finalizedBy(tasks.named("shadowJar"))
}

val generatedDir = layout.buildDirectory.dir("generated/templates").get().asFile

sourceSets.main {
    java.srcDirs("src/main/java")
    kotlin.srcDirs("src/main/kotlin", generatedDir)
}

sourceSets.test {
    kotlin.srcDirs("src/test/kotlin")
}

tasks.test {
    doFirst {
        classpath = classpath.plus(
            files(tasks.named<ShadowJar>("shadowJar").get().archiveFile.get().asFile)
        )
    }

    useJUnitPlatform()
}

tasks.register("generateBuildConstants") {
    doLast {
        val fileSpec = FileSpec.builder("me.adrigamer2950.adriapi.api.internal", "BuildConstants")
            .addType(
                TypeSpec.objectBuilder("BuildConstants")
                    .addProperty(
                        PropertySpec.builder("JANSI_VERSION", String::class)
                            .initializer("%S", libs.versions.jansi.get())
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("REFLECTIONS_VERSION", String::class)
                            .initializer("%S", libs.versions.reflections.get())
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("XSERIES_VERSION", String::class)
                            .initializer("%S", libs.versions.xseries.get())
                            .build()
                    )
                    .build()
            )
            .build()

        val generatedDir = layout.buildDirectory.dir("generated/templates").get().asFile
        fileSpec.writeTo(generatedDir)
    }
}

tasks.named("compileKotlin") {
    dependsOn(tasks.named("generateBuildConstants"))
}

tasks.withType<Test>().configureEach {
    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    )
}
