package de.firstminecoding.casinoPlugin.Casino.stash;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoPanes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilder.createItem;

import java.util.List;

public class StashGUI {

    public Inventory createStashInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Stash", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("casino-stash"), 54, inventoryTitle);

        List<ItemStack> stashItems = session.getStashItems();

        ItemStack greenGlassPane = createItem(Material.LIME_STAINED_GLASS_PANE,
                Component.text("Collect all", NamedTextColor.GREEN, TextDecoration.BOLD));

        for (int i=0;i<stashItems.size() && i<52;i++) {
            inventory.setItem(i, stashItems.get(i));
        }
        inventory.setItem(53, CasinoPanes.RED_STAINED_GLASS_PANE.createItem());
        inventory.setItem(52, greenGlassPane);


        return inventory;
    }
}
