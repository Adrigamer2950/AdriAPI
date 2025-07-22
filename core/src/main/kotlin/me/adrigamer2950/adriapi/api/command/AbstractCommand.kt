package me.adrigamer2950.adriapi.api.command

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.event.CommandLoadedEvent
import me.adrigamer2950.adriapi.api.event.CommandUnloadedEvent
import me.adrigamer2950.adriapi.api.toUser
import me.adrigamer2950.adriapi.api.user.User
import me.adrigamer2950.adriapi.api.util.CommandUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender

/**
 * Default implementation of Command
 *
 * @since 2.4.0
 */
@Suppress("unused")
abstract class AbstractCommand @JvmOverloads constructor(
    override val plugin: APIPlugin,
    name: String,
    description: String = "No description",
    aliases: List<String> = listOf<String>(),
    override val subCommands: MutableList<Command> = mutableListOf<Command>(),
    usage: String = "/$name"
) : BukkitCommand(name, description, usage, aliases), Command {

    override var info: CommandInfo = CommandInfo(this.name, this.description, this.aliases)

    override fun setDescription(description: String): BukkitCommand = apply {
        this.info = CommandInfo(this.name, description, this.aliases)
        this.description = description
    }

    override fun setAliases(aliases: List<String>): BukkitCommand = apply {
        this.info = CommandInfo(this.name, this.description, aliases)
        this.aliases = aliases
    }

    override fun setName(name: String): Boolean {
        if (!isRegistered) {
            this.info = CommandInfo(name, this.description, this.aliases)
            this.name = name
            return true
        } else {
            return false
        }
    }

    override fun setPermission(ignored: String?) {
        return super.setPermission(null)
    }

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

    final override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): List<String?> {
        return tabComplete(sender.toUser(), args, this.info.name)
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
            help?.execute(user, args, commandLabel)

            return
        }

        val subCommand = this.subCommands.firstOrNull { it.info.name == args[0] || it.info.aliases.contains(args[0]) }

        if (subCommand == null) {
            help?.execute(user, args, commandLabel)

            return
        }

        if (removeFirstArgument) args = args.drop(1).toTypedArray()

        subCommand.execute(user, args, commandLabel)
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

        if (args.isEmpty()) return subCommands.map { it.info.name }

        val subCommand = this.subCommands.firstOrNull { it.info.name == args[0] || it.info.aliases.contains(args[0]) }

        if (subCommand == null) {
            return listOf(
                *subCommands.map { it.info.name }.toTypedArray(),
                *subCommands.flatMap { it.info.aliases }.toTypedArray()
            ).filter { it.startsWith(args[0]) }
        }

        if (removeFirstArgument) args = args.drop(1).toTypedArray()

        return subCommand.tabComplete(user, args, commandLabel)
    }

    private fun findHelpCommand(): Command? {
        return this.subCommands.firstOrNull {
            it.info.name == "help"
        }
    }

    @Override
    override fun unRegister() {
        CommandUtil.unRegisterCommand(this, plugin)

        unregister(Bukkit.getCommandMap())

        Bukkit.getPluginManager().callEvent(CommandUnloadedEvent(this))
    }

    @Override
    override fun register() {
        CommandUtil.registerCommand(this, plugin)

        register(Bukkit.getCommandMap())

        Bukkit.getPluginManager().callEvent(CommandLoadedEvent(this))
    }
}
