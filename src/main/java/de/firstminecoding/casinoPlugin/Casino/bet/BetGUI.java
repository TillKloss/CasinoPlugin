package de.firstminecoding.casinoPlugin.Casino.bet;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoPanes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BetGUI {
    public Inventory createBetInventory(CasinoSession session, String returnType) {
        Component inventoryTitle = Component.text("Casino - Place Bet", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("bet-"+returnType), 9, inventoryTitle);

        List<ItemStack> betItems = session.getBetItems();
        for (int i=0;i<betItems.size() && i<5;i++) {
            inventory.setItem(i, betItems.get(i));
        }

        ItemStack redGlassPane = CasinoPanes.RED.createItem();

        for (int j=5;j<9;j++) {
            inventory.setItem(j, redGlassPane);
        }

        return inventory;
    }
}
