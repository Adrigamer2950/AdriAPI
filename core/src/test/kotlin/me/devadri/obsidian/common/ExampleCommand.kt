@file:Suppress("unused")

package me.devadri.obsidian.common

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.AutoRegister
import me.devadri.obsidian.command.AbstractCommand
import me.devadri.obsidian.user.User

@AutoRegister
class ExampleAutoCommand(plugin: ObsidianPlugin) : AbstractCommand(plugin, "example_auto") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage("nice auto command")
    }
}

class ExampleCommand(plugin: ObsidianPlugin) : AbstractCommand(plugin, "example") {
    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage("nice command")
    }

    override fun tabComplete(user: User, args: Array<out String>, commandName: String): List<String> {
        if (args.isEmpty()) return listOf()

        if (args[0] == "test1") {
            if (args.size > 1 && args[1] == "test2") {
                return listOf("test3", "test4")
            }
        }

        return listOf()
    }
}