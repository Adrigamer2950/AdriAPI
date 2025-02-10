package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.listeners.CustomEventsListener;
import me.adrigamer2950.adriapi.listeners.InventoriesListener;
import me.adrigamer2950.adriapi.listeners.ManagersListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Set;

@ApiStatus.Internal
public final class AdriAPI extends APIPlugin {

    @SuppressWarnings("deprecation")
    @Override
    public void onPreLoad() {
        List<Component> l = List.of(
                Component.text("|    ")
                        .append(Component.text("AdriAPI ", NamedTextColor.GREEN))
                        .append(Component.text("v%s".formatted(this.getDescription().getVersion()), NamedTextColor.GOLD)),
                Component.text("|    ")
                        .append(Component.text("Running on %s".formatted(this.getServerType().getName()), NamedTextColor.BLUE)),
                Component.text("|    ")
                        .append(Component.text("Loading...", NamedTextColor.GOLD))
        );

        for (Component c : l)
            this.getLogger().info(c);
    }

    @Override
    public void onPostLoad() {
        this.registerListeners(
                Set.of(
                        new CustomEventsListener(),
                        new ManagersListener(this),
                        new InventoriesListener()
                )
        );

        this.registerCommand(new AdriAPICommand(this));

        this.getLogger().info(Component.text("Enabled", NamedTextColor.GREEN, TextDecoration.BOLD));
    }

    @Override
    public void onUnload() {
        this.getLogger().info(Component.text("Disabled", NamedTextColor.RED, TextDecoration.BOLD));
    }

    @Override
    protected int getBStatsServiceId() {
        return 20135;
    }
}
