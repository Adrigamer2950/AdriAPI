package me.adrigamer2950.adriapi.api.exceptions.database;

public class DatabaseConnectionNotEstablishedException extends RuntimeException {

    public DatabaseConnectionNotEstablishedException(String message) {
        super(message);
    }
}
