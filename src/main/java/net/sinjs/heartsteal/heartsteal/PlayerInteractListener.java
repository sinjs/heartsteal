package net.sinjs.heartsteal.heartsteal;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {
    private final Plugin plugin;
    public final double HEART_HP = 2.0;

    public PlayerInteractListener(Plugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();

        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (i.getType() == Material.RED_DYE && i.getItemMeta().getCustomModelData() == 4450001) {
                if (!this.plugin.getConfig().getBoolean("hearts-enabled")) return;

                AttributeInstance max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                assert max_health != null;

                max_health.setBaseValue(max_health.getBaseValue() + this.HEART_HP);
                p.sendMessage("§aYou just added §c§l1 Heart§r§a to your health!");

                if (i.getAmount() == 1) p.getInventory().setItemInMainHand(null);
                else i.setAmount(i.getAmount() - 1);
            }
        }
    }
}
