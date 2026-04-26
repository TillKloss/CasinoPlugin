package de.firstminecoding.casinoPlugin.Casino.citizens;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CasinoNPCListener implements Listener {
    private final CasinoHandler casinoHandler;

    public CasinoNPCListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        if (!event.getNPC().getName().equalsIgnoreCase("Casino")) return;

        casinoHandler.openCasinoInventory(player);
    }
}
