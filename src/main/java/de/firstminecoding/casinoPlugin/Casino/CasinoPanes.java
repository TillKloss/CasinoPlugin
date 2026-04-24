package de.firstminecoding.casinoPlugin.Casino;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public enum CasinoPanes {
    GRAY_STAINED_GLASS_PANE(Material.GRAY_STAINED_GLASS_PANE,
            Component.empty()),
    LIME_STAINED_GLASS_PANE(Material.LIME_STAINED_GLASS_PANE,
            Component.text("SPIN", NamedTextColor.GREEN, TextDecoration.BOLD)),
    ORANGE_STAINED_GLASS_PANE(Material.ORANGE_STAINED_GLASS_PANE,
            Component.text("PLACE BET", NamedTextColor.GOLD, TextDecoration.BOLD)),
    RED_STAINED_GLASS_PANE(Material.RED_STAINED_GLASS_PANE,
            Component.text("EXIT", NamedTextColor.RED, TextDecoration.BOLD))
    ;

    private final Material material;
    private final Component name;

    CasinoPanes(Material material, @NotNull Component name) {
        this.material = material;
        this.name = name;
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
