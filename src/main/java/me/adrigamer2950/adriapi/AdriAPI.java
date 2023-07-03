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

import java.io.IOException;
import java.util.List;

@ApiStatus.Internal
public final class AdriAPI extends JavaPlugin {

    private static AdriAPI plugin;
    public static AdriAPI get() {return plugin;}
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

        for(String s : l)
            getLogger().info(Colors.translateAPIColors(s));

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

        getLogger().info(Colors.translateAPIColors("<green><bold>Enabled"));
    }

    @Override
    public void onDisable() {
        try {
            fileManager.saveConfigFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().cancelTasks(this);

        getLogger().info(Colors.translateAPIColors("<red><bold>Disabled"));

        plugin = null;
        cmdManager = null;
        fileManager = null;
    }
}
