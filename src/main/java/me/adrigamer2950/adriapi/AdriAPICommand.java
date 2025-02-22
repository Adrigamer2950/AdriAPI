package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.ApiStatus;

/**
 * Only for internal use
 */
@ApiStatus.Internal
public class AdriAPICommand extends Command<AdriAPI> {

    public AdriAPICommand(AdriAPI plugin) {
        super(plugin, "adriapi");
    }

    @Override
    public boolean execute(User user, String label, String[] args) {
        user.sendMessage(
                Component.text("AdriAPI Version ", NamedTextColor.GRAY)
                        .append(Component.text(getPlugin().getDescription().getVersion(), NamedTextColor.GOLD))
        );

        return true;
    }
}
