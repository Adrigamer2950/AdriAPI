package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.nms.common.NmsSound
import me.adrigamer2950.adriapi.api.nms.common.NmsVersions
import kotlin.reflect.KClass

object Nms {

    @JvmField
    val sound: NmsSound

    init {
        sound = getNMSInstance(getNMSClass("NmsSoundImpl"))
    }

    private fun <T : Any> getNMSClass(@Suppress("SameParameterValue") name: String): KClass<T> {
        return try {
            @Suppress("UNCHECKED_CAST")
            Class.forName("${Nms::class.java.packageName}.nms.${NmsVersions.getCurrent().name.replace('V', 'v')}.$name").kotlin as KClass<T>
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("NMS class not found: $name", e)
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Error casting NMS class: $name", e)
        }
    }

    private fun <T : Any> getNMSInstance(klass: KClass<T>): T = klass.java.getDeclaredConstructor().newInstance()
}