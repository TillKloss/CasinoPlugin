package de.firstminecoding.casinoPlugin.Casino.gui;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.games.coinflip.CoinflipCustomHead;
import de.firstminecoding.casinoPlugin.Casino.games.dice.DiceCustomHead;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil.createItem;

import java.util.List;

public class CasinoGUI {
    public Inventory createCasinoInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino", NamedTextColor.GOLD, TextDecoration.BOLD);

        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("casino-menu"), 9, inventoryTitle);

        ItemStack glassPane = CasinoPanes.GRAY.createItem();
        ItemStack redPane = CasinoPanes.RED.createItem();
        ItemStack chest = createItem(Material.CHEST,
                Component.text("Stash", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        ItemMeta meta = chest.getItemMeta();

        int itemStashAmount = 0;
        for (ItemStack itemStack : session.getStashItems()) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                itemStashAmount += itemStack.getAmount();
            }
        }
        if (meta != null) {
            meta.lore(List.of(
                    Component.text(itemStashAmount, NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)
                            .append(Component.text(" items in stash", NamedTextColor.LIGHT_PURPLE)
                                    .decoration(TextDecoration.BOLD, false))
            ));
            chest.setItemMeta(meta);
        }

        ItemStack slotSymbol = createItem(Material.DIAMOND,
                Component.text("Slot Machine", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        ItemStack coinflipSymbol = CoinflipCustomHead.HEADS.createItem();
        ItemMeta metaCoinflip = coinflipSymbol.getItemMeta();
        if (metaCoinflip != null) {
            metaCoinflip.displayName(Component.text("Coinflip", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
            coinflipSymbol.setItemMeta(metaCoinflip);
        }
        ItemStack diceSymbol = DiceCustomHead.DICE_ONE.createItem();
        ItemMeta metaDice = diceSymbol.getItemMeta();
        if (metaDice != null) {
            metaDice.displayName(Component.text("Dice", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
            diceSymbol.setItemMeta(metaDice);
        }

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, glassPane);
        }
        inventory.setItem(0, chest);
        inventory.setItem(2, slotSymbol);
        inventory.setItem(4, diceSymbol);
        inventory.setItem(6, coinflipSymbol);
        inventory.setItem(8, redPane);

        return inventory;
    }

    public Inventory createSlotSelectionInventory() {
        Component inventoryTitle = Component.text("Casino - Slots", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("slot-selection"), 9, inventoryTitle);

        ItemStack glassPane = CasinoPanes.GRAY.createItem();
        ItemStack redPane = CasinoPanes.RED.createItem();
        ItemStack classicSlotSymbol = createItem(Material.DIAMOND,
                Component.text("Classic Slot Machine", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        ItemStack bookSlotSymbol = createItem(Material.BOOK,
                Component.text("Book Slot", NamedTextColor.GOLD, TextDecoration.BOLD));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, glassPane);
        }

        inventory.setItem(3, classicSlotSymbol);
        inventory.setItem(5, bookSlotSymbol);
        inventory.setItem(8, redPane);

        return inventory;
    }

}
