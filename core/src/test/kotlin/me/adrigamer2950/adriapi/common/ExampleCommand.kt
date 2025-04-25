package me.adrigamer2950.adriapi.common

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.AutoRegister
import me.adrigamer2950.adriapi.api.ExperimentalAPI
import me.adrigamer2950.adriapi.api.command.AbstractCommand
import me.adrigamer2950.adriapi.api.user.User

@OptIn(ExperimentalAPI::class)
@AutoRegister
open class ExampleAutoCommand(plugin: APIPlugin) : AbstractCommand(plugin, "example_auto") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage("nice command")
    }
}