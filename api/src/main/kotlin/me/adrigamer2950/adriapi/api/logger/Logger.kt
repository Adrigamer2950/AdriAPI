package me.adrigamer2950.adriapi.api.logger

import me.adrigamer2950.adriapi.api.logger.builder.LogBuilder

interface Logger {

    fun log(builder: LogBuilder.() -> Unit)
}