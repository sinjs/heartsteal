package net.sinjs.heartsteal.heartsteal;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("no-hearts-ban", true);
        config.addDefault("enable-hearts", true);
        config.options().copyDefaults(true);
        saveConfig();

        if (!getServer().getVersion().contains("Paper")) {
            getLogger().severe("******************************************************");
            getLogger().severe("* Please use Paper instead of Spigot or CraftBukkit. *");
            getLogger().severe("* Your server software is not supported.             *");
            getLogger().severe("******************************************************");
        }

        getServer().getPluginManager().registerEvents(new PlayerKilledListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        this.getCommand("set-hearts").setExecutor(new SetHeartsCommand());

        HeartCraftingRecipe.addRecipe(this);
    }

    @Override
    public void onDisable() {
        if (!getServer().isStopping()) {
            getLogger().warning("Do not reload the server. This may cause issues with the plugin.");
            getLogger().warning("Please use /restart to restart the server instead of reloading.");
        }
    }
}
