@file:Suppress("unused")

package me.devadri.obsidian.item

import org.bukkit.inventory.ItemStack

fun ItemStack.toItemBuilder(): ItemBuilder = ItemBuilder.fromItemStack(this)