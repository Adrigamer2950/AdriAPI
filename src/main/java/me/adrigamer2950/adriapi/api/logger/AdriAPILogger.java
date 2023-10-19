package me.adrigamer2950.adriapi.api.logger;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AdriAPILogger extends Logger {

    public AdriAPILogger(String name, Logger parent) {
        super(name, null);
        if(parent != null) setParent(parent);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(logRecord.getMessage());
        super.log(logRecord);
    }
}
