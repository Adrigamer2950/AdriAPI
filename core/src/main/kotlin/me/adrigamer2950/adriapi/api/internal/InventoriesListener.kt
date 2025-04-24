package me.adrigamer2950.adriapi.api.internal

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.inventory.Inventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoriesListener(val plugin: APIPlugin) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        if (inventory.plugin != plugin) return

        inventory.onInventoryClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        if (inventory.plugin != plugin) return

        inventory.onInventoryClose(event)
    }
}