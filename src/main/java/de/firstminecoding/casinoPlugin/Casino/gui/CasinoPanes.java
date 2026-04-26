package de.firstminecoding.casinoPlugin.Casino.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CasinoPanes {
    GRAY(Material.GRAY_STAINED_GLASS_PANE, Component.empty()),
    LIME(Material.LIME_STAINED_GLASS_PANE),
    ORANGE(Material.ORANGE_STAINED_GLASS_PANE),

    RED(Material.RED_STAINED_GLASS_PANE,
            Component.text("EXIT", NamedTextColor.RED, TextDecoration.BOLD));

    private final Material material;
    private final Component defaultName;

    CasinoPanes(Material material) {
        this(material, null);
    }

    CasinoPanes(Material material, Component defaultName) {
        this.material = material;
        this.defaultName = defaultName;
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null && defaultName != null) {
            meta.displayName(defaultName);
            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack createItem(Component name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(name);
            item.setItemMeta(meta);
        }

        return item;
    }
}