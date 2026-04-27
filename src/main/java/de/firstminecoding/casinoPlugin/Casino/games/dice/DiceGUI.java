package de.firstminecoding.casinoPlugin.Casino.games.dice;

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

import java.util.List;
import java.util.Set;

import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil.createItem;

public class DiceGUI {
    public Inventory createDiceInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Dice", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("dice"), 27, inventoryTitle);

        ItemStack grayGlassPane = CasinoPanes.GRAY.createItem();

        ItemStack limeGlassPane = CasinoPanes.LIME.createItem(
                Component.text("ROLL", NamedTextColor.GREEN, TextDecoration.BOLD));
        ItemStack orangeGlassPane = CasinoPanes.ORANGE.createItem(
                Component.text("PLACE BET", NamedTextColor.GOLD, TextDecoration.BOLD));
        ItemStack redGlassPane = CasinoPanes.RED.createItem();

        ItemStack one = DiceCustomHead.DICE_ONE.createItem();
        ItemStack two = DiceCustomHead.DICE_TWO.createItem();
        ItemStack three = DiceCustomHead.DICE_THREE.createItem();
        ItemStack four = DiceCustomHead.DICE_FOUR.createItem();
        ItemStack five = DiceCustomHead.DICE_FIVE.createItem();
        ItemStack six = DiceCustomHead.DICE_SIX.createItem();

        Set<Integer> selected = session.getSelectedDiceNumbers();

        if (selected.contains(1)) one = markSelected(one, "ONE");
        if (selected.contains(2)) two = markSelected(two, "TWO");
        if (selected.contains(3)) three = markSelected(three, "THREE");
        if (selected.contains(4)) four = markSelected(four, "FOUR");
        if (selected.contains(5)) five = markSelected(five, "FIVE");
        if (selected.contains(6)) six = markSelected(six, "SIX");

        for (int i=0;i<27;i++) {
            inventory.setItem(i, grayGlassPane);
        }
        inventory.setItem(9, one);
        inventory.setItem(10, two);
        inventory.setItem(11, three);
        inventory.setItem(13, null);
        inventory.setItem(15, four);
        inventory.setItem(16, five);
        inventory.setItem(17, six);

        for (int k=20;k<25;k++) {
            inventory.setItem(k, limeGlassPane);
        }
        inventory.setItem(0, orangeGlassPane);
        inventory.setItem(1, orangeGlassPane);
        inventory.setItem(7, orangeGlassPane);
        inventory.setItem(8, orangeGlassPane);
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

    private ItemStack markSelected(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(
                    Component.text(">>"+name+"<<",
                            NamedTextColor.LIGHT_PURPLE,
                            TextDecoration.BOLD
                    )
            );
            item.setItemMeta(meta);
        }
        return item;
    }

    public void setRollButton(Inventory inventory, Material material, Component component) {
        ItemStack itemStack = createItem(material, component);

        for (int i=20;i<25;i++) {
            inventory.setItem(i, itemStack);
        }
    }
}
