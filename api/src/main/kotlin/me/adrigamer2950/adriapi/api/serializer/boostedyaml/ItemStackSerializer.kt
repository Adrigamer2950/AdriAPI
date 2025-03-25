package me.adrigamer2950.adriapi.api.serializer.boostedyaml

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

import java.util.*

@Suppress("unused")
class ItemStackSerializer : TypeAdapter<ItemStack> {

    override fun serialize(itemStack: ItemStack): Map<Any, Any> {
        val map = LinkedHashMap<Any, Any>()

        map.put("material", itemStack.type.name)
        map.put("count", itemStack.amount)

        if (itemStack.itemMeta.hasDisplayName())
            map.put(
                "name",
                LegacyComponentSerializer.legacyAmpersand().serialize(itemStack.itemMeta.displayName()!!)
            )

        if (itemStack.itemMeta.hasLore())
            map.put(
                "lore", itemStack.itemMeta.lore()!!
                    .stream().map(LegacyComponentSerializer.legacyAmpersand()::serialize)
            )

        if (itemStack.itemMeta.hasCustomModelData())
            map.put("custom_model_data", itemStack.itemMeta.customModelData)

        if (!itemStack.enchantments.isEmpty()) {
            val enchantments: MutableMap<Any, Any> = LinkedHashMap<Any, Any>()

            itemStack.enchantments.keys.forEach {
                val level: Any = itemStack.enchantments[it] as Any

                enchantments.put(it.key.key, level)
            }

            map.put("enchantments", enchantments)
        }

        if (!itemStack.itemFlags.isEmpty()) {
            map.put(
                "flags", itemStack.itemFlags
                    .stream().map(ItemFlag::name)
                    .toList()
            )
        }

        if (itemStack.itemMeta.hasAttributeModifiers()) {
            val attributes: MutableList<Map<Any, Any>> = LinkedList<Map<Any, Any>>()

            itemStack.itemMeta.attributeModifiers?.keySet()?.forEach {
                val attribute: MutableMap<Any, Any> = LinkedHashMap<Any, Any>()

                val modifiers: Collection<AttributeModifier?>? = itemStack.itemMeta.attributeModifiers?.get(it)

                modifiers?.forEach {
                    val modifierMap: MutableMap<Any, Any> = LinkedHashMap<Any, Any>()

                    modifierMap.put("name", it!!.name)
                    modifierMap.put("operation", it.operation.name)
                    modifierMap.put("amount", it.amount)

                    attribute.put(it.name, modifierMap)
                }

                attributes.add(attribute)
            }

            map.put("attributes", attributes)
        }

        if (itemStack.itemMeta.isUnbreakable)
            map.put("unbreakable", true)

        return map
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(map: Map<in Any, Any?>): ItemStack {
        val mat = Material.getMaterial(map["material"] as String)

        if (mat == null)
            throw IllegalArgumentException("Material does not exist!")

        if (!map.containsKey("count"))
            throw IllegalArgumentException("Count variable isn't set!")

        val count = map["count"] as Int

        val stack = ItemStack(mat, count)
        val meta = stack.itemMeta

        if (map.containsKey("name"))
            meta.displayName(
                LegacyComponentSerializer.legacyAmpersand().deserialize(map["name"] as String)
            )

        if (map.containsKey("lore"))
            meta.lore(
                (map["lore"] as List<String>).stream()
                    .map { LegacyComponentSerializer.legacyAmpersand().deserialize(it) as Component }
                    .toList()
            )

        if (map.containsKey("custom_model_data"))
            meta.setCustomModelData(map["custom_model_data"] as Int)

        if (map.containsKey("enchantments")) {
            val enchantments = map["enchantments"] as Map<String, Int>

            for (key in enchantments.keys) {
                val level = enchantments[key] as Int

                meta.addEnchant(
                    Enchantment.getByKey(NamespacedKey.minecraft(key))!!,
                    level,
                    true
                )
            }
        }

        if (map.containsKey("flags"))
            for (key in map["flags"] as List<String>)
                meta.addItemFlags(ItemFlag.valueOf(key))

        if (map.containsKey("attributes")) {
            val attributes = map["attributes"] as List<Map<String, Map<String, Any>>>

            for (attribute in attributes) {
                for (key in attribute.keys) {
                    val modifierMap = attribute[key]

                    val name = modifierMap!!["name"] as String
                    val operation = AttributeModifier.Operation.valueOf(modifierMap["operation"] as String)
                    val amount = modifierMap["amount"] as Double

                    val modifier = AttributeModifier(name, amount, operation)
                    val attr = Attribute.valueOf(key)

                    meta.addAttributeModifier(attr, modifier)
                }
            }
        }

        if (map.containsKey("unbreakable"))
            meta.isUnbreakable = map["unbreakable"] as Boolean

        stack.itemMeta = meta

        return stack
    }
}
