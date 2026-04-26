package de.firstminecoding.casinoPlugin.Casino.payout;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.stash.StashGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayoutHandler {
    private final CasinoHandler casinoHandler;

    public PayoutHandler(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    public void openPayoutInventory(Player player, List<ItemStack> rewards, String returnType) {
        player.openInventory(new PayoutGUI().createPayoutInventory(rewards, returnType));
    }

    public void handlePayoutClose(Player player, String returnType) {
        if (returnType.equals("coinflip")) {
            casinoHandler.getCoinflipHandler().openCoinflipInventory(player);
            return;
        }
        if (returnType.equals("slot-machine")) {
            casinoHandler.getSlotMachineHandler().openSlotMachineInventory(player);
            return;
        }
    }

    public void movePayoutToStash(Player player, Inventory inventory, String returnType) {
        casinoHandler.getStashHandler().stashRemainingPayout(player, inventory);
        handlePayoutClose(player, returnType);
    }

    public void collectAllPayoutItems(Player player, Inventory inventory) {
        for (int i = 0; i < 51; i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR) continue;

            Map<Integer, ItemStack> notAdded = player.getInventory().addItem(item.clone());

            if (notAdded.isEmpty()) {
                inventory.setItem(i, null);
            } else {
                ItemStack leftover = notAdded.values().iterator().next();
                inventory.setItem(i, leftover);
            }
        }
    }

}
