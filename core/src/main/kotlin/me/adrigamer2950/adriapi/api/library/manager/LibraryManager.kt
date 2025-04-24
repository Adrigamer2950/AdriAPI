package me.adrigamer2950.adriapi.api.library.manager

import net.byteflux.libby.Library
import org.bukkit.plugin.Plugin

/**
 * Simple Library management
 * @since 2.2.0
 */
@Suppress("unused")
interface LibraryManager {

    /**
     * @return The plugin
     */
    val plugin: Plugin

    /**
     * Adds a library
     * @param library The library
     */
    fun addLibrary(library: Library)

    /**
     * Adds multiple libraries at once
     * @param libraries The libraries
     */
    fun addLibraries(vararg libraries: Library)

    /**
     * Loads a library
     * @param library The library
     */
    fun loadLibrary(library: Library)

    /**
     * Loads an iterable list of libraries
     * @param libraries The libraries
     */
    fun loadLibraries(vararg libraries: Library)

    /**
     * @return The libraries
     */
    val libraries: MutableCollection<Library>

    /**
     * Loads all libraries registered using [.addLibrary]
     */
    fun loadLibraries()

    fun addRepository(url: String)

    companion object {
        /**
         * @param plugin The plugin
         * @return A new instance of [LibraryManager]
         */
        @JvmStatic
        fun get(plugin: Plugin): LibraryManager {
            return LibraryManagerImpl(plugin)
        }
    }
}
