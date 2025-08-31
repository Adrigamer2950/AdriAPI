package me.devadri.obsidian.plugin

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.AutoRegister
import me.devadri.obsidian.command.AbstractCommand
import me.devadri.obsidian.user.User
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

@Suppress("unused")
@AutoRegister
class ObsidianCommand(plugin: ObsidianPlugin) : AbstractCommand(plugin, "obsidian") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage(
            Component.text("Obsidian Version ", NamedTextColor.GRAY)
                .append(Component.text(plugin.description.version, NamedTextColor.GOLD))
        )
    }
}
