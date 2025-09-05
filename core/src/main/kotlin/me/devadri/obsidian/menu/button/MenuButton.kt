package me.devadri.obsidian.menu.button

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.item.ItemBuilder
import me.devadri.obsidian.menu.Menu
import me.devadri.obsidian.menu.coords.Coordinates
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

open class MenuButton(
    val plugin: ObsidianPlugin,
    val item: ItemBuilder,
    val coordinates: Coordinates,
    val onClick: (InventoryClickEvent, Menu, MenuButton) -> Unit = { _, _, _ -> }
) {
    val id: UUID = UUID.randomUUID()
}