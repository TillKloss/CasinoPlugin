package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

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

public class SlotMachineListener implements Listener {

    private final CasinoHandler casinoHandler;

    public SlotMachineListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder casinoHolder)) return;

        if (casinoHolder.getType().equals("slot-machine")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            CasinoSession session = casinoHandler.getSession(player);

            if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                casinoHandler.getSlotMachineHandler().startSlotMachine(player);
                return;
            }

            if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                if (session.isSpinning()) return;

                casinoHandler.getBetHandler().openBetInventory(player, "slot-machine");
                return;
            }

            if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
                if (session.isSpinning()) return;

                casinoHandler.openCasinoInventory(player);
            }
        }
    }
}