package me.adrigamer2950.adriapi.plugin;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.AbstractCommand;
import me.adrigamer2950.adriapi.api.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class AdriAPICommand extends AbstractCommand {

    public AdriAPICommand(@NotNull APIPlugin plugin, @NotNull String name) {
        super(plugin, name);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] args, @NotNull String commandName) {
        user.sendMessage(
                Component.text("AdriAPI Version ", NamedTextColor.GRAY)
                        .append(Component.text(getPlugin().getDescription().getVersion(), NamedTextColor.GOLD))
        );
    }
}
