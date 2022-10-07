package de.firstmine.casino.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle().equals("§aCongratulations!")) {
            Player player = (Player) event.getWhoClicked();
            ItemStack glass = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            if (Objects.equals(event.getCurrentItem(), glass)) {
                event.setCancelled(true);
            }
        }
    }

}
