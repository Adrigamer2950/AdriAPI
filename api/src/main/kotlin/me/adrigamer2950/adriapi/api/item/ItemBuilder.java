package me.adrigamer2950.adriapi.api.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings({"unused", "UnusedReturnValue"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemBuilder {

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static ItemBuilder fromItemStack(@NotNull @NonNull ItemStack stack) {
        ItemBuilder builder = ItemBuilder.builder()
                .material(stack.getType())
                .amount(stack.getAmount())
                .name(stack.getItemMeta().hasDisplayName() ? stack.getItemMeta().displayName() : null)
                .lore(stack.getItemMeta().hasLore() ? stack.getItemMeta().lore() : List.of())
                .customModelData(stack.getItemMeta().getCustomModelData())
                .unbreakable(stack.getItemMeta().isUnbreakable());

        for (Map.Entry<Enchantment, Integer> entry : stack.getEnchantments().entrySet())
            builder.addEnchantment(entry.getKey(), entry.getValue());

        for (ItemFlag flag : stack.getItemFlags())
            builder.addItemFlag(flag);

        if (stack.getItemMeta().hasAttributeModifiers())
            //noinspection DataFlowIssue
            for (Map.Entry<Attribute, AttributeModifier> entry : stack.getItemMeta().getAttributeModifiers().entries())
                builder.addAttributeModifier(entry.getKey(), entry.getValue());

        return builder;
    }

    Material material;
    int amount = 1;
    Component name;
    List<Component> lore = new ArrayList<>();
    int customModelData = 0;
    final HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    final Set<ItemFlag> flags = new HashSet<>();
    final Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
    boolean unbreakable = false;

    public Material material() {
        return this.material;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public int amount() {
        return this.amount;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public Component name() {
        return this.name;
    }

    public ItemBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public List<Component> lore() {
        return this.lore;
    }

    public ItemBuilder lore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public int customModelData() {
        return this.customModelData;
    }

    public ItemBuilder customModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public HashMap<Enchantment, Integer> enchantments() {
        return this.enchantments;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public Set<ItemFlag> flags() {
        return this.flags;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public Multimap<Attribute, AttributeModifier> attributes() {
        return this.attributes;
    }

    public ItemBuilder addAttributeModifier(Attribute attr, AttributeModifier mod) {
        this.attributes.put(attr, mod);
        return this;
    }

    public boolean unbreakable() {
        return this.unbreakable;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemStack build() {
        ItemStack stack = new ItemStack(this.material(), this.amount());
        ItemMeta meta = stack.getItemMeta();

        if (name != null)
            meta.displayName(this.name());

        if (lore.isEmpty())
            meta.lore(this.lore());

        meta.setCustomModelData(this.customModelData());

        for (Map.Entry<Enchantment, Integer> entry : this.enchantments().entrySet())
            meta.addEnchant(entry.getKey(), entry.getValue(), true);

        for (ItemFlag flag : this.flags())
            meta.addItemFlags(flag);

        meta.setAttributeModifiers(this.attributes());

        meta.setUnbreakable(this.unbreakable());

        stack.setItemMeta(meta);

        return stack;
    }
}
