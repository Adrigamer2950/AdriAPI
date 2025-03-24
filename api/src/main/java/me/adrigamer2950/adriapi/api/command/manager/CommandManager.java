package me.adrigamer2950.adriapi.api.command.manager;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Optional;

/**
 * Register and unregister commands
 *
 * @see Command
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class CommandManager {

    private final APIPlugin plugin;

    private final MethodHandle syncCommands = this.findSyncCommandsMethod();

    private MethodHandle findSyncCommandsMethod() {
        try {
            return MethodHandles.lookup().findVirtual(Bukkit.getServer().getClass(), "syncCommands", MethodType.methodType(void.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Could not find syncCommands method", e);
        }
    }

    /**
     * Register commands
     *
     * @param command The command that you want to register
     * @throws IllegalArgumentException if command is already registered
     */
    public void registerCommand(@NotNull @NonNull Command command) {
        command.register();
    }

    public void unRegisterCommand(@NotNull @NonNull Command command) {
        command.unRegister();
    }

    public void syncCommands() {
        try {
            if (this.syncCommands != null) this.syncCommands.invoke(Bukkit.getServer());
        } catch (Throwable e) {
            this.plugin.getLogger().error("Could not sync commands", e);
        }
    }

    public Optional<Command> getCommand(String name) {
        return Bukkit.getCommandMap().getKnownCommands().values().stream()
                .filter(command -> command instanceof Command)
                .map(command -> (Command) command)
                .filter(command -> command.getPlugin().equals(this.plugin))
                .filter(command -> command.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @SuppressWarnings("unused")
    public Command getCommandOrNull(String name) {
        return this.getCommand(name).orElse(null);
    }
}
