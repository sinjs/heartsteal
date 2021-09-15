package net.sinjs.heartsteal.heartsteal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class HeartCraftingRecipe {
    public static void addRecipe(Plugin plugin) {
        if (!plugin.getConfig().getBoolean("enable-hearts")) {
            Bukkit.getLogger().info("Not registering HeartCrafting recipe, enable-hearts is false.");
            return;
        }

        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§c§lHeart");
        meta.setCustomModelData(4450001);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        NamespacedKey key = new NamespacedKey(plugin, "heart");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("RDR", "BDB", "RDR");

        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('B', Material.REDSTONE_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND);

        Bukkit.addRecipe(recipe);
    }
}
