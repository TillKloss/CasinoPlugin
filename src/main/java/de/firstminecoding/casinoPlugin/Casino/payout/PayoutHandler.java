package de.firstminecoding.casinoPlugin.Casino.payout;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

}
