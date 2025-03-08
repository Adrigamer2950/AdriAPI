package me.adrigamer2950.adriapi.plugin.listeners;

import me.adrigamer2950.adriapi.api.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoriesListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;

        if (!(e.getClickedInventory().getHolder() instanceof Inventory inv))
            return;

        inv.onInventoryClick(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof Inventory inv))
            return;

        inv.onInventoryClose(e);
    }
}
