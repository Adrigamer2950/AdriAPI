package me.devadri.obsidian.tests

import me.devadri.obsidian.item.ItemBuilder
import me.devadri.obsidian.platform.AbstractTestPlatform
import me.devadri.obsidian.serializer.boostedyaml.ItemStackSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.junit.jupiter.api.Order
import kotlin.test.Test
import kotlin.test.assertEquals

class BoostedYamlSerializers : AbstractTestPlatform() {

    companion object {
        lateinit var serializedStack: Map<in Any, Any?>
    }

    @Test
    @Order(1)
    fun `Serialize ItemStack`() {
        val item = ItemBuilder.builder()
            .material(Material.DIAMOND)
            .amount(3)
            .name(Component.text("Test Item", NamedTextColor.BLUE))
            .lore(
                listOf(Component.text("test line"))
            )
            .addAttributeModifier(
                Attribute.GENERIC_MAX_HEALTH,
                AttributeModifier("test", 1.0, AttributeModifier.Operation.ADD_NUMBER)
            )
            .addItemFlag(ItemFlag.HIDE_ENCHANTS)
            .addEnchantment(Enchantment.THORNS, 1)
            .unbreakable(true)
            .customModelData(3)
            .build()

        val map = ItemStackSerializer().serialize(item)

        assertEquals("DIAMOND", map["material"])
        assertEquals(3, map["count"])
        assertEquals("&9Test Item", map["name"])
        assertEquals("test line", (map["lore"] as List<*>)[0])

        val attribute = (map["attributes"] as List<*>)[0] as Map<*, *>
        assertEquals("GENERIC_MAX_HEALTH", attribute["name"])

        val modifiers = attribute["modifiers"] as List<*>
        assertEquals(1, modifiers.size)
        assertEquals("test", (modifiers[0] as Map<*, *>)["name"])
        assertEquals(1.0, (modifiers[0] as Map<*, *>)["amount"])
        assertEquals("ADD_NUMBER", (modifiers[0] as Map<*, *>)["operation"])

        assertEquals("HIDE_ENCHANTS", (map["flags"] as List<*>)[0])
        assertEquals(1, (map["enchantments"] as Map<*, *>)["thorns"])
        assertEquals(true, map["unbreakable"])
        assertEquals(3, map["custom_model_data"])

        serializedStack = map
    }

    @Test
    @Order(2)
    fun `Deserialize ItemStack`() {
        val item = ItemStackSerializer().deserialize(serializedStack)

        assertEquals("DIAMOND", item.type.name)
        assertEquals(3, item.amount)
        assertEquals("&9Test Item", LegacyComponentSerializer.legacyAmpersand().serialize(item.itemMeta.displayName()!!))
        assertEquals("test line", LegacyComponentSerializer.legacyAmpersand().serialize(item.itemMeta.lore()!![0]))
        assertEquals(1, item.itemMeta.attributeModifiers!![Attribute.GENERIC_MAX_HEALTH].size)
        assertEquals("test", item.itemMeta.attributeModifiers!![Attribute.GENERIC_MAX_HEALTH].first().name)
        assertEquals(1.0, item.itemMeta.attributeModifiers!![Attribute.GENERIC_MAX_HEALTH].first().amount)
        assertEquals(AttributeModifier.Operation.ADD_NUMBER, item.itemMeta.attributeModifiers!![Attribute.GENERIC_MAX_HEALTH].first().operation)
        assertEquals(true, item.itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS))
        assertEquals(1, item.itemMeta.enchants[Enchantment.THORNS])
        assertEquals(true, item.itemMeta.isUnbreakable)
        assertEquals(3, item.itemMeta.customModelData)
    }
}