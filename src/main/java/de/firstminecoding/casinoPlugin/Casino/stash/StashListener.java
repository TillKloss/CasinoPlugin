package de.firstminecoding.casinoPlugin.Casino.stash;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class StashListener implements Listener {

    private final CasinoHandler casinoHandler;

    public StashListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder holder)) return;
        if (!holder.getType().equals("casino-stash")) return;

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        if (event.getRawSlot() == 52) {
            event.setCancelled(true);
            casinoHandler.getStashHandler().collectAllStashItems(player, topInventory);
            return;
        }

        if (event.getRawSlot() == 53) {
            event.setCancelled(true);
            player.closeInventory();

            Bukkit.getScheduler().runTaskLater(
                    casinoHandler.getPlugin(),
                    () -> casinoHandler.openCasinoInventory(player),
                    1L
            );
            return;
        }

        if (event.isShiftClick() && event.getRawSlot() >= topInventory.getSize()) {
            event.setCancelled(true);
            return;
        }

        if (event.getRawSlot() < topInventory.getSize()) {
            if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(false);
            return;
        }

        event.setCancelled(false);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)) return;
        if (!holder.getType().equals("casino-stash")) return;

        casinoHandler.getStashHandler().saveStashFromInventory(player, inventory);
    }
}