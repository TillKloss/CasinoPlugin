package de.firstminecoding.casinoPlugin.Casino.games.coinflip;

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

public enum CoinflipCustomHead {
    HEADS(
            Component.text("HEADS", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjEyNjBkNzg2MGQ4YzJkNmM3ZjVmNTc4YjAyOWMzNzVlNGM0NmNlY2IwNGMzYjAyYmE0MzFiOTJlMzVlNzUzNSJ9fX0="
    ),

    TAILS(
            Component.text("TAILS", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTE0M2Q3MDA4ZmQ2MzI1ZmQ5MjQ0NzA5OTVlYWE1MTI2NTliNzBlYzVmYTk2YTNlZGQzNjZjMjUzN2MzNGIxOCJ9fX0="
    );
    private final Component name;
    private final String texture;

    CoinflipCustomHead(Component name, String texture) {
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
}
