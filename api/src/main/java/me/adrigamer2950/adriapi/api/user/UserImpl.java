package me.adrigamer2950.adriapi.api.user;

import me.adrigamer2950.adriapi.api.colors.Colors;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

/**
 * Implementation of {@link User}. Preferable to use it as
 * this class may change overtime without warning
 *
 * @see User
 * @since 2.0.0
 */
@ApiStatus.Internal
public class UserImpl implements User {

    private final CommandSender sender;

    UserImpl(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isConsole() {
        return Bukkit.getConsoleSender().equals(sender);
    }

    @Override
    public boolean isPlayer() {
        return !isConsole();
    }

    @Override
    public Optional<ConsoleCommandSender> getConsole() {
        if (isConsole()) return Optional.of((ConsoleCommandSender) sender);
        return Optional.empty();
    }

    @Override
    public Optional<Player> getPlayer() {
        if (isPlayer()) return Optional.of((Player) sender);
        return Optional.empty();
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(
                this.isConsole()
                        ? Colors.translateToAnsi(message)
                        : Colors.translateColors(message)
        );
    }

    @Override
    public void sendMessage(String... messages) {
        for (String msg : messages)
            this.sendMessage(msg);
    }

    @Override
    public void sendMessage(Component component) {
        sender.sendMessage(component);
    }

    @Override
    public void sendMessage(Component... components) {
        for (Component comp : components)
            this.sendMessage(comp);
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @SuppressWarnings("removal")
    @Override
    public Component name() {
        return Component.text(this.getName());
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
