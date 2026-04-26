package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public enum SlotMachineMaterial {

    DIAMOND(Material.DIAMOND, Component.text("DIAMOND", NamedTextColor.AQUA, TextDecoration.BOLD), 9, 1),
    GOLD(Material.GOLD_INGOT, Component.text("GOLD", NamedTextColor.GOLD, TextDecoration.BOLD), 6, 1),
    IRON(Material.IRON_INGOT, Component.text("IRON", NamedTextColor.GRAY, TextDecoration.BOLD), 4, 1),
    COAL(Material.COAL, Component.text("COAL", NamedTextColor.DARK_GRAY, TextDecoration.BOLD), 0, 0)
    ;

    private final Material material;
    private final Component name;
    private final int tripleMultiplier;
    private final int doubleMultiplier;

    SlotMachineMaterial(Material material, @NotNull Component name, int tripleMultiplier, int doubleMultiplier) {
        this.material = material;
        this.name = name;
        this.tripleMultiplier = tripleMultiplier;
        this.doubleMultiplier = doubleMultiplier;
    }

    public int getTripleMultiplier() {
        return tripleMultiplier;
    }
    public int getDoubleMultiplier() {
        return doubleMultiplier;
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(name);
            item.setItemMeta(meta);
        }

        return item;
    }
}