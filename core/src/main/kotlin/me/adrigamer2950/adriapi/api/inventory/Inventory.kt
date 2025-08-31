package me.adrigamer2950.adriapi.api.inventory

import me.adrigamer2950.adriapi.api.APIPlugin
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder
import org.jetbrains.annotations.NotNull
import java.util.function.BiConsumer
import java.util.function.Consumer

typealias BukkitInventory = org.bukkit.inventory.Inventory

/**
 * Create inventories
 *
 * @since 2.1.0
 */
@Suppress("unused")
abstract class Inventory(
    title: Component? = null,
    val plugin: APIPlugin,
    val size: InventorySize = InventorySize.THREE_ROWS
) : InventoryHolder {

    val bukkitInventory = title?.let {
        Bukkit.createInventory(this, size.size, it)
    } ?: Bukkit.createInventory(this, size.size)

    override fun getInventory(): BukkitInventory = bukkitInventory

    /**
     * Setup inventory items
     * and open the inventory to the player
     *
     * @param player The player (duh)
     * @see setupInventory
     */
    fun openFor(player: Player) {
        this.setupInventory()

        player.openInventory(bukkitInventory)
    }

    /**
     * Setup items in the inventory or any other thing you may want to do
     */
    protected open fun setupInventory() {}

    /**
     * Executed when a player clicks in the inventory
     *
     * @param e An InventoryClickEvent.
     *          Null check on InventoryClickEvent#getClickedInventory()
     *          is unnecessary as it is checked before executing this method
     * @see InventoryClickEvent
     * @see InventoryClickEvent.getClickedInventory
     */
    open fun onInventoryClick(e: InventoryClickEvent) {}

    /**
     * @param e An InventoryCloseEvent
     * @see InventoryCloseEvent
     */
    open fun onInventoryClose(e: InventoryCloseEvent) {}

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder {
        var title: Component? = null
            private set

        var plugin: APIPlugin? = null
            private set

        var size: InventorySize = InventorySize.THREE_ROWS
            private set

        var setupInventory: Consumer<@NotNull Inventory>? = null
            private set

        var onInventoryClick: BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory>? = null
            private set

        var onInventoryClose: BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory>? = null
            private set

        fun title(title: Component) = apply { this.title = title }

        fun plugin(plugin: APIPlugin) = apply { this.plugin = plugin }

        fun size(size: InventorySize) = apply { this.size = size }

        fun setupInventory(setupInventory: Consumer<@NotNull Inventory>) =
            apply { this.setupInventory = setupInventory }

        fun onInventoryClick(onInventoryClick: Consumer<@NotNull InventoryClickEvent>) =
            apply { this.onInventoryClick = BiConsumer { e, _ -> onInventoryClick.accept(e) } }

        fun onInventoryClick(onInventoryClick: BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory>) =
            apply { this.onInventoryClick = onInventoryClick }

        fun onInventoryClose(onInventoryClose: Consumer<@NotNull InventoryCloseEvent>) =
            apply { this.onInventoryClose = BiConsumer { e, _ -> onInventoryClose.accept(e) } }

        fun onInventoryClose(onInventoryClose: BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory>) =
            apply { this.onInventoryClose = onInventoryClose }

        fun build(): Inventory {
            if (plugin == null)
                throw IllegalArgumentException("Plugin cannot be null")

            return object : Inventory(title, plugin!!, size) {
                override fun setupInventory() {
                    setupInventory?.accept(this)
                }

                override fun onInventoryClick(e: InventoryClickEvent) {
                    onInventoryClick?.accept(e, this)
                }

                override fun onInventoryClose(e: InventoryCloseEvent) {
                    onInventoryClose?.accept(e, this)
                }
            }
        }
    }
}
