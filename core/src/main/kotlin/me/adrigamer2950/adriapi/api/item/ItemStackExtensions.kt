@file:Suppress("unused")

package me.adrigamer2950.adriapi.api.item

import org.bukkit.inventory.ItemStack

fun ItemStack.toItemBuilder(): ItemBuilder = ItemBuilder.fromItemStack(this)