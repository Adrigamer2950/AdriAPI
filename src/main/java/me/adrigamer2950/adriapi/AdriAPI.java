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

    @Override
    public void onPreLoad() {
        plugin = this;

        List<String> l = List.of(
                String.format("|    <green>AdriAPI <gold>v%s", this.getDescription().getVersion()),
                String.format("|    <blue>Running on <green>Bukkit <gold>%s", this.getServer().getVersion()),
                "|    <gold>Loading...");

        for (String s : l)
            this.getApiLogger().info(s);
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

        this.getApiLogger().info("&a&lEnabled");
    }

    @Override
    public void onUnload() {
        this.getApiLogger().info("&c&lDisabled");
    }
}
