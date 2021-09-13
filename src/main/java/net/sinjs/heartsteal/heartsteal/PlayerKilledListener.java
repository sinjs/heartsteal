package net.sinjs.heartsteal.heartsteal;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;


public class PlayerKilledListener implements Listener {
    public final int HEART_HP = 2;
    private final Plugin plugin;

    public PlayerKilledListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player k = p.getKiller();
        AttributeInstance max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        assert max_health != null;

        if (max_health.getBaseValue() - this.HEART_HP <= 0) {
            Bukkit.getLogger().info("Player " + p.getName() + " lost all of his hearts.");
            if (this.plugin.getConfig().getBoolean("no-hearts-ban")) {
                p.banPlayer("You lost all of your hearts!");
            } else {
                p.sendMessage("§c§lYou lost all of your hearts, you are now spectating the world.");
                p.setGameMode(GameMode.SPECTATOR);
            }
            return;
        }
        
        max_health.setBaseValue(max_health.getBaseValue() - this.HEART_HP);

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
