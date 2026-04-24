package de.firstminecoding.casinoPlugin.Casino;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public enum SlotMachineMaterial {

    DIAMOND(Material.DIAMOND, Component.text("DIAMOND", NamedTextColor.AQUA, TextDecoration.BOLD), 10),
    GOLD(Material.GOLD_INGOT, Component.text("GOLD", NamedTextColor.GOLD, TextDecoration.BOLD), 7),
    IRON(Material.IRON_INGOT, Component.text("IRON", NamedTextColor.GRAY, TextDecoration.BOLD), 5);

    private final Material material;
    private final Component name;
    private final int multiplier;

    SlotMachineMaterial(Material material, @NotNull Component name, int multiplier) {
        this.material = material;
        this.name = name;
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
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