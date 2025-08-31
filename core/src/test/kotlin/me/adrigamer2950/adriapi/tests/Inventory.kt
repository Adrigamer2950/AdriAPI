package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.api.inventory.Inventory
import me.adrigamer2950.adriapi.api.inventory.InventorySize
import me.adrigamer2950.adriapi.api.item.ItemBuilder
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import org.bukkit.Material
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertNotNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Inventory : AbstractTestPlatform() {

    companion object {

        lateinit var inventory: Inventory

        @JvmStatic
        @BeforeAll
        fun `Create Inventory`() {
            inventory = object : Inventory(plugin = plugin, size = InventorySize.SIX_ROWS) {
                init {
                    setup()
                }

                override fun setup() {
                    val item = ItemBuilder.builder()
                        .material(Material.STONE)
                        .build()

                    this.inventory.setItem(6, item)
                }
            }
        }
    }

    @Test
    fun `Setup Inventory`() {
        val item = inventory.inventory.getItem(6)

        assertNotNull(item, "Item should not be null")
        assertNotEquals(Material.AIR, item.type, "Item should not be AIR")
        assertEquals(Material.STONE, item.type, "Item should be STONE")
    }
}