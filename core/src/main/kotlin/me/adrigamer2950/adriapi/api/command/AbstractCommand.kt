package me.adrigamer2950.adriapi.api.command

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.event.CommandLoadedEvent
import me.adrigamer2950.adriapi.api.event.CommandUnloadedEvent
import me.adrigamer2950.adriapi.api.toUser
import me.adrigamer2950.adriapi.api.user.User
import me.adrigamer2950.adriapi.api.util.CommandUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

/**
 * Default implementation of Command
 *
 * @since 2.4.0
 */
@Suppress("unused")
abstract class AbstractCommand(
    override val plugin: APIPlugin,
    override val commandName: String,
    description: String,
    aliases: List<String>,
    override val subCommands: MutableList<Command>
) : BukkitCommand(commandName, description, "/$commandName", aliases), Command {

    constructor(plugin: APIPlugin, name: String) : this(plugin, name, mutableListOf())
    constructor(plugin: APIPlugin, name: String, subCommands: MutableList<Command>) : this(
        plugin,
        name,
        "No description",
        listOf(),
        subCommands
    )

    abstract override fun execute(user: User, args: Array<out String>, commandName: String)

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        this.execute(sender.toUser(), args, commandLabel)

        return true
    }

    override fun tabComplete(user: User, args: Array<out String>, commandName: String): List<String> {
        return listOf()
    }

    final override fun tabComplete(sender: CommandSender, commandLabel: String, args: Array<out String>): List<String> {
        return tabComplete(sender.toUser(), args, commandLabel)
    }

    @JvmOverloads
    @Suppress("LocalVariableName", "SameParameterValue")
    protected fun executeSubCommands(
        user: User,
        _args: Array<out String>,
        commandLabel: String,
        removeFirstArgument: Boolean = true
    ) {
        var args = _args

        if (subCommands.isEmpty()) return

        val help = this.findHelpCommand()

        if (args.isEmpty()) {
            if (help.isPresent) help.get().execute(user, args, commandLabel)

            return
        }

        val subCommand = this.subCommands.stream()
            .filter {
                it.commandName == args[0]
            }.findFirst()

        if (subCommand.isEmpty) {
            if (help.isPresent) help.get().execute(user, args, commandLabel)

            return
        }

        if (removeFirstArgument) args = args.drop(1).toTypedArray()

        subCommand.get().execute(user, args, commandLabel)
    }

    @JvmOverloads
    @Suppress("LocalVariableName", "SameParameterValue")
    protected fun suggestSubCommands(
        user: User,
        _args: Array<out String>,
        commandLabel: String,
        removeFirstArgument: Boolean = true
    ): List<String> {
        var args = _args

        if (subCommands.isEmpty()) return listOf()

        val help = this.findHelpCommand()

        if (args.isEmpty()) {
            if (help.isPresent) return help.get().tabComplete(user, args, commandLabel)

            return subCommands.map { it.commandName }
        }

        val subCommand = this.subCommands.stream()
            .filter {
                it.commandName == args[0]
            }.findFirst()

        if (subCommand.isEmpty) {
            if (help.isPresent) return help.get().tabComplete(user, args, commandLabel)

            return subCommands.filter { it.commandName.startsWith(args[0]) }.map { it.commandName }
        }

        if (removeFirstArgument) args = args.drop(1).toTypedArray()

        return subCommand.get().tabComplete(user, args, commandLabel)
    }

    private fun findHelpCommand(): Optional<Command> {
        return this.subCommands.stream()
            .filter {
                it.commandName == "help"
            }
            .findFirst()
    }

    @Override
    override fun unRegister() {
        CommandUtil.unRegisterCommand(this, plugin)

        Bukkit.getPluginManager().callEvent(CommandUnloadedEvent(this))
    }

    @Override
    override fun register() {
        CommandUtil.registerCommand(this, plugin)

        Bukkit.getPluginManager().callEvent(CommandLoadedEvent(this))
    }
}
