package de.firstminecoding.casinoPlugin.Casino.core;

import de.firstminecoding.casinoPlugin.Casino.games.coinflip.CoinflipCustomHead;
import de.firstminecoding.casinoPlugin.Casino.games.dice.DiceCustomHead;
import net.kyori.adventure.text.Component;
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
            int slot = event.getRawSlot();

            switch (slot) {
                case 0 -> {
                    casinoHandler.getStashHandler().openStashInventory(player);
                }
                case 2 -> {
                    casinoHandler.getSlotMachineHandler().openSlotMachineInventory(player);
                }
                case 4 -> {
                    casinoHandler.getDiceHandler().openDiceInventory(player);
                }
                case 6 -> {
                    casinoHandler.getCoinflipHandler().openCoinflipInventory(player);
                }
                case 8 -> {
                    player.closeInventory();
                }
            }
        }
    }
}
