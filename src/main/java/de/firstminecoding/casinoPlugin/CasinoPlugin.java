package de.firstminecoding.casinoPlugin;

import de.firstminecoding.casinoPlugin.Casino.CasinoCommand;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoListener;
import de.firstminecoding.casinoPlugin.Casino.citizens.CasinoNPCListener;
import de.firstminecoding.casinoPlugin.Casino.games.slotmachine.SlotMachineListener;
import de.firstminecoding.casinoPlugin.Casino.stash.StashListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CasinoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        CasinoHandler casinoHandler = new CasinoHandler(this);

        //Register Command
        getCommand("casino").setExecutor(new CasinoCommand(casinoHandler));

        //Register Listener
        getServer().getPluginManager().registerEvents(new CasinoListener(casinoHandler), this);
        getServer().getPluginManager().registerEvents(new StashListener(casinoHandler), this);
        getServer().getPluginManager().registerEvents(new SlotMachineListener(casinoHandler), this);
        if (getServer().getPluginManager().getPlugin("Citizens") != null) {
            getServer().getPluginManager().registerEvents(new CasinoNPCListener(casinoHandler), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
