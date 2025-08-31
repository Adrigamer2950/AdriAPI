package me.devadri.obsidian

open class TestPlugin : ObsidianPlugin() {

    override fun onPreLoad() {
        debug = true

        logger.info("&6Loading...")
    }

    override fun onPostLoad() {
        logger.info("&aEnabled")
    }

    override fun onUnload() {
        logger.info("&cDisabled")
    }
}