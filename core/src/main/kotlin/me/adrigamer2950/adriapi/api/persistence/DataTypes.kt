@file:Suppress("unused", "PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package me.adrigamer2950.adriapi.api.persistence

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.reflect.KClass

object DataTypes {

    @JvmField
    val UUID: PersistentDataType<String, UUID> = UUIDDataType()

    @JvmField
    val BOOLEAN: PersistentDataType<java.lang.Byte, Boolean> = BooleanDataType()
}

class BooleanDataType : PersistentDataType<java.lang.Byte, Boolean> {
    override fun getPrimitiveType(): Class<java.lang.Byte> = java.lang.Byte::class.java

    override fun getComplexType(): Class<Boolean> = Boolean::class.java

    override fun toPrimitive(bool: Boolean, p1: PersistentDataAdapterContext): java.lang.Byte =
        (if (bool) java.lang.Byte.valueOf(1.toByte()) else java.lang.Byte.valueOf(0.toByte())) as java.lang.Byte

    override fun fromPrimitive(
        byte: java.lang.Byte,
        p1: PersistentDataAdapterContext,
    ): Boolean = byte.toByte() == 1.toByte()
}

class UUIDDataType : PersistentDataType<String, UUID> {

    override fun getPrimitiveType(): Class<String> = String::class.java

    override fun getComplexType(): Class<UUID> = UUID::class.java

    override fun toPrimitive(
        uuid: UUID,
        p1: PersistentDataAdapterContext,
    ): String = uuid.toString()

    override fun fromPrimitive(
        str: String,
        p1: PersistentDataAdapterContext,
    ): UUID = UUID.fromString(str)
}

data class GsonDataType<T : Any>(val clazz: Class<T>, val gson: Gson = Gson()) : PersistentDataType<String, T> {

    constructor(clazz: KClass<T>, gson: Gson = Gson()) : this(clazz.java, gson)

    constructor(clazz: Class<T>, adapter: TypeAdapter<T>? = null) : this(clazz, GsonBuilder().registerTypeAdapter(clazz, adapter).create())

    constructor(clazz: KClass<T>, adapter: TypeAdapter<T>? = null) : this(clazz.java, adapter)

    override fun getPrimitiveType(): Class<String> = String::class.java

    override fun getComplexType(): Class<T> = clazz

    override fun toPrimitive(
        complex: T,
        context: PersistentDataAdapterContext,
    ): String = gson.toJson(complex)

    override fun fromPrimitive(
        primitive: String,
        context: PersistentDataAdapterContext,
    ): T {
        try {
            return gson.fromJson(primitive, clazz)
        } catch (e: Exception) {
            throw RuntimeException("Failed to deserialize JSON", e)
        }
    }
}
