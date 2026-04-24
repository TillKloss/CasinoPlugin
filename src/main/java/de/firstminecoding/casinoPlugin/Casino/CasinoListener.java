package de.firstminecoding.casinoPlugin.Casino;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
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
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof CasinoInventoryHolder casinoHolder)) return;

        if (casinoHolder.getType().equals("slot-machine-bet")) {
            if (event.getSlot() == 8) {
                event.setCancelled(true);
                casinoHandler.handleBetSave(player, event.getInventory());
                return;
            }
            event.setCancelled(false);
            return;
        }

        if (casinoHolder.getType().equals("slot-machine-payout")) {
            if (event.getRawSlot() == 53) {
                event.setCancelled(true);
                casinoHandler.handlePayoutClose(player);
                return;
            }
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked ==null || clicked.getType() == Material.AIR) return;

        if (casinoHolder.getType().equals("casino-menu")) {
            if (clicked.getType() == Material.DIAMOND) {
                casinoHandler.openSlotMachineInventory(player);
            }
        }

        if (casinoHolder.getType().equals("slot-machine")) {
            if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                casinoHandler.startSlotMachine(player);
            }
            if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                casinoHandler.openBetInventory(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof  CasinoInventoryHolder holder)) return;
        if (!holder.getType().equals("slot-machine-bet")) return;

        casinoHandler.saveBetFromInventory(player, inventory);
    }
}
