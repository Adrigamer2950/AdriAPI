package me.adrigamer2950.adriapi.api.item

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

@Suppress("unused")
class ItemBuilder {

    companion object {
        @JvmStatic
        fun builder(): ItemBuilder = ItemBuilder()

        @JvmStatic
        fun fromItemStack(stack: ItemStack): ItemBuilder {
            val builder = builder()
                .material(stack.type)
                .amount(stack.amount)
                .name(
                    if (stack.itemMeta.hasDisplayName())
                        stack.itemMeta.displayName()
                    else
                        null
                )
                .lore(
                    if (stack.itemMeta.hasLore())
                        stack.itemMeta.lore() as List<Component>
                    else
                        listOfNotNull<Component>()
                )
                .customModelData(stack.itemMeta.customModelData)
                .unbreakable(stack.itemMeta.isUnbreakable)

            stack.itemMeta.enchants.forEach {
                builder.addEnchantment(it.key, it.value)
            }

            stack.itemMeta.itemFlags.forEach {
                builder.addItemFlag(it)
            }

            if (stack.itemMeta.hasAttributeModifiers())
                stack.itemMeta.attributeModifiers!!.entries().forEach {
                    builder.addAttributeModifier(it.key, it.value)
                }

            return builder
        }
    }

    var material: Material? = null
        private set

    var amount: Int = 1
        private set

    var name: Component? = null

    val lore: MutableList<Component> = mutableListOf()

    var customModelData: Int = 0

    val enchantments: MutableMap<Enchantment, Int> = mutableMapOf()

    val flags: MutableSet<ItemFlag> = mutableSetOf()

    val attributes: Multimap<Attribute, AttributeModifier> = HashMultimap.create()

    var unbreakable: Boolean = false

    fun material(material: Material): ItemBuilder {
        this.material = material
        return this
    }

    fun amount(amount: Int): ItemBuilder {
        this.amount = amount
        return this
    }

    fun name(name: Component?): ItemBuilder {
        this.name = name
        return this
    }

    fun lore(lore: List<Component>): ItemBuilder {
        this.lore.addAll(lore)
        return this
    }

    fun customModelData(customModelData: Int): ItemBuilder {
        this.customModelData = customModelData
        return this
    }

    fun addEnchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        this.enchantments[enchantment] = level
        return this
    }

    fun addItemFlag(flag: ItemFlag): ItemBuilder {
        this.flags.add(flag)
        return this
    }

    fun addAttributeModifier(attribute: Attribute, modifier: AttributeModifier): ItemBuilder {
        this.attributes.put(attribute, modifier)
        return this
    }

    fun unbreakable(unbreakable: Boolean): ItemBuilder {
        this.unbreakable = unbreakable
        return this
    }

    fun build(): ItemStack {
        if (this.material == null)
            throw IllegalArgumentException("Material cannot be null")

        val stack = ItemStack(this.material!!, this.amount)
        val meta = stack.itemMeta

        if (name != null)
            meta.displayName(name)

        if (lore.isNotEmpty())
            meta.lore(lore)

        meta.setCustomModelData(this.customModelData)

        this.enchantments.entries.forEach {
            meta.addEnchant(it.key, it.value, true)
        }

        this.flags.forEach {
            meta.addItemFlags(it)
        }

        meta.attributeModifiers = this.attributes

        meta.isUnbreakable = this.unbreakable

        stack.itemMeta = meta

        return stack
    }
}
