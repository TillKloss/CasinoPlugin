package de.firstminecoding.casinoPlugin;

import de.firstminecoding.casinoPlugin.Casino.CasinoCommand;
import de.firstminecoding.casinoPlugin.Casino.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.CasinoListener;
import de.firstminecoding.casinoPlugin.Casino.Citizens.CasinoNPCListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CasinoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        CasinoHandler casinoHandler = new CasinoHandler(this);

        //Register Command
        getCommand("casino").setExecutor(new CasinoCommand(casinoHandler));

        //Register Listener
        getServer().getPluginManager().registerEvents(new CasinoListener(casinoHandler), this);
        if (getServer().getPluginManager().getPlugin("Citizens") != null) {
            getServer().getPluginManager().registerEvents(new CasinoNPCListener(casinoHandler), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
