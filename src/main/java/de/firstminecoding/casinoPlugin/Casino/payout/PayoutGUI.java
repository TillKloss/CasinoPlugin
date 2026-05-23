package de.firstminecoding.casinoPlugin.Casino.payout;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoPanes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil.createItem;

public class PayoutGUI {
    public static final int PAYOUT_ITEM_SLOTS = 51;

    public Inventory createPayoutInventory(List<ItemStack> rewards, String returnType) {
        Component inventoryTitle = Component.text("Casino - Payout", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("payout-"+returnType), 54, inventoryTitle);

        ItemStack orangeGlassPane = createItem(Material.ORANGE_STAINED_GLASS_PANE,
                Component.text("Move to Stash", NamedTextColor.GOLD, TextDecoration.BOLD));
        ItemStack greenGlassPane = CasinoPanes.LIME.createItem(
                Component.text("Collect all", NamedTextColor.GREEN, TextDecoration.BOLD));

        for (int i=0;i<rewards.size()&&i<PAYOUT_ITEM_SLOTS;i++) {
            inventory.setItem(i, rewards.get(i));
        }
        inventory.setItem(53, CasinoPanes.RED.createItem());
        inventory.setItem(52, orangeGlassPane);
        inventory.setItem(51, greenGlassPane);

        return inventory;
    }
}
