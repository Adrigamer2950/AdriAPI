package me.adrigamer2950.adriapi.api.menu.button

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.ExperimentalAPI
import me.adrigamer2950.adriapi.api.item.ItemBuilder
import me.adrigamer2950.adriapi.api.menu.Menu
import me.adrigamer2950.adriapi.api.menu.coords.Coordinates
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

@ExperimentalAPI
open class MenuButton(
    val plugin: APIPlugin,
    val item: ItemBuilder,
    val coordinates: Coordinates,
    val onClick: (InventoryClickEvent, Menu, MenuButton) -> Unit)
{
    val id: UUID = UUID.randomUUID()
}