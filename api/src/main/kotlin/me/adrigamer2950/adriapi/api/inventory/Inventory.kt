package me.adrigamer2950.adriapi.api.inventory

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.user.User
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder
import org.jetbrains.annotations.NotNull
import java.util.function.BiConsumer
import java.util.function.Consumer
import org.bukkit.inventory.Inventory as BukkitInventory

/**
 * Create inventories
 *
 * @since 2.1.0
 */
@Suppress("unused")
abstract class Inventory(
    val user: User,
    title: Component? = null,
    val plugin: APIPlugin,
    size: Int = InventorySize.THREE_ROWS.size
) : InventoryHolder {

    constructor(
        user: User,
        title: Component? = null,
        plugin: APIPlugin,
        size: InventorySize = InventorySize.THREE_ROWS
    ) : this(user, title, plugin, size.size)

    val bukkitInventory: BukkitInventory

    override fun getInventory(): BukkitInventory = bukkitInventory

    init {
        if (!user.isPlayer())
            throw IllegalArgumentException("User must be a player!")

        bukkitInventory = title?.let {
            Bukkit.createInventory(this, size, it)
        } ?: Bukkit.createInventory(this, size)
    }

    /**
     * Setup inventory items ({@link #setupInventory()})
     * and open the inventory to the player
     */
    fun openInventory() {
        this.setupInventory()

        //noinspection DataFlowIssue
        this.user.getPlayerOrNull()?.openInventory(this.getInventory())
    }

    /**
     * Setup items in the inventory or any other thing you may want to do
     */
    protected open fun setupInventory() {}

    /**
     * Executed when a player clicks in the inventory
     *
     * @param e An InventoryClickEvent.
     *          Null check on {@link InventoryClickEvent#getClickedInventory()}
     *          is unnecessary as it is checked before executing this method
     * @see InventoryClickEvent
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
        var user: User? = null
            private set

        var title: Component? = null
            private set

        var plugin: APIPlugin? = null
            private set

        var size: Int = InventorySize.THREE_ROWS.size
            private set

        var setupInventory: Consumer<@NotNull Inventory>? = null
            private set

        var onInventoryClick: BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory>? = null
            private set

        var onInventoryClose: BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory>? = null
            private set

        fun user(user: User) = apply { this.user = user }

        fun title(title: Component) = apply { this.title = title }

        fun plugin(plugin: APIPlugin) = apply { this.plugin = plugin }

        fun size(size: InventorySize) = apply { this.size = size.size }

        fun size(size: Int) = apply { this.size = size }

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
            if (user == null)
                throw IllegalArgumentException("User cannot be null")

            if (plugin == null)
                throw IllegalArgumentException("Plugin cannot be null")

            return object : Inventory(user!!, title, plugin!!, size) {
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
