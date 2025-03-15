package me.adrigamer2950.adriapi.api.library.manager;

import net.byteflux.libby.Library;
import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ApiStatus.Internal
public class LibraryManagerImpl extends net.byteflux.libby.LibraryManager implements LibraryManager {

    private final URLClassLoaderHelper classLoader;

    protected final Plugin plugin;
    protected final Set<Library> libraries = new HashSet<>();

    public LibraryManagerImpl(Plugin plugin) {
        super(new JDKLogAdapter(plugin.getLogger()), new File(Bukkit.getPluginsFolder(), "AdriAPI").toPath(), "lib");
        this.classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader(), this);

        this.plugin = plugin;

        this.addMavenCentral();
    }

    protected void addToClasspath(Path file) {
        this.classLoader.addToClasspath(file);
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
