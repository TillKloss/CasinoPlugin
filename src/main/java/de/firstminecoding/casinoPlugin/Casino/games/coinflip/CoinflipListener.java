package de.firstminecoding.casinoPlugin.Casino.games.coinflip;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CoinflipListener implements Listener {
    private final CasinoHandler casinoHandler;

    public CoinflipListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!((event.getWhoClicked()) instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder casinoHolder)) return;

        if (casinoHolder.getType().equals("coinflip-bet")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            if (event.getRawSlot() >= 5 && event.getRawSlot() <= 8) {
                event.setCancelled(true);
                casinoHandler.getCoinflipHandler().handleBetSave(player, event.getInventory());
                return;
            }

            event.setCancelled(false);
            return;
        }

        if (casinoHolder.getType().equals("coinflip-payout")) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            if (event.getRawSlot() == 52) {
                event.setCancelled(true);
                casinoHandler.getStashHandler().stashRemainingPayout(player, topInventory);
                casinoHandler.getCoinflipHandler().openCoinflipInventory(player);
                return;
            }

            if (event.getRawSlot() == 53) {
                event.setCancelled(true);
                casinoHandler.getCoinflipHandler().handlePayoutClose(player);
                return;
            }

            event.setCancelled(false);
            return;
        }

        if (casinoHolder.getType().equals("coinflip")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            CasinoSession session = casinoHandler.getSession(player);

            if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                casinoHandler.getCoinflipHandler().startCoinflip(player);
                return;
            }

            if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                if (session.isCoinflipRunning()) return;

                casinoHandler.getCoinflipHandler().openBetInventory(player);
                return;
            }

            if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
                if (session.isCoinflipRunning()) return;

                casinoHandler.openCasinoInventory(player);
            }

            if (event.getRawSlot() == 11) {
                if (session.isCoinflipRunning()) return;
                casinoHandler.getCoinflipHandler().selectSide(player, CoinflipSide.HEADS);
                return;
            }

            if (event.getRawSlot() == 15) {
                if (session.isCoinflipRunning()) return;
                casinoHandler.getCoinflipHandler().selectSide(player, CoinflipSide.TAILS);
                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)) return;

        if (holder.getType().equals("coinflip-bet")) {
            casinoHandler.getCoinflipHandler().saveBetFromInventory(player, inventory);
            return;
        }

        if (holder.getType().equals("coinflip-payout")) {
            casinoHandler.getStashHandler().stashRemainingPayout(player, inventory);
        }
    }
}
