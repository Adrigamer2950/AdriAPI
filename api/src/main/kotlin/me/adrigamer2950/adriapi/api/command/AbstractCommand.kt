package me.adrigamer2950.adriapi.api.command

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.event.CommandLoadedEvent
import me.adrigamer2950.adriapi.api.event.CommandUnloadedEvent
import me.adrigamer2950.adriapi.api.user.User
import me.adrigamer2950.adriapi.api.user.UserFactory
import me.adrigamer2950.adriapi.api.util.CommandUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

import java.util.Optional

/**
 * Default implementation of {@link Command}
 *
 * @since 2.4.0
 */
@Suppress("unused")
abstract class AbstractCommand(
    override val plugin: APIPlugin,
    override val commandName: String,
    description: String,
    aliases: List<String>,
    override val subCommands: List<Command>
) : BukkitCommand(commandName, description, "/$commandName", aliases), Command {

    constructor(plugin: APIPlugin, name: String) : this(plugin, name, listOf())
    constructor(plugin: APIPlugin, name: String, subCommands: List<Command>) : this(
        plugin,
        name,
        "No description",
        listOf(),
        subCommands
    )

    abstract override fun execute(user: User, args: Array<out String>, commandLabel: String)

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        this.execute(UserFactory.fromBukkitSender(sender), args, commandLabel)

        return true
    }

    override fun tabComplete(user: User, args: Array<out String>, commandLabel: String): List<String> {
        return listOf()
    }

    override fun tabComplete(sender: CommandSender, commandLabel: String, args: Array<out String>): List<String> {
        return tabComplete(UserFactory.fromBukkitSender(sender), args, commandLabel)
    }

    protected fun executeSubCommands(user: User, args: Array<out String>, commandLabel: String) {
        this.executeSubCommands(user, args, commandLabel, true)
    }

    @Suppress("LocalVariableName", "SameParameterValue")
    protected fun executeSubCommands(
        user: User,
        _args: Array<out String>,
        commandLabel: String,
        removeFirstArgument: Boolean
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

        if (removeFirstArgument) { // Remove first argument
            val l = args.toMutableList()
            l.removeAt(0)
            args = l.toTypedArray()
        }

        subCommand.get().execute(user, args, commandLabel)
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
        CommandUtil.unRegisterCommand(this, plugin.commandManager)

        Bukkit.getPluginManager().callEvent(CommandUnloadedEvent(this))
    }

    @Override
    override fun register() {
        CommandUtil.registerCommand(this, plugin)

        Bukkit.getPluginManager().callEvent(CommandLoadedEvent(this))
    }
}
