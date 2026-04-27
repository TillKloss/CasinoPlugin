package de.firstminecoding.casinoPlugin.Casino.games.dice;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DiceListener implements Listener {

    private final CasinoHandler casinoHandler;

    public DiceListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder holder)) return;
        if (!holder.getType().equals("dice")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        CasinoSession session = casinoHandler.getSession(player);

        if (session.isDiceRolling()) return;

        int slot = event.getRawSlot();

        switch (slot) {
            case 9 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 1);
                return;
            }
            case 10 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 2);
                return;
            }
            case 11 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 3);
                return;
            }
            case 15 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 4);
                return;
            }
            case 16 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 5);
                return;
            }
            case 17 -> {
                casinoHandler.getDiceHandler().selectNumber(player, 6);
                return;
            }
        }

        if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
            casinoHandler.getDiceHandler().startDice(player);
            return;
        }

        if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
            casinoHandler.getBetHandler().openBetInventory(player, "dice");
            return;
        }

        if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
            casinoHandler.openCasinoInventory(player);
        }
    }
}