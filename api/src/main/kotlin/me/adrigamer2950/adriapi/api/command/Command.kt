package me.adrigamer2950.adriapi.api.command

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.user.User

typealias BukkitCommand = org.bukkit.command.Command

/**
 * Command interface. You can implement this interface
 * if you want to customize default behavior however
 * you see fit. Otherwise, you should extend AbstractCommand
 * if you want to use default behavior.
 *
 * @since 1.0.0
 */
interface Command {

    /**
     * This could be named "name", but an
     * "Inherited platform declarations clash" error
     * or an "Accidental override" error
     * would be thrown at compile time 😿
     */
    val commandName: String

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
