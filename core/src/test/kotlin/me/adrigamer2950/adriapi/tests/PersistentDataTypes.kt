package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.api.item.ItemBuilder
import me.adrigamer2950.adriapi.api.persistence.DataTypes
import me.adrigamer2950.adriapi.api.persistence.GsonDataType
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import org.bukkit.Material
import org.bukkit.NamespacedKey
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PersistentDataTypes : AbstractTestPlatform() {

    @Test
    fun `UUID Data Type`() {
        val uuid = UUID.randomUUID()
        val key = NamespacedKey(plugin, "data_type_test")

        val stack = ItemBuilder.builder()
            .material(Material.DIRT)
            .addPersistentData(key, DataTypes.UUID, uuid)
            .build()

        assertEquals(uuid, stack.itemMeta.persistentDataContainer.get(key, DataTypes.UUID), "UUID data type test failed")
    }

    @Test
    fun `Boolean Data Type`() {
        val bool = false
        val key = NamespacedKey(plugin, "data_type_test")

        val stack = ItemBuilder.builder()
            .material(Material.DIRT)
            .addPersistentData(key, DataTypes.BOOLEAN, bool)
            .build()

        assertEquals(bool, stack.itemMeta.persistentDataContainer.get(key, DataTypes.BOOLEAN), "Boolean data type test failed")
    }

    @Test
    fun `Gson Data Type`() {
        val testClass = GsonTestClass("Adri", 25)
        val gson = GsonDataType(GsonTestClass::class)
        val key = NamespacedKey(plugin, "data_type_test")

        val stack = ItemBuilder.builder()
            .material(Material.DIRT)
            .addPersistentData(key, gson, testClass)
            .build()

        assertEquals(testClass, stack.itemMeta.persistentDataContainer.get(key, gson), "Gson data type test failed")
    }
}

data class GsonTestClass(val name: String, val age: Int)