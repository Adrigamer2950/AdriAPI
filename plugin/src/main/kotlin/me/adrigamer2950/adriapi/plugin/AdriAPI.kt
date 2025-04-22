package me.adrigamer2950.adriapi.plugin

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.util.ServerType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

class AdriAPI : APIPlugin() {

    override fun bStatsServiceId(): Int {
        return 20135
    }

    override fun onPreLoad() {
        val l = listOf(
            Component.text("|    ")
                .append(Component.text("AdriAPI ", NamedTextColor.GREEN))
                .append(Component.text("v${this.description.version}", NamedTextColor.GOLD)),
            Component.text("|    ")
                .append(Component.text("Running on ${ServerType.name}", NamedTextColor.BLUE)),
            Component.text("|    ")
                .append(Component.text("Loading...", NamedTextColor.GOLD))
        )

        l.forEach(this.logger::info)
    }

    override fun onPostLoad() {
        this.logger.info(Component.text("Enabled", NamedTextColor.GREEN, TextDecoration.BOLD))
    }

    override fun onUnload() {
        this.logger.info(Component.text("Disabled", NamedTextColor.RED, TextDecoration.BOLD))
    }
}
