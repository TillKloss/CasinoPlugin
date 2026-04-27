package de.firstminecoding.casinoPlugin.Casino.games.coinflip;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoPanes;
import de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static de.firstminecoding.casinoPlugin.Casino.util.ItemBuilderUtil.*;

public class CoinflipGUI {
    public Inventory createCoinflipInventory(CasinoSession session) {
        Component inventoryTitle = Component.text("Casino - Coinflip", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("coinflip"), 27, inventoryTitle);

        ItemStack grayGlassPane = CasinoPanes.GRAY.createItem();

        ItemStack limeGlassPane = CasinoPanes.LIME.createItem(
                Component.text("FLIP", NamedTextColor.GREEN, TextDecoration.BOLD));
        ItemStack orangeGlassPane = CasinoPanes.ORANGE.createItem(
                Component.text("PLACE BET", NamedTextColor.GOLD, TextDecoration.BOLD));
        ItemStack redGlassPane = CasinoPanes.RED.createItem();

        ItemStack head = CoinflipCustomHead.HEADS.createItem();
        ItemStack tails = CoinflipCustomHead.TAILS.createItem();

        if (session.getSelectedCoinflipSide() == CoinflipSide.HEADS) {
            ItemMeta meta = head.getItemMeta();
            if (meta != null) {
                meta.displayName(
                        Component.text(">>HEADS<<",
                                NamedTextColor.LIGHT_PURPLE,
                                TextDecoration.BOLD
                        ));
                head.setItemMeta(meta);
            }
        }
        if (session.getSelectedCoinflipSide() == CoinflipSide.TAILS) {
            ItemMeta meta = tails.getItemMeta();
            if (meta != null) {
                meta.displayName(
                        Component.text(">>TAILS<<",
                                NamedTextColor.LIGHT_PURPLE,
                                TextDecoration.BOLD
                        ));
                tails.setItemMeta(meta);
            }
        }

        for (int i=0;i<27;i++) {
            inventory.setItem(i, grayGlassPane);
        }
        inventory.setItem(11, head);
        inventory.setItem(13, null);
        inventory.setItem(15, tails);
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

    public void setStartButton(Inventory inventory, Material material, Component component) {
        ItemStack itemStack = createItem(material, component);

        for (int i=20;i<25;i++) {
            inventory.setItem(i, itemStack);
        }
    }
}
