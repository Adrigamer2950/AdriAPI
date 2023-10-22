package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.files.manager.FileManager;
import me.adrigamer2950.adriapi.api.files.yaml.YamlFile;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import org.bukkit.plugin.java.*;
import org.jetbrains.annotations.*;
import me.adrigamer2950.adriapi.api.command.manager.*;

import java.io.*;
import java.util.*;

import me.adrigamer2950.adriapi.listeners.*;

@ApiStatus.Internal
public final class AdriAPI extends JavaPlugin {

    public static final APILogger LOGGER = new APILogger("AdriAPI", null);
    private static AdriAPI plugin;

    public static AdriAPI get() {
        return AdriAPI.plugin;
    }

    private CommandManager cmdManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    public void onEnable() {
        List<String> l = List.of(
                String.format("|    <green>AdriAPI <gold>v%s", this.getDescription().getVersion()),
                String.format("|    <blue>Running on <green>Bukkit <gold>%s", this.getServer().getVersion()),
                "|    <gold>Loading");

        for (String s : l)
            LOGGER.log(s);

        this.cmdManager = new CommandManager(this);

        this.getServer().getPluginManager().registerEvents(new CustomEventsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ManagersListener(), this);

        this.cmdManager.registerCommand(new AdriAPICommand());

        LOGGER.log("<green><bold>Enabled");
    }

    @Override
    public void onDisable() {
        LOGGER.log("<red><bold>Disabled");

        this.cmdManager = null;
    }
}
