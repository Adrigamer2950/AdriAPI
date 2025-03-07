@file:Suppress("VulnerableLibrariesLocal")

import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    alias(libs.plugins.plugin.yml)
}

dependencies {
    compileOnly(libs.paper.api)

    compileOnly(project(":folia"))

    compileOnly(project(":api"))
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
