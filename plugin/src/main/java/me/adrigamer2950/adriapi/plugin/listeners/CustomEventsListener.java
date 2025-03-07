package me.adrigamer2950.adriapi.plugin.listeners;

import me.adrigamer2950.adriapi.api.event.item.ItemCraftedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class CustomEventsListener implements Listener {

    @EventHandler
    public void onItemCrafted(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().getType().equals(InventoryType.CRAFTING)) return;
        if (!e.getSlotType().equals(InventoryType.SlotType.RESULT)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        ItemCraftedEvent event = new ItemCraftedEvent(e.getCurrentItem(), (Player) e.getWhoClicked(), e.getInventory());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
