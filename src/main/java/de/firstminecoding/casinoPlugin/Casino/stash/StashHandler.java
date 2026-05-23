package de.firstminecoding.casinoPlugin.Casino.stash;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StashHandler {
    private final CasinoHandler casinoHandler;

    public StashHandler(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    public void openStashInventory(Player player) {
        player.openInventory(new StashGUI().createStashInventory(casinoHandler.getSession(player)));
    }

    public void stashRemainingPayout(Player player, Inventory inventory) {
        List<ItemStack> remaining = getRemainingPayoutItems(inventory);

        if (remaining.isEmpty()) return;

        casinoHandler.getSession(player).addToStash(remaining);
    }

    public void saveStashFromInventory(Player player, Inventory inventory) {
        List<ItemStack> stashItems = new ArrayList<>();

        for (int i=0;i<52;i++) {
            ItemStack item = inventory.getItem(i);

            if(item != null && item.getType() != Material.AIR) {
                stashItems.add(item.clone());
            }
        }

        casinoHandler.getSession(player).setStashItems(stashItems);
    }

    public void collectAllStashItems(Player player, Inventory inventory) {
        List<ItemStack> remaining = new ArrayList<>();

        for (int i=0;i<52;i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR) continue;

            Map<Integer, ItemStack> notAdded = player.getInventory().addItem(item.clone());

            if (notAdded.isEmpty()) {
                inventory.setItem(i, null);
            } else {
                ItemStack leftover = notAdded.values().iterator().next();
                inventory.setItem(i, leftover);
                remaining.add(leftover.clone());
            }
        }

        casinoHandler.getSession(player).setStashItems(remaining);
        player.openInventory(new StashGUI().createStashInventory(casinoHandler.getSession(player)));
    }

    public List<ItemStack> getRemainingPayoutItems(Inventory inventory) {
        List<ItemStack> remaining = new ArrayList<>();

        for (int i=0;i<51;i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null && item.getType() != Material.AIR) {
                remaining.add(item.clone());
                inventory.setItem(i, null);
            }
        }
        return remaining;
    }

    public void handleStashClose(Player player) {
        casinoHandler.openCasinoInventory(player);
    }

}
