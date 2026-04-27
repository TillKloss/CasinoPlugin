package de.firstminecoding.casinoPlugin.Casino.games.dice;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public enum DiceCustomHead {
    DICE_ONE(
            Component.text("ONE", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzMwZDBkNGQwZTM3OTc0MzhjYzM3M2JiNTY0YzNjN2I1ZTg0M2IzNjRkZTUyYzhhZGU2MWQ2NWIxOTk2MWM2In19fQ=="
    ),
    DICE_TWO(
            Component.text("TWO", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzBmM2E3ZjdjZmM0NGE2YzI2M2M5ZTUwZmM0NWEzYjQ2Zjk0NDExZTMxZjc3YmIwZWQwY2ExZDE0MTc3NDFkNCJ9fX0="
    ),
    DICE_THREE(
            Component.text("THREE", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThkODQyMzhhNTQ2MDMxNDM5Yjg2ZGYyZjI5YTAwZmM3YzY3NzU5YjkzMjIzOGEzY2E5ZWEwNmJlNWY1ODVmOSJ9fX0="
    ),
    DICE_FOUR(
            Component.text("FOUR", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmJlZTcyYjQ5NDVlNzY0OWE5MTA3N2QwMzM1MmU0NjUwZmYxNGE0ODc4OGU2Y2JmMjAzNDUwMzZjMDU0ZWQ3In19fQ=="
    ),
    DICE_FIVE(
            Component.text("FIVE", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZlMmNjMmU3OWNlMDQwNmRjNDhkZDBiYTBlMGJjOThkZGRkOTdkNDRkNjZhYWJkM2NhMjEzZjIzNmExOWFiZCJ9fX0="
    ),
    DICE_SIX(
            Component.text("SIX", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWU2ZjA3Y2I5MjU4N2Y1NGNlYWU4MzIwM2Q0NTgyMDlmNDU1N2YzMDJmNDVhMmM3OGNlYzMxNzhmMWQ3ZjIxMiJ9fX0="
    );

    private final Component name;
    private final String texture;

    DiceCustomHead(Component name, String texture) {
        this.name = name;
        this.texture = texture;
    }

    public ItemStack createItem() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", texture));

            meta.setPlayerProfile(profile);
            meta.displayName(name);

            head.setItemMeta(meta);
        }

        return head;
    }

    public static DiceCustomHead fromNumber(int number) {
        int index = Math.max(1, Math.min(6, number)) - 1;
        return values()[index];
    }
}
