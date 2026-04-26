package de.firstminecoding.casinoPlugin.Casino.gui;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilder.createItem;

import java.util.List;

public class CasinoGUI {
    public Inventory createCasinoInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino", NamedTextColor.GOLD, TextDecoration.BOLD);

        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("casino-menu"), 9, inventoryTitle);

        ItemStack glassPane = CasinoPanes.GRAY_STAINED_GLASS_PANE.createItem();
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

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, glassPane);
        }
        inventory.setItem(4, slotSymbol);
        inventory.setItem(8, chest);

        return inventory;
    }

}
