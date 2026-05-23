package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BookSlotListener implements Listener {
    private final CasinoHandler casinoHandler;

    public BookSlotListener(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof CasinoInventoryHolder holder)) return;
        if (!holder.getType().equals("book-slot")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        int slot = event.getRawSlot();

        if (slot == 0 || slot == 45) {
            casinoHandler.getBetHandler().openBetInventory(player, "book-slot");
            return;
        }

        if (slot >= 38 && slot <= 42) {
            casinoHandler.getBookSlotHandler().spin(player, topInventory);
            return;
        }

        if (slot == 46 || slot == 52) {
            casinoHandler.getBookSlotHandler().toggleAutoSpin(player, topInventory);
            return;
        }

        if (slot == 8 || slot == 53) {
            casinoHandler.openCasinoInventory(player);
        }
    }
}
