package me.adrigamer2950.adriapi.api.logger;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SubLogger extends AdriAPILogger {
    public SubLogger(String name, Logger parent) {
        super(String.format("%s - %s", parent.getName(), name), parent);
    }
}
