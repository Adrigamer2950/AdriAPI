package me.adrigamer2950.adriapi.api.util

import kotlin.reflect.KClass

@Suppress("unused")
object ClassUtil {

    @JvmStatic
    fun searchForClass(name: String): Class<*>? {
        return try {
            Class.forName(name)
        } catch (ignored: ClassNotFoundException) {
            null
        }
    }

    @JvmStatic
    fun searchForKClass(name: String): KClass<*>? {
        return searchForClass(name)?.kotlin
    }

    @JvmStatic
    fun classExists(name: String): Boolean {
        return searchForClass(name) != null
    }
}