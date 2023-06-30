package me.adrigamer2950.adriapi.api.files.file.module;

import java.io.IOException;

public interface FileModule {

    Object get(String path);

    String getString(String path);

    Boolean getBoolean(String path);

    Integer getInteger(String path);

    void loadConfiguration(java.io.File file, boolean fileExists) throws IOException;

    void saveFile(java.io.File file) throws IOException;
}
