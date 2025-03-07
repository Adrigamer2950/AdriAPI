package me.adrigamer2950.adriapi.api.library.manager;

import net.byteflux.libby.Library;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Simple Library management
 * @since 2.2.0
 */
public interface LibraryManager {

    /**
     * @return The plugin
     */
    Plugin getPlugin();

    /**
     * Adds a library
     * @param library The library
     */
    void addLibrary(Library library);

    /**
     * Adds multiple libraries at once
     * @param libraries The libraries
     */
    void addLibraries(Library... libraries);

    /**
     * @return The libraries
     */
    Collection<Library> getLibraries();

    /**
     * Loads all libraries registered using {@link #addLibrary(Library)}
     */
    void loadLibraries();

    void addRepository(String url);

    /**
     *
     * @param plugin The plugin
     * @return A new instance of {@link LibraryManager}
     */
    static LibraryManager get(Plugin plugin) {
        return new LibraryManagerImpl(plugin);
    }
}
