package de.firstminecoding.casinoPlugin.Casino.stash;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StashCommand implements CommandExecutor {
    private final CasinoHandler casinoHandler;

    public StashCommand(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Nur Spieler!", NamedTextColor.RED));
            return true;
        }

        casinoHandler.getStashHandler().openStashInventory(player);
        return true;
    }
}
