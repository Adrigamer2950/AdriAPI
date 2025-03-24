package me.adrigamer2950.adriapi.plugin;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.plugin.listeners.InventoriesListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

public final class AdriAPI extends APIPlugin {

    @Override
    public void onPreLoad() {
        List<Component> l = List.of(
                Component.text("|    ")
                        .append(Component.text("AdriAPI ", NamedTextColor.GREEN))
                        .append(Component.text("v%s".formatted(this.getDescription().getVersion()), NamedTextColor.GOLD)),
                Component.text("|    ")
                        .append(Component.text("Running on %s".formatted(this.getServerType().getServerName()), NamedTextColor.BLUE)),
                Component.text("|    ")
                        .append(Component.text("Loading...", NamedTextColor.GOLD))
        );

        for (Component c : l)
            this.getLogger().info(c);
    }

    @Override
    public void onPostLoad() {
        this.registerListener(new InventoriesListener());

        this.registerCommand(new AdriAPICommand(this, "adriapi"));

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
