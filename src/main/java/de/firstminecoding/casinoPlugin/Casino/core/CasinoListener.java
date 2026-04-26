package de.firstminecoding.casinoPlugin.Casino.core;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CasinoListener implements Listener {
    private final CasinoHandler casinoHandler;

    public CasinoListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder casinoHolder)) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked ==null || clicked.getType() == Material.AIR) return;

        if (casinoHolder.getType().equals("casino-menu")) {
            if (clicked.getType() == Material.DIAMOND) {
                casinoHandler.getSlotMachineHandler().openSlotMachineInventory(player);
            }
            if (clicked.getType() == Material.CHEST) {
                casinoHandler.getStashHandler().openStashInventory(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof  CasinoInventoryHolder holder)) return;
        if (holder.getType().equals("slot-machine-bet")) {
            casinoHandler.getSlotMachineHandler().saveBetFromInventory(player, inventory);
            return;
        }
        if (holder.getType().equals("slot-machine-payout")) {
            casinoHandler.getStashHandler().stashRemainingPayout(player, inventory);
            return;
        }

    }
}
