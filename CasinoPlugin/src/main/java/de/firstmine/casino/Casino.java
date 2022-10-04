package de.firstmine.casino;

import de.firstmine.casino.commands.CoinflipCommand;
import de.firstmine.casino.commands.SlotCommand;
import de.firstmine.casino.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Casino extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("slot").setExecutor(new SlotCommand());
        getCommand("coinflip").setExecutor(new CoinflipCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryListener(), this);

    }

    public static String getServerPrefix() {
        return ChatColor.GOLD + "[Server]";
    }
    public static String getCasinoPrefix() {
        return ChatColor.GOLD + "[Casino]";
    }

    @Override
    public void onDisable() {
    }
}
