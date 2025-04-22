package me.adrigamer2950.adriapi.plugin

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.AutoRegister
import me.adrigamer2950.adriapi.api.command.AbstractCommand
import me.adrigamer2950.adriapi.api.user.User
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

@Suppress("unused")
@AutoRegister
class AdriAPICommand(plugin: APIPlugin) : AbstractCommand(plugin, "adriapi") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage(
            Component.text("AdriAPI Version ", NamedTextColor.GRAY)
                .append(Component.text(plugin.description.version, NamedTextColor.GOLD))
        )
    }
}
