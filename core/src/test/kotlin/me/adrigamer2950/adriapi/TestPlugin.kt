package me.adrigamer2950.adriapi

import me.adrigamer2950.adriapi.api.APIPlugin

open class TestPlugin : APIPlugin() {

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