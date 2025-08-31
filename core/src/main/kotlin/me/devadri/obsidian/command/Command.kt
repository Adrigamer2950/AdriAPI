package me.devadri.obsidian.command

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.user.User

typealias BukkitCommand = org.bukkit.command.Command

/**
 * Command interface. You can implement this interface
 * if you want to customize default behavior however
 * you see fit. Otherwise, you should extend AbstractCommand
 * if you don't want to code any custom behavior
 *
 * @since 1.0.0
 */
interface Command {

    val info: CommandInfo

    val plugin: ObsidianPlugin

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
