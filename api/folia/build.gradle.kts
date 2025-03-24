plugins {
    id("java")
}

group = "me.adrigamer2950.adriapi"

dependencies {
    // JetBrains Annotations
    compileOnly(libs.jetbrains.annotations)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Folia
    compileOnly(libs.folia.api)
}