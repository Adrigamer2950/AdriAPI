package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.command.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApiStatus.Internal
public class AdriAPICommand extends Command<AdriAPI> {

    public AdriAPICommand(AdriAPI plugin) {
        super(plugin, "adriapi");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        //noinspection deprecation
        sender.sendMessage(
                Component.text("AdriAPI Version ", NamedTextColor.GRAY)
                        .append(Component.text(getPlugin().getDescription().getVersion(), NamedTextColor.GOLD))
        );

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        return null;
    }
}
