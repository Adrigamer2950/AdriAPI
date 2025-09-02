package me.devadri.obsidian.inventory

import me.devadri.obsidian.ObsidianPlugin
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
    val plugin: ObsidianPlugin,
    val size: InventorySize = InventorySize.THREE_ROWS
) : InventoryHolder {

    val bukkitInventory: BukkitInventory = title?.let {
        Bukkit.createInventory(this, size.size, it)
    } ?: Bukkit.createInventory(this, size.size)

    override fun getInventory(): BukkitInventory = bukkitInventory

    /**
     * Setup inventory items
     * and open the inventory to the player
     *
     * @param player The player (duh)
     * @see setup
     */
    fun openFor(player: Player) {
        this.setup()

        player.openInventory(bukkitInventory)
    }

    /**
     * Setup items in the inventory or any other thing you may want to do
     */
    protected open fun setup() {}

    /**
     * Executed when a player clicks in the inventory
     *
     * @param e An InventoryClickEvent
     * @see InventoryClickEvent
     * @see InventoryClickEvent.getClickedInventory
     */
    open fun onClick(e: InventoryClickEvent) {}

    /**
     * @param e An InventoryCloseEvent
     * @see InventoryCloseEvent
     */
    open fun onClose(e: InventoryCloseEvent) {}

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder {
        var title: Component? = null
            private set

        var plugin: ObsidianPlugin? = null
            private set

        var size: InventorySize = InventorySize.THREE_ROWS
            private set

        var setup: Consumer<@NotNull Inventory>? = null
            private set

        var onClick: BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory>? = null
            private set

        var onClose: BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory>? = null
            private set

        fun title(title: Component) = apply { this.title = title }

        fun plugin(plugin: ObsidianPlugin) = apply { this.plugin = plugin }

        fun size(size: InventorySize) = apply { this.size = size }

        fun setup(setupInventory: Consumer<@NotNull Inventory>) =
            apply { this.setup = setupInventory }

        fun onClick(onClick: Consumer<@NotNull InventoryClickEvent>) =
            apply { this.onClick = BiConsumer { e, _ -> onClick.accept(e) } }

        fun onClick(onClick: BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory>) =
            apply { this.onClick = onClick }

        fun onClose(onClose: Consumer<@NotNull InventoryCloseEvent>) =
            apply { this.onClose = BiConsumer { e, _ -> onClose.accept(e) } }

        fun onClose(onInventoryClose: BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory>) =
            apply { this.onClose = onInventoryClose }

        fun build(): Inventory {
            if (plugin == null)
                throw IllegalArgumentException("Plugin cannot be null")

            return object : Inventory(title, plugin!!, size) {
                override fun setup() {
                    setup?.accept(this)
                }

                override fun onClick(e: InventoryClickEvent) {
                    onClick?.accept(e, this)
                }

                override fun onClose(e: InventoryCloseEvent) {
                    onClose?.accept(e, this)
                }
            }
        }
    }
}
