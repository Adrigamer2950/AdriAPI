package me.adrigamer2950.adriapi.api.command

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.user.User

/**
 * Command interface. You can implement this interface
 * if you want to customize default behavior however
 * you see fit. Otherwise, you should extend {@link AbstractCommand}.
 *
 * @since 1.0.0
 */
interface Command {

    val name: String

    val plugin: APIPlugin

    val subCommands: List<Command>

    /**
     * Register the Command
     */
    fun register()

    /**
     * Unregister the Command
     */
    fun unRegister()

    /**
     * @param user The user that executed the command
     * @param args The arguments of the command
     * @param commandName The alias of the command
     */
    fun execute(user: User, args: Array<out String>, commandName: String)

    /**
     * @param user The user that executed the command
     * @param args The arguments of the command
     * @param commandName The alias of the command
     * @return The list of suggestions
     */
    fun tabComplete(user: User, args: Array<out String>, commandName: String): List<String>
}
