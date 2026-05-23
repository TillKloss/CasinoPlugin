package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

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

public enum BookSlotCustomHead {
    NUMBER_ZERO(
            Component.text("0", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRiMDFhMzA3NzU2Mjg0MzA5MGUwMzBlZTdiNGE4NjM0ZTZmYzJkNTNlNDYwM2EzNGYyOGY1ZjJhZGMzMzcxZCJ9fX0="
    ),
    NUMBER_ONE(
            Component.text("1", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U2OWI1Y2ZlYmUwNWRkNDMwNTAyNzdlNWJlODNjODA3YjQ1YWRlMzBlMDgyOTFmNjg2N2M3MjNlODU0YjQ4MiJ9fX0="
    ),
    NUMBER_TWO(
            Component.text("2", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNkYmNkMzJiNTVlOGJiYzM2M2E3NDk3ZjA3NTc2MzhhN2JiMDc4YmUwN2YzNjJkMTExMjhjZjQyODhhNmJjYyJ9fX0="
    ),
    NUMBER_THREE(
            Component.text("3", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyOWU0Nzc0MDE4ODljOWUyNjNjZDQ5ZWM0Yzk4ODA1NDAxNDk5YjI3Yzk1NDYxNDIwMTAxOGI5MTcwMDc2ZCJ9fX0="
    ),
    NUMBER_FOUR(
            Component.text("4", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ4NmYwYTA1ZGM2ZmE3NmJjNDZjNGUzYzEyMWI0ZTBiOTUxNzgyMjgwODQ3NDdjYWQ2N2U1MWQ5YzVlNmJhYiJ9fX0="
    ),
    NUMBER_FIVE(
            Component.text("5", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNmMWMzNTI0MGQ0ZGY4YTJlNWY4MTU5MzQ3YTFkNDJmYjdiZGQ1MGM5MGRlOTVhZjMwNjg0MTgyNWQ3MmJiZCJ9fX0="
    ),
    NUMBER_SIX(
            Component.text("6", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxYmE1NTk5NzIyZGJhNjFkMTZmNzU5NjFmZTA5NGRlNDUxZDUxYjdkZGYwODdiZGNlNDFlZjkzMTk2ZDgxNiJ9fX0="
    ),
    NUMBER_SEVEN(
            Component.text("7", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA4Yjg4ZWZhMGZiMWZkMjMwMTNhYTEzNmFjYzFhZjQ0YmRlZDVmM2FlNDE3ZWY3ZjZkNDdiNGYyYzQ1NTBhMyJ9fX0="
    ),
    NUMBER_EIGHT(
            Component.text("8", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE1ZmQxYjdjNjM3NzNmOTMyNDUzNmU0OGNiZDBhYzEwMWUyYzE4Y2FiYWE1NTk5M2M1NWRiMDJhODZlMTk2YSJ9fX0="
    ),
    NUMBER_NINE(
            Component.text("9", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE0M2YyMmMwOWM4OGM0MWZiMGM0ZDZmZjI0ZjQ5N2Q4YzI4NjM0NWEwZGY2OTk4YTA1MGNmOTc1OWE3MmQxYSJ9fX0="
    ),
    LAST_WIN(
            Component.text("Last Win", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIzMjMyM2MzZGEwMGY0M2U3MDU4MTdmZGJjMTA2ZDJhODhlNTBlOWJkNTM4NGNlODE2MjJlMzBkNWU4MzliZCJ9fX0="
    ),
    BET(
            Component.text("Place Bet", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTdmNTMxYWNmYjgyNzliNjk1NTY3YTk1YjAyYTJlMjNjOTNkM2ZiYTZiYzZmOTNkZjZhN2Y4ZWI2MWYzMTdmYyJ9fX0="
    ),
    SLOT_INFO(
            Component.text("Slot Info", NamedTextColor.BLUE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY0MzlkMmUzMDZiMjI1NTE2YWE5YTZkMDA3YTdlNzVlZGQyZDUwMTVkMTEzYjQyZjQ0YmU2MmE1MTdlNTc0ZiJ9fX0="
    ),
    SPIN_INFO(
            Component.text("Spin Status", NamedTextColor.BLUE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY0MzlkMmUzMDZiMjI1NTE2YWE5YTZkMDA3YTdlNzVlZGQyZDUwMTVkMTEzYjQyZjQ0YmU2MmE1MTdlNTc0ZiJ9fX0="
    ),
    PAYOUT_TABLE(
            Component.text("Payout Table", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTcxYTIyODVjOTFjNmM3Mjc0NzYwNDgxOWVlNTIyM2E5MGFhNTFlNmU3OWU0ZjlhZjY2MjhlYzhmMGRkN2RmYyJ9fX0="
    ),
    AUTO_SPIN(
            Component.text("Auto Spin", NamedTextColor.GREEN, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ2NWMxMjE5NThjMDUyMmUzZGNjYjNkMTRkNjg2MTJkNjMxN2NkMzgwYjBlNjQ2YjYxYjc0MjBiOTA0YWYwMiJ9fX0="
    ),
    SYMBOL_SCATTER(
            Component.text("SCATTER", NamedTextColor.YELLOW, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ0YjY1ODZkYzVlN2ZmY2E5OGM5NjE5N2Y0ZDdjOTk3NDBiNmQwYTA4ZTg1MjFjZmNlMjJjZDZjOGI3ZmM0OCJ9fX0="
    ),
    SYMBOL_WILD(
            Component.text("BOOK WILD", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY5NGNmYzdiZjQ5ZTQ3YjQ0OGNjZDVmOGQ2ZGZjZjQwNzBlZTQ0ZTczYzQ4ZDRiNDI0Y2YxYTJkMzE0NSJ9fX0="
    ),
    SYMBOL_ACE(
            Component.text("A", NamedTextColor.RED, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZmODhiMTIyZmY5MjUxM2M2YTI3YjdmNjdjYjNmZWE5NzQzOWUwNzg4MjFkNjg2MWI3NDMzMmEyMzk2In19fQ=="
    ),
    SYMBOL_KING(
            Component.text("K", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBiMTNjNzE2NjYyODdjNTk4OWQ4NTEwMWRiN2M2NTUwZTJlMDcwNzZkYjUyYTdiYmJiYmRkYjg0OTMxMjkifX19"
    ),
    SYMBOL_QUEEN(
            Component.text("Q", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTgyMTc4MzdhNjNlNzRlMjRiYWZkNmM3YzUwMGQzNjc4YTY4MzhlZDg2MjE1YTc5ZjE2ZDQ5NjI3MDFmM2EifX19"
    ),
    SYMBOL_JOKER(
            Component.text("J", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgxZDJjMWE1YTU5YjU3NTM1OTQ4MmE4YTMzODU0Y2QzYjE2MzRkZDU0YzQ1YzRjYTFjYTEwZTg2MDdkNDcifX19"
    ),
    SYMBOL_TEN(
            Component.text("10", NamedTextColor.GRAY, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg4YTI4NTJmZmJmZjJlNDk1YmRlZmFkMmZjMmU2MzY5MDM3NWU1YTFiNjAyNGU5Yzk4OGU2Mzk2NWRkNzZkZSJ9fX0="
    ),
    SYMBOL_PHARAOH(
            Component.text("PHARAOH", NamedTextColor.YELLOW, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM2YmRmNThhNTg1YmM2OGQ2YzQwNjI1ZTY3ZDY1NWNhM2YxNTYxMWYyYmU3ODM4MWIwYjQ3YzNjNTE4YjllMSJ9fX0="
    ),
    SYMBOL_CLEOPATRA(
            Component.text("CLEOPATRA", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk0NWYzODc4ZDQyYTg1ZDcwNGY1ZmNiZTMxYjI1N2Q3Y2ViOTE3NzU3MWU2ZTc2N2M2ZGQxMzYyZjBmOWYifX19"
    ),
    SYMBOL_SPHINX(
            Component.text("SPHINX", NamedTextColor.AQUA, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFiMDZjZjQ2MmRkN2U0YzgyYjE3NDlmZGM0YzhlODg5NTgwNjg5ZWQ3Y2ExZjdlYWViODQ3ZTFlZmVjM2NkNiJ9fX0="
    ),
    SYMBOL_RING(
            Component.text("RING", NamedTextColor.GOLD, TextDecoration.BOLD),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNjMWIwM2I2OTAyYjk3ZjRjZWVhYTZiODNiZDQ3ODBmMmU0Y2YxNTdjZDZmYmNjZjMwMjcyY2JlYmU5N2E5YiJ9fX0="
    );


    private final Component name;
    private final String texture;

    BookSlotCustomHead(Component name, String texture) {
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
