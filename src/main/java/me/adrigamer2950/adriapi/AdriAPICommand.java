package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class AdriAPICommand extends Command {

    public AdriAPICommand() {
        super(AdriAPI.get(), "adriapi");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        sender.sendMessage(Colors.translateColors(String.format(
                "&7AdriAPI Version &6%s", AdriAPI.get().getDescription().getVersion()
        ), '&'));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return null;
    }
}
