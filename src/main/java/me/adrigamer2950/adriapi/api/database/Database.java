package me.adrigamer2950.adriapi.api.database;

import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.exceptions.database.DatabaseConnectionNotEstablishedException;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adriapi.api.logger.SubLogger;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private final APILogger LOGGER = new SubLogger("Databases", AdriAPI.LOGGER);
    private final String name;
    private final Plugin plugin;

    private Connection connection = null;
    private final DatabaseType type;
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String databaseName;
    private String h2Path = "";

    public Database(DatabaseType type, String name, Plugin plugin, String hostname, int port, String username, String password, String databaseName) {
        this.name = name;
        this.plugin = plugin;

        this.type = type;
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    public Database(DatabaseType type, String name, Plugin plugin, String path) {
        this.name = name;
        this.plugin = plugin;

        this.type = type;
        this.h2Path = path;
    }

    public String getName() {
        return name;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void connect() throws SQLException {
        if(this.type.equals(DatabaseType.MYSQL)) {
            connection = DriverManager.getConnection(
                    String.format("jdbc:%s://%s:%s/%s", this.type.name().toLowerCase(), this.hostname, this.port, this.databaseName),
                    this.username, this.password
            );
        } else if(this.type.equals(DatabaseType.H2)) {
            connection = DriverManager.getConnection(
                    String.format("jdbc:h2:%s", this.h2Path),
                    this.username, this.password
            );
        }

        LOGGER.log("Database connected succesfully");
    }

    public void disconnect() {
        this.connection = null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void makeStatement(String statement) {
        if(this.connection == null) throw new DatabaseConnectionNotEstablishedException("Database with name '%s' doesn't have established a successful connection, please use 'connect' method before making a statement.");

        java.sql.PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement(statement);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet makeQuery(String query) {
        if(this.connection == null) throw new DatabaseConnectionNotEstablishedException("Database with name '%s' doesn't have established a successful connection, please use 'connect' method before making a query.");

        java.sql.PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement(query);

            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
