package net.sinjs.heartsteal.heartsteal;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class PlayerKilledListener implements Listener {
    public final int HEART_HP = 2;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player k = p.getKiller();
        AttributeInstance max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        assert max_health != null;

        max_health.setBaseValue(max_health.getBaseValue() - this.HEART_HP);

        if (max_health.getBaseValue() - this.HEART_HP <= 0) {
            Bukkit.getLogger().info("Player " + p.getName() + " lost all of his hearts.");
            p.banPlayer("You lost all of your hearts!");
            return;
        }

        Bukkit.getLogger().info("Player " + p.getName() + " lost one heart.");

        if (k != null) {
            AttributeInstance killer_max_health = k.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert killer_max_health != null;

            Bukkit.getLogger().info("Player " + k.getName() + " got one heart.");

            killer_max_health.setBaseValue(killer_max_health.getBaseValue() + this.HEART_HP);
            k.sendMessage("§aYou just stole §c§l1 Heart§r§a from §e§l" + p.getName());
            p.sendMessage("§e§l" + k.getName() + "§r§a just stole §c§l1 Heart§r§a from you!");
        } else {
            p.sendMessage("§aYou just lost §c§l1 Heart§r§a!");
        }
    }
}
