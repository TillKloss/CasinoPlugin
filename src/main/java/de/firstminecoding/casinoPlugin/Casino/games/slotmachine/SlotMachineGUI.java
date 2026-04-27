package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

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
import org.bukkit.inventory.meta.ItemMeta;
import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil.createItem;

import java.util.List;

public class SlotMachineGUI {
    public Inventory createSlotMachineInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Slot Machine", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("slot-machine"), 27, inventoryTitle);

        ItemStack grayGlassPane = CasinoPanes.GRAY.createItem();

        ItemStack limeGlassPane = CasinoPanes.LIME.createItem(
                Component.text("SPIN", NamedTextColor.GREEN, TextDecoration.BOLD));
        ItemStack orangeGlassPane = CasinoPanes.ORANGE.createItem(
                Component.text("PLACE BET", NamedTextColor.GOLD, TextDecoration.BOLD));
        ItemStack redGlassPane = CasinoPanes.RED.createItem();
        ItemStack purpleGlassPane = createItem(Material.PURPLE_STAINED_GLASS_PANE,
                Component.text("Payouts", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));

        ItemMeta meta = purpleGlassPane.getItemMeta();
        if (meta != null) {
            meta.lore(List.of(
                    Component.text("COAL ").color(NamedTextColor.DARK_GRAY)
                            .append(Component.text("| 2x: loss | 3x: loss", NamedTextColor.GRAY)),
                    Component.text("IRON ").color(NamedTextColor.GRAY)
                            .append(Component.text("| 2x: payback | 3x: bet x5", NamedTextColor.GRAY)),
                    Component.text("GOLD ").color(NamedTextColor.GOLD)
                            .append(Component.text("| 2x: payback | 3x: bet x7", NamedTextColor.GRAY)),
                    Component.text("DIAMOND ").color(NamedTextColor.AQUA)
                            .append(Component.text("| 2x: payback | 3x: bet x10", NamedTextColor.GRAY)),
                    Component.empty()
            ));
        }
        purpleGlassPane.setItemMeta(meta);

        ItemStack diamondSymbol = SlotMachineMaterial.DIAMOND.createItem();
        ItemStack goldSymbol = SlotMachineMaterial.GOLD.createItem();
        ItemStack ironSymbol = SlotMachineMaterial.IRON.createItem();

        for (int i=0;i<27;i++) {
            inventory.setItem(i, grayGlassPane);
        }
        inventory.setItem(12, diamondSymbol);
        inventory.setItem(13, goldSymbol);
        inventory.setItem(14, ironSymbol);
        for (int k=20;k<25;k++) {
            inventory.setItem(k, limeGlassPane);
        }
        inventory.setItem(0, orangeGlassPane);
        inventory.setItem(1, orangeGlassPane);
        inventory.setItem(7, orangeGlassPane);
        inventory.setItem(8, orangeGlassPane);
        inventory.setItem(10, purpleGlassPane);
        inventory.setItem(18, redGlassPane);
        inventory.setItem(19, redGlassPane);
        inventory.setItem(25, redGlassPane);
        inventory.setItem(26, redGlassPane);

        List<ItemStack> betItems = session.getBetItems();

        int startSlot = 2;
        for (int i = 0; i < betItems.size() && i < 5; i++) {
            inventory.setItem(startSlot + i, betItems.get(i));
        }

        return inventory;
    }

    public void setSpinButton(Inventory inventory, Material material, Component component) {
        ItemStack itemStack = createItem(material, component);

        for (int i=20;i<25;i++) {
            inventory.setItem(i, itemStack);
        }
    }
}
