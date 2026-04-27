package de.firstminecoding.casinoPlugin.Casino.bet;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class BetListener implements Listener {
    private final CasinoHandler casinoHandler;

    public BetListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder holder)) return;

        String type = holder.getType();
        if (!type.startsWith("bet-")) return;

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        String returnType = type.replace("bet-", "");

        if (event.getRawSlot() >= 5 && event.getRawSlot() <= 8) {
            event.setCancelled(true);
            casinoHandler.getBetHandler().handleBetSave(player, topInventory, returnType);
            return;
        }

        event.setCancelled(false);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)) return;

        String type = holder.getType();
        if (!type.startsWith("bet-")) return;

        casinoHandler.getBetHandler().saveBetFromInventory(player, inventory);
    }
}
