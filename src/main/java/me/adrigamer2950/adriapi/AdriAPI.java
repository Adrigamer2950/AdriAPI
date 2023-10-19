package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.files.manager.FileManager;
import me.adrigamer2950.adriapi.api.files.yaml.YamlFile;
import me.adrigamer2950.adriapi.api.logger.AdriAPILogger;
import org.bukkit.plugin.java.*;
import org.jetbrains.annotations.*;
import me.adrigamer2950.adriapi.api.command.manager.*;
import me.adrigamer2950.adriapi.api.colors.*;

import java.io.*;
import java.util.*;

import me.adrigamer2950.adriapi.listeners.*;

@ApiStatus.Internal
public final class AdriAPI extends JavaPlugin {

    public static final AdriAPILogger LOGGER = new AdriAPILogger("AdriAPI", null);
    private static AdriAPI plugin;

    public static AdriAPI get() {
        return AdriAPI.plugin;
    }

    private CommandManager cmdManager;
    private FileManager fileManager;
    public YamlFile configFile;

    @Override
    public void onLoad() {
        plugin = this;
    }

    public void onEnable() {
        configFile = new YamlFile(this.getDataFolder().getAbsolutePath(), "config", this, true);

        List<String> l = List.of(
                String.format("|    <green>AdriAPI <gold>v%s", this.getDescription().getVersion()),
                String.format("|    <blue>Running on <green>Bukkit <gold>%s", this.getServer().getVersion()),
                "|    <gold>Loading");

        for (String s : l)
            LOGGER.info(Colors.translateAPIColors(s));

        this.fileManager = new FileManager(this);

        this.fileManager.registerConfigFile(configFile);
        try {
            this.fileManager.createConfigFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.cmdManager = new CommandManager(this);

        this.getServer().getPluginManager().registerEvents(new CustomEventsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ManagersListener(), this);

        this.cmdManager.registerCommand(new AdriAPICommand());

        LOGGER.info(Colors.translateAPIColors("<green><bold>Enabled"));
    }

    @Override
    public void onDisable() {
        try {
            this.fileManager.saveConfigFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info(Colors.translateAPIColors("<red><bold>Disabled"));

        this.cmdManager = null;
        this.fileManager = null;
    }
}
