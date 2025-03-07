@file:Suppress("VulnerableLibrariesLocal")

import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    alias(libs.plugins.plugin.yml)
}

dependencies {
    // Paper API
    compileOnly(libs.paper.api)

    // Ansi
    compileOnly(libs.jansi)

    // Libby
    compileOnly(libs.libby)

    // Folia module
    compileOnly(project(":folia"))
}

bukkit {
    name = "AdriAPI"
    main = "me.adrigamer2950.adriapi.plugin.AdriAPI"
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
