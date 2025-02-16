package me.adrigamer2950.adriapi.api.library.manager;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LibraryManagerImpl extends BukkitLibraryManager implements LibraryManager {

    protected final Plugin plugin;
    protected final Set<Library> libraries = new HashSet<>();

    public LibraryManagerImpl(Plugin plugin) {
        super(plugin);

        this.plugin = plugin;

        this.addMavenCentral();
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void addLibrary(Library library) {
        this.libraries.add(library);
    }

    @Override
    public void addLibraries(Library... libraries) {
        for (Library library : libraries) {
            this.addLibrary(library);
        }
    }

    @Override
    public Collection<Library> getLibraries() {
        return this.libraries;
    }

    @Override
    public void loadLibraries() {
        libraries.forEach(this::loadLibrary);
    }
}
