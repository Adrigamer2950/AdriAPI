package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.listeners.CustomEventsListener;
import me.adrigamer2950.adriapi.listeners.ManagersListener;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Set;

@ApiStatus.Internal
public final class AdriAPI extends APIPlugin {

    private static AdriAPI plugin;

    public static AdriAPI get() {
        return AdriAPI.plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPreLoad() {
        plugin = this;

        List<Component> l = List.of(
                Component.text("|    ")
                        .append(Component.text("AdriAPI ", NamedTextColor.GREEN))
                        .append(Component.text("v%s".formatted(this.getDescription().getVersion()), NamedTextColor.GOLD)),
                Component.text("|    ")
                        .append(Component.text("Running on %s".formatted(Objects.requireNonNull(ServerType.getType()).getName()), NamedTextColor.BLUE)),
                Component.text("|    ")
                        .append(Component.text("Loading...", NamedTextColor.GOLD))
        );

        for (Component c : l)
            this.getApiLogger().info(c);
    }

    @Override
    public void onPostLoad() {
        this.registerListeners(
                Set.of(
                        new CustomEventsListener(),
                        new ManagersListener()
                )
        );

        this.registerCommand(new AdriAPICommand());

        this.getApiLogger().info(Component.text("Enabled", NamedTextColor.GREEN, TextDecoration.BOLD));
    }

    @Override
    public void onUnload() {
        this.getApiLogger().info(Component.text("Disabled", NamedTextColor.RED, TextDecoration.BOLD));
    }
}
