package me.devadri.obsidian.menu

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.ExperimentalAPI
import me.devadri.obsidian.inventory.Inventory
import me.devadri.obsidian.inventory.InventorySize
import me.devadri.obsidian.menu.button.MenuButton
import me.devadri.obsidian.persistence.DataTypes
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.InventoryClickEvent

@ExperimentalAPI
abstract class Menu protected constructor(
    title: Component, plugin: ObsidianPlugin, size: InventorySize = InventorySize.THREE_ROWS
) : Inventory(title, plugin, size) {

    protected val buttons: MutableList<MenuButton> = mutableListOf()

    open fun addButton(vararg buttonsArray: MenuButton) {
        buttonsArray.forEach { buttons.add(it) }
    }

    open fun removeButton(vararg buttonsArray: MenuButton) {
        buttonsArray.forEach {
            if (!buttons.contains(it)) return@forEach

            buttons.remove(it)
            inventory.setItem(it.coordinates.toSlot(), null)
        }
    }

    override fun setup() {
        for (button in buttons) {
            inventory.setItem(
                button.coordinates.toSlot(),
                button.item.addPersistentData(
                    NamespacedKey(plugin, "adriapi_menu_button_id"),
                    DataTypes.UUID, button.id
                ).build()
            )
        }
    }

    override fun onClick(e: InventoryClickEvent) {
        val inv = e.clickedInventory ?: return

        val stack = inv.getItem(e.slot) ?: return

        val meta = stack.itemMeta ?: return

        if (!meta.persistentDataContainer.has(NamespacedKey(plugin, "adriapi_menu_button_id"), DataTypes.UUID)) return

        val id = meta.persistentDataContainer.get(NamespacedKey(plugin, "adriapi_menu_button_id"), DataTypes.UUID)

        e.isCancelled = true

        buttons.firstOrNull { it.id == id }?.let {
            it.onClick(e, this, it)
        }
    }
}