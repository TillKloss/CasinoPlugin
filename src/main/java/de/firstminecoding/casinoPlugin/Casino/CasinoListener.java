package de.firstminecoding.casinoPlugin.Casino;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
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
        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder casinoHolder)) return;

        if (casinoHolder.getType().equals("slot-machine-bet")) {
            if (event.getRawSlot() >=5 && event.getRawSlot() <=8) {
                event.setCancelled(true);
                casinoHandler.handleBetSave(player, event.getInventory());
                return;
            }
            event.setCancelled(false);
            return;
        }

        if (casinoHolder.getType().equals("slot-machine-payout")) {
            if (event.getRawSlot() == 52) {
                event.setCancelled(true);

                casinoHandler.stashRemainingPayout(player, topInventory);
                casinoHandler.openSlotMachineInventory(player);

                return;
            }

            if (event.getRawSlot() == 53) {
                event.setCancelled(true);
                casinoHandler.handlePayoutClose(player);
                return;
            }

            event.setCancelled(false);
            return;
        }

        if (casinoHolder.getType().equals("casino-stash")) {
            if (event.getRawSlot() == 52) {
                event.setCancelled(true);
                casinoHandler.collectAllStashItems(player, topInventory);
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
            return;
        }

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked ==null || clicked.getType() == Material.AIR) return;

        if (casinoHolder.getType().equals("casino-menu")) {
            if (clicked.getType() == Material.DIAMOND) {
                casinoHandler.openSlotMachineInventory(player);
            }
            if (clicked.getType() == Material.CHEST) {
                casinoHandler.openStashInventory(player);
            }
        }

        if (casinoHolder.getType().equals("slot-machine")) {
            CasinoSession session = casinoHandler.getSession(player);

            if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                casinoHandler.startSlotMachine(player);
            }
            if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                if (session.isSpinning()) {
                    event.setCancelled(true);
                    return;
                }

                casinoHandler.openBetInventory(player);
            }
            if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
                casinoHandler.openCasinoInventory(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof  CasinoInventoryHolder holder)) return;
        if (holder.getType().equals("slot-machine-bet")) {
            casinoHandler.saveBetFromInventory(player, inventory);
            return;
        }
        if (holder.getType().equals("slot-machine-payout")) {
            casinoHandler.stashRemainingPayout(player, inventory);
            return;
        }
        if (holder.getType().equals("casino-stash")) {
            casinoHandler.saveStashFromInventory(player, inventory);
            return;
        }

    }
}
