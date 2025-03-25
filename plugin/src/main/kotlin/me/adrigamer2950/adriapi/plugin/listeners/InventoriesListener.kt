package me.adrigamer2950.adriapi.plugin.listeners

import me.adrigamer2950.adriapi.api.inventory.Inventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoriesListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        inventory.onInventoryClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        inventory.onInventoryClose(event)
    }
}
