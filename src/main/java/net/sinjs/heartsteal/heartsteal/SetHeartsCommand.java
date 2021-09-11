package net.sinjs.heartsteal.heartsteal;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHeartsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c§lError: §r§eIncorrect arguments provided, usage: " + command.getUsage());
            return true;
        }

        String p_name = args[0];
        String hearts = args[1];

        Player p = sender.getServer().getPlayer(p_name);

        if (p == null) {
            sender.sendMessage("§c§lError: §r§ePlayer is not online or does not exist");
            return true;
        }

        AttributeInstance max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert max_health != null;

        double hearts_double = Double.parseDouble(hearts);
        max_health.setBaseValue(hearts_double * 2.0);
        p.setHealth(hearts_double * 2.0);

        sender.sendMessage("§aThe player §c§l" + p.getName() + "§r§a now has §c§l" + hearts + " Hearts§r§a.");

        return true;
    }
}
