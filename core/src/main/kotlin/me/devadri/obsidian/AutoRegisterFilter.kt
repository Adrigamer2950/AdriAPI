package me.devadri.obsidian

/**
 * Filters auto-registration of a Listener/Command
 */
interface AutoRegisterFilter {

    /**
     * Decides if the Listener/Command should be auto-registered.
     *
     * @param plugin The plugin that is registering the Listener/Command
     * @return true if the Listener/Command should be auto-registered, false otherwise
     */
    fun shouldBeRegistered(plugin: ObsidianPlugin): Boolean
}