@file:Suppress("unused")

package me.adrigamer2950.adriapi.common

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.AutoRegister
import me.adrigamer2950.adriapi.api.command.AbstractCommand
import me.adrigamer2950.adriapi.api.user.User

@AutoRegister
class ExampleAutoCommand(plugin: APIPlugin) : AbstractCommand(plugin, "example_auto") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage("nice auto command")
    }
}

class ExampleCommand(plugin: APIPlugin) : AbstractCommand(plugin, "example") {
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