package me.adrigamer2950.adriapi.api.event.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This event will be triggered when a {@link org.bukkit.entity.Player} crafts an {@link org.bukkit.inventory.ItemStack} of any type in a Crafting Table.
 */
@Getter
@RequiredArgsConstructor
public class ItemCraftedEvent extends Event implements Cancellable {
    private final HandlerList handlers = new HandlerList();

    private final ItemStack item;
    private final Player player;
    private final Inventory inventory;

    @Setter
    private boolean cancelled;
}
