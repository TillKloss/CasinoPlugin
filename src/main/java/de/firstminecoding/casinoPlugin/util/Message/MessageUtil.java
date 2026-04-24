package de.firstminecoding.casinoPlugin.util.Message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MessageUtil {
    public static Component onlyPlayer() {
        return Component.text("Nur Spieler!", NamedTextColor.RED);
    }

    public static Component success(String text) {return Component.text(text, NamedTextColor.GREEN);}
    public static Component unsuccess(String text) {return Component.text(text, NamedTextColor.RED);}
}
