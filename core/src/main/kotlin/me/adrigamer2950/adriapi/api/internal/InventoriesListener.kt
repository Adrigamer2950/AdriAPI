package me.adrigamer2950.adriapi.api.internal

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.inventory.Inventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
@Suppress("unused")
class InventoriesListener(val plugin: APIPlugin) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        if (inventory.plugin != plugin) return

        inventory.onClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val inventory = event.inventory.holder as? Inventory ?: return

        if (inventory.plugin != plugin) return

        inventory.onClose(event)
    }
}