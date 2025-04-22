package me.adrigamer2950.adriapi.api.library.manager

import net.byteflux.libby.Library
import net.byteflux.libby.classloader.URLClassLoaderHelper
import net.byteflux.libby.logging.adapters.JDKLogAdapter
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.jetbrains.annotations.ApiStatus
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Path
import net.byteflux.libby.LibraryManager as LibbyLibraryManager

@ApiStatus.Internal
class LibraryManagerImpl internal constructor(override val plugin: Plugin) : LibbyLibraryManager(
    JDKLogAdapter(plugin.logger), File(
        Bukkit.getPluginsFolder(), "AdriAPI"
    ).toPath(), "lib"
), LibraryManager {
    private val classLoader: URLClassLoaderHelper =
        URLClassLoaderHelper(plugin.javaClass.getClassLoader() as URLClassLoader?, this)

    override val libraries: MutableSet<Library> = HashSet()

    init {
        this.addMavenCentral()
    }

    override fun addToClasspath(file: Path?) {
        this.classLoader.addToClasspath(file)
    }

    override fun addLibrary(library: Library) {
        this.libraries.add(library)
    }

    override fun addLibraries(vararg libraries: Library) {
        libraries.forEach { this.addLibrary(it) }
    }

    override fun loadLibraries() {
        libraries.forEach { this.loadLibrary(it) }

        libraries.clear()
    }
}
