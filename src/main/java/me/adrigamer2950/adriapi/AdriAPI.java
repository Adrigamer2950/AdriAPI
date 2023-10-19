package me.adrigamer2950.adriapi;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.files.FileManager;
import me.adrigamer2950.adriapi.api.files.file.File;
import me.adrigamer2950.adriapi.listeners.CustomEventsListener;
import me.adrigamer2950.adriapi.listeners.ManagersListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
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
    public final File configFile = new File(this.getDataFolder().getAbsolutePath(), "config", File.FileType.YML, this);

    @Override
    public void onLoad() {
        plugin = this;
        List<String> l = List.of(String.format("|    <green>AdriAPI <gold>v%s", getDescription().getVersion()),
                String.format("|    <blue>Running on <green>Bukkit <blue>- <gold>%s", getServer().getName()),
                "|    <gold>Loading"
        );
        List<String> l = List.of(
                String.format("|    <green>AdriAPI <gold>v%s", this.getDescription().getVersion()),
                String.format("|    <blue>Running on <green>Bukkit <gold>%s", this.getServer().getVersion()),
                "|    <gold>Loading");

        for(String s : l)
            getLogger().info(Colors.translateAPIColors(s));
        for (String s : l)
            LOGGER.info(Colors.translateAPIColors(s));

        fileManager = new FileManager(this);

        try {
            fileManager.registerConfigFile(configFile);

            fileManager.createConfigFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cmdManager = new CommandManager(this);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CustomEventsListener(), this);
        getServer().getPluginManager().registerEvents(new ManagersListener(), this);
        cmdManager.registerCommand(new AdriAPICommand());

        getLogger().info(String.valueOf(
                ((FileModule4Toml) configFile.module)
                        .getTable("tabla")
                        .getTable("otra_tabla")
                        .getTable("otra_tabla_mas")
                        .getString("un_texto")
        ));

        LOGGER.info(Colors.translateAPIColors("<green><bold>Enabled"));
    }

    @Override
    public void onDisable() {
        try {
            fileManager.saveConfigFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(Colors.translateAPIColors("<red><bold>Disabled"));

        getLogger().info(Colors.translateAPIColors("<red><bold>Disabled"));

        plugin = null;
        cmdManager = null;
        fileManager = null;
        this.cmdManager = null;
        this.fileManager = null;
    }
}
