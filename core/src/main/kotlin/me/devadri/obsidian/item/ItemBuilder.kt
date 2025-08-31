package me.devadri.obsidian.item

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.function.Consumer

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
                        listOf()
                )
                .customModelData(
                    if (stack.itemMeta.hasCustomModelData())
                        stack.itemMeta.customModelData
                    else
                        0
                )
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

    val persistentData: MutableList<Consumer<ItemMeta>> = mutableListOf()

    fun material(material: Material): ItemBuilder = apply { this.material = material }

    fun amount(amount: Int): ItemBuilder = apply { this.amount = amount }

    fun name(name: Component?): ItemBuilder = apply { this.name = name }

    fun lore(lore: List<Component>): ItemBuilder = apply { this.lore.addAll(lore) }

    fun customModelData(customModelData: Int): ItemBuilder = apply { this.customModelData = customModelData }

    fun addEnchantment(enchantment: Enchantment, level: Int): ItemBuilder = apply { this.enchantments[enchantment] = level }

    fun addItemFlag(flag: ItemFlag): ItemBuilder = apply { this.flags.add(flag) }

    fun addAttributeModifier(attribute: Attribute, modifier: AttributeModifier): ItemBuilder = apply { this.attributes.put(attribute, modifier) }

    fun unbreakable(unbreakable: Boolean): ItemBuilder = apply { this.unbreakable = unbreakable }

    fun <T, Z : Any> addPersistentData(namespacedKey: NamespacedKey, type: PersistentDataType<T, Z>, obj: Z): ItemBuilder = apply {
        this.persistentData.add {
            it.persistentDataContainer.set(namespacedKey, type, obj)
        }
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

        this.persistentData.forEach {
            it.accept(meta)
        }

        stack.itemMeta = meta

        return stack
    }
}
