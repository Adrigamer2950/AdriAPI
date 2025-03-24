package me.adrigamer2950.adriapi.api.serializer.boostedyaml;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class ItemStackSerializer implements TypeAdapter<ItemStack> {

    @SuppressWarnings("DataFlowIssue")
    @Override
    public @NotNull Map<Object, Object> serialize(@NotNull ItemStack itemStack) {
        Map<Object, Object> map = new LinkedHashMap<>();

        map.put("material", itemStack.getType().name());
        map.put("count", itemStack.getAmount());

        if (itemStack.getItemMeta().hasDisplayName())
            map.put("name",
                    LegacyComponentSerializer.legacyAmpersand().serialize(itemStack.getItemMeta().displayName())
            );

        if (itemStack.getItemMeta().hasLore())
            map.put("lore", itemStack.getItemMeta().lore()
                    .stream().map(LegacyComponentSerializer.legacyAmpersand()::serialize));

        if (itemStack.getItemMeta().hasCustomModelData())
            map.put("custom_model_data", itemStack.getItemMeta().getCustomModelData());

        if (!itemStack.getEnchantments().isEmpty()) {
            Map<Object, Object> enchantments = new LinkedHashMap<>();

            for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                int level = itemStack.getEnchantments().get(enchantment);

                enchantments.put(enchantment.getKey().getKey(), level);
            }

            map.put("enchantments", enchantments);
        }

        if (!itemStack.getItemFlags().isEmpty()) {
            map.put("flags", itemStack.getItemFlags()
                    .stream().map(ItemFlag::name)
                    .toList()
            );
        }

        if (itemStack.getItemMeta().hasAttributeModifiers()) {
            List<Map<Object, Object>> attributes = new LinkedList<>();

            for (Attribute key : itemStack.getItemMeta().getAttributeModifiers().keySet()) {
                Map<Object, Object> attribute = new LinkedHashMap<>();

                Collection<AttributeModifier> modifiers = itemStack.getItemMeta().getAttributeModifiers().get(key);

                for (AttributeModifier modifier : modifiers) {
                    Map<Object, Object> modifierMap = new LinkedHashMap<>();

                    modifierMap.put("name", modifier.getName());
                    modifierMap.put("operation", modifier.getOperation().name());
                    modifierMap.put("amount", modifier.getAmount());

                    attribute.put(key.name(), modifierMap);
                }

                attributes.add(attribute);
            }

            map.put("attributes", attributes);
        }

        if (itemStack.getItemMeta().isUnbreakable())
            map.put("unbreakable", true);

        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull ItemStack deserialize(@NotNull Map<Object, Object> map) {
        Material mat = Material.getMaterial((String) map.get("material"));

        if (mat == null)
            throw new IllegalArgumentException("Material does not exist!");

        if (!map.containsKey("count"))
            throw new IllegalArgumentException("Count variable isn't set!");

        int count = (int) map.get("count");

        ItemStack stack = new ItemStack(mat, count);
        ItemMeta meta = stack.getItemMeta();

        if (map.containsKey("name"))
            meta.displayName(
                    LegacyComponentSerializer.legacyAmpersand().deserialize((String) map.get("name"))
            );

        if (map.containsKey("lore"))
            meta.lore(
                    ((List<String>) map.get("lore")).stream()
                            .map(s -> (Component) LegacyComponentSerializer.legacyAmpersand().deserialize(s))
                            .toList()
            );

        if (map.containsKey("custom_model_data"))
            meta.setCustomModelData(Integer.parseInt(map.get("custom_model_data").toString()));

        if (map.containsKey("enchantments")) {
            Map<String, Integer> enchantments = (Map<String, Integer>) map.get("enchantments");

            for (String key : enchantments.keySet()) {
                int level = enchantments.get(key);

                meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(key))), level, true);
            }
        }

        if (map.containsKey("flags"))
            for (String key : (List<String>) map.get("flags"))
                meta.addItemFlags(ItemFlag.valueOf(key));

        if (map.containsKey("attributes")) {
            List<Map<String, Map<String, Object>>> attributes = (List<Map<String, Map<String, Object>>>) map.get("attributes");

            for (Map<String, Map<String, Object>> attribute : attributes) {
                for (String key : attribute.keySet()) {
                    Map<String, Object> modifierMap = attribute.get(key);

                    String name = (String) modifierMap.get("name");
                    AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf((String) modifierMap.get("operation"));
                    double amount = (Double) modifierMap.get("amount");

                    AttributeModifier modifier = new AttributeModifier(name, amount, operation);
                    Attribute attr = Attribute.valueOf(key);

                    meta.addAttributeModifier(attr, modifier);
                }
            }
        }

        if (map.containsKey("unbreakable")) {
            meta.setUnbreakable((Boolean) map.get("unbreakable"));
        }

        stack.setItemMeta(meta);

        return stack;
    }
}
