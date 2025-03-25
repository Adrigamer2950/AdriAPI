package me.adrigamer2950.adriapi.plugin

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.command.AbstractCommand
import me.adrigamer2950.adriapi.api.user.User
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class AdriAPICommand(plugin: APIPlugin, name: String) : AbstractCommand(plugin, name) {

    override fun execute(user: User, args: Array<out String>, commandLabel: String) {
        user.sendMessage(
            Component.text("AdriAPI Version ", NamedTextColor.GRAY)
                .append(Component.text(plugin.description.version, NamedTextColor.GOLD))
        )
    }
}
