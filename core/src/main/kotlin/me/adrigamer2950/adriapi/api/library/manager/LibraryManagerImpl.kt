package me.adrigamer2950.adriapi.api.library.manager

import com.alessiodp.libby.Library
import com.alessiodp.libby.StandaloneLibraryManager
import com.alessiodp.libby.logging.adapters.JDKLogAdapter
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.jetbrains.annotations.ApiStatus.Internal
import java.io.File

@Internal
class LibraryManagerImpl internal constructor(override val plugin: Plugin, folderName: String = plugin.name) : StandaloneLibraryManager(
    JDKLogAdapter(plugin.logger), File(
        Bukkit.getPluginsFolder(), folderName
    ).toPath(), "lib"
), LibraryManager {

    override val libraries: MutableSet<Library> = HashSet()

    init {
        this.addMavenCentral()
    }

    override fun addLibrary(library: Library) {
        this.libraries.add(library)
    }

    override fun addLibraries(vararg libraries: Library) {
        libraries.forEach { this.addLibrary(it) }
    }

    override fun loadLibraries(vararg libraries: Library) {
        libraries.forEach { this.loadLibrary(it) }
    }

    override fun loadLibraries() {
        libraries.forEach { this.loadLibrary(it) }

        libraries.clear()
    }
}
