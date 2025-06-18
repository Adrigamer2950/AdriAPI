plugins {
    kotlin("jvm")
    id("java")
    id("maven-publish")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    tasks.register("sourcesJar", Jar::class) {
        from(sourceSets.main.get().kotlin)
        archiveClassifier.set("sources")

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    if (this.name == "common") return@subprojects

    apply(plugin = "maven-publish")

    afterEvaluate {
        dependencies {
            compileOnly(project(":nms:common"))
        }

        tasks.assemble {
            finalizedBy(tasks.named("reobfJar"))
        }
    }
}

tasks.build {
    enabled = false
}

tasks.jar {
    enabled = false
}