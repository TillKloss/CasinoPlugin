package de.firstminecoding.casinoPlugin.Casino;

import de.firstminecoding.casinoPlugin.util.Message.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CasinoCommand implements CommandExecutor {
    private final CasinoHandler casinoHandler;

    public CasinoCommand(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageUtil.onlyPlayer());
            return true;
        }

        casinoHandler.openCasinoInventory(player);
        return true;
    }
}
