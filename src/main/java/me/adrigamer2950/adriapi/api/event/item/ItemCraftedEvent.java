package me.adrigamer2950.adriapi.api.event.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This event will be triggered when a {@link org.bukkit.entity.Player} crafts an {@link org.bukkit.inventory.ItemStack} of any type in a Crafting Table.
 */
public class ItemCraftedEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private ItemStack item;
    private Player player;
    private Inventory inventory;

    private boolean cancelled;

    private String denyMessage;

    public ItemCraftedEvent(ItemStack item, Player player, Inventory inventory) {
        this.item = item;
        this.player = player;
        this.inventory = inventory;

        this.denyMessage = null;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public void setDenyMessage(String denyMessage) {
        this.denyMessage = denyMessage;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
