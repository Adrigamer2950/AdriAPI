@file:Suppress("VulnerableLibrariesLocal")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

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

val sourcesImplementation = configurations.create("sourcesImplementation")

val unpackSources by tasks.registering(Sync::class) {
    doNotTrackState("Unpack dependency sources")

    from({
        sourcesImplementation.resolve().map { zipTree(it) }
    })
    into(layout.buildDirectory.dir("unpacked-dep-sources"))

    exclude("META-INF/**")
}

tasks.register("sourcesJar", Jar::class) {
    dependsOn(unpackSources)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().allSource)
    from(layout.buildDirectory.dir("unpacked-dep-sources")) {
        exclude("META-INF/**")
    }
    archiveClassifier.set("sources")

    rootProject.subprojects.filter { it.name != "plugin" }.forEach { sub ->
        from(sub.file("src/main/java"))
        from(sub.file("src/main/kotlin"))
    }
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

    implementation(libs.libby)
    sourcesImplementation(libs.libby)

    implementation(project(":folia"))
//    sourcesImplementation(project(":folia"))

    compileOnly(libs.boosted.yaml)

    compileOnly(libs.reflections)

    compileOnly(libs.kotlinpoet)

    api(libs.xseries)
    compileOnly(libs.xseries)

    implementation(project(":nms:common")) {
        isTransitive = false
    }

    compileOnly(project(":nms:1_17_R1"))
    implementation(project(":nms:1_17_R1", "reobf"))

    compileOnly(project(":nms:1_18_R1"))
    implementation(project(":nms:1_18_R1", "reobf"))

    compileOnly(project(":nms:1_18_R2"))
    implementation(project(":nms:1_18_R2", "reobf"))

    compileOnly(project(":nms:1_19_R1"))
    implementation(project(":nms:1_19_R1", "reobf"))

    compileOnly(project(":nms:1_19_R2"))
    implementation(project(":nms:1_19_R2", "reobf"))

    compileOnly(project(":nms:1_19_R3"))
    implementation(project(":nms:1_19_R3", "reobf"))

    compileOnly(project(":nms:1_20_R1"))
    implementation(project(":nms:1_20_R1", "reobf"))

    compileOnly(project(":nms:1_20_R2"))
    implementation(project(":nms:1_20_R2", "reobf"))

    compileOnly(project(":nms:1_20_R3"))
    implementation(project(":nms:1_20_R3", "reobf"))

    implementation(project(":nms:1_20_R4", "archives")) {
        isTransitive = false
    }

    implementation(project(":nms:1_21_R1", "archives")) {
        isTransitive = false
    }
    implementation(project(":nms:1_21_R2", "archives")) {
        isTransitive = false
    }
    implementation(project(":nms:1_21_R3", "archives")) {
        isTransitive = false
    }
    implementation(project(":nms:1_21_R4", "archives")) {
        isTransitive = false
    }

    testImplementation(kotlin("test"))
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.mockbukkit)
    testImplementation(libs.slf4j.jdk14)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    dependencies {
        relocate("com.alessiodp.libby", "me.adrigamer2950.adriapi.lib.libby")

        exclude("com/cryptomorin/xseries/**")
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
                        PropertySpec.builder("ADRIAPI_VERSION", String::class)
                            .initializer("%S", rootProject.version as String)
                            .addModifiers(KModifier.CONST)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("JANSI_VERSION", String::class)
                            .initializer("%S", libs.versions.jansi.get())
                            .addModifiers(KModifier.CONST)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("REFLECTIONS_VERSION", String::class)
                            .initializer("%S", libs.versions.reflections.get())
                            .addModifiers(KModifier.CONST)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("XSERIES_VERSION", String::class)
                            .initializer("%S", libs.versions.xseries.get())
                            .addModifiers(KModifier.CONST)
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
