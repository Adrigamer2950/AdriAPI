package me.adrigamer2950.adriapi.api.exceptions;

/**
 * Thrown if trying to create a manager that already exists
 */
public class DuplicatedManagerException extends RuntimeException {

    public DuplicatedManagerException(String message) {
        super(message);
    }
}
