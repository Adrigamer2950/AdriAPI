package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.api.item.ItemBuilder
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ItemBuilder : AbstractTestPlatform() {

    @Test
    fun `Item Builder`() {
        val material = Material.DIAMOND
        val amount = 3
        val name = Component.text("Test Item", Style.style(NamedTextColor.GREEN))
        val lore = listOf(
            "&aHey!",
            "&bThis is a test item"
        )
            .map { LegacyComponentSerializer.legacyAmpersand().deserialize(it) }
        val enchantment = Enchantment.ARROW_FIRE
        val enchantmentLevel = 3
        val flag = ItemFlag.HIDE_ENCHANTS
        val attribute = Attribute.GENERIC_LUCK
        val attributeModifier = AttributeModifier(
            "attribute_modifier",
            1.0,
            AttributeModifier.Operation.ADD_NUMBER
        )

        val stack = ItemBuilder.builder()
            .material(material)
            .amount(amount)
            .name(name)
            .lore(lore)
            .addEnchantment(enchantment, enchantmentLevel)
            .addItemFlag(flag)
            .addAttributeModifier(attribute, attributeModifier)
            .build()

        assertEquals(material, stack.type, "Material mismatch")
        assertEquals(amount, stack.amount, "Amount mismatch")
        assertEquals(name, stack.itemMeta.displayName(), "Name mismatch")
        assertEquals(lore, stack.itemMeta.lore(), "Lore mismatch")
        assertTrue(stack.itemMeta.hasEnchant(enchantment), "Enchantment mismatch")
        assertEquals(enchantmentLevel, stack.itemMeta.getEnchantLevel(enchantment), "Enchantment level mismatch")
        assertTrue(stack.itemMeta.hasItemFlag(flag), "Item flag mismatch")
        assertTrue(stack.itemMeta.hasAttributeModifiers(), "Item should have attributes")
        assertNotNull(stack.itemMeta.attributeModifiers?.get(attribute), "Attribute modifier mismatch")
        assertEquals(attributeModifier, stack.itemMeta.attributeModifiers?.get(attribute)?.firstOrNull(), "Attribute modifier mismatch")
    }
}