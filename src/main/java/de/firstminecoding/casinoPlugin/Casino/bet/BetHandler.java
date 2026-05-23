package de.firstminecoding.casinoPlugin.Casino.bet;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BetHandler {
    private final CasinoHandler casinoHandler;

    public BetHandler(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    public void openBetInventory(Player player, String returnType) {
        player.openInventory(new BetGUI().createBetInventory(casinoHandler.getSession(player), returnType));
    }

    public void handleBetSave(Player player, Inventory inventory, String returnType) {
        saveBetFromInventory(player, inventory);
        switch (returnType) {
            case "slot-machine" -> casinoHandler.getSlotMachineHandler().openSlotMachineInventory(player);
            case "book-slot" -> casinoHandler.getBookSlotHandler().openBookSlotInventory(player);
            case "coinflip" -> casinoHandler.getCoinflipHandler().openCoinflipInventory(player);
            case "dice" -> casinoHandler.getDiceHandler().openDiceInventory(player);
        }
    }

    public void saveBetFromInventory(Player player, Inventory inventory) {
        List<ItemStack> betItems = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null && item.getType() != Material.AIR) {
                betItems.add(item.clone());
            }
        }

        casinoHandler.getSession(player).setBetItems(betItems);
    }
}
