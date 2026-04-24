package de.firstminecoding.casinoPlugin.Casino;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CasinoGUI {
    public Inventory createCasinoInventory() {
        Component inventoryTitle = Component.text("Casino", NamedTextColor.GOLD, TextDecoration.BOLD);

        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("casino-menu"), 9, inventoryTitle);

        ItemStack glassPane = CasinoPanes.GRAY_STAINED_GLASS_PANE.createItem();
        ItemStack chest = createItem(Material.CHEST,
                Component.text("Stash", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        ItemStack slotSymbol = createItem(Material.DIAMOND,
                Component.text("Slot Machine", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, glassPane);
        }
        inventory.setItem(4, slotSymbol);
        inventory.setItem(8, chest);

        return inventory;
    }

    public Inventory createSlotMachineInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Slot Machine", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("slot-machine"), 27, inventoryTitle);

        ItemStack grayGlassPane = CasinoPanes.GRAY_STAINED_GLASS_PANE.createItem();

        ItemStack limeGlassPane = CasinoPanes.LIME_STAINED_GLASS_PANE.createItem();
        ItemStack orangeGlassPane = CasinoPanes.ORANGE_STAINED_GLASS_PANE.createItem();
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
        inventory.setItem(18, orangeGlassPane);
        inventory.setItem(19, orangeGlassPane);
        inventory.setItem(25, orangeGlassPane);
        inventory.setItem(26, orangeGlassPane);

        List<ItemStack> betItems = session.getBetItems();

        int startSlot = 2;
        for (int i = 0; i < betItems.size() && i < 5; i++) {
            inventory.setItem(startSlot + i, betItems.get(i));
        }

        return inventory;
    }

    public Inventory createBetInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Place Bet", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("slot-machine-bet"), 9, inventoryTitle);

        List<ItemStack> betItems = session.getBetItems();
        for (int i=0;i<betItems.size() && i<5;i++) {
            inventory.setItem(i, betItems.get(i));
        }

        ItemStack redGlassPane = CasinoPanes.RED_STAINED_GLASS_PANE.createItem();

        for (int j=5;j<9;j++) {
            inventory.setItem(j, redGlassPane);
        }


        return inventory;
    }

    public Inventory createPayoutInventory(List<ItemStack> rewards) {
        Component inventoryTitle = Component.text("Casino - Payout", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("slot-machine-payout"), 54, inventoryTitle);

        ItemStack orangeGlassPane = createItem(Material.ORANGE_STAINED_GLASS_PANE,
                Component.text("Move to Stash", NamedTextColor.GOLD, TextDecoration.BOLD));

        for (int i=0;i<rewards.size()&&i<53;i++) {
            inventory.setItem(i, rewards.get(i));
        }
        inventory.setItem(53, CasinoPanes.RED_STAINED_GLASS_PANE.createItem());
        inventory.setItem(52, orangeGlassPane);

        return inventory;
    }

    public Inventory createStashInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Stash", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("casino-stash"), 54, inventoryTitle);

        List<ItemStack> stashItems = session.getStashItems();

        for (int i=0;i<stashItems.size() && i<52;i++) {
            inventory.setItem(i, stashItems.get(i));
        }
        inventory.setItem(53, CasinoPanes.RED_STAINED_GLASS_PANE.createItem());

        return inventory;
    }

    public void setSpinButton(Inventory inventory, Material material, Component component) {
        ItemStack itemStack = createItem(material, component);

        for (int i=20;i<25;i++) {
            inventory.setItem(i, itemStack);
        }
    }

    private ItemStack createItem(Material material, Component name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.displayName(name);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
}
