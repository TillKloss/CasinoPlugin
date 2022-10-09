package de.firstmine.casino.commands;

import de.firstmine.casino.Casino;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;


public class CoinflipCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Casino.getServerPrefix()+"§cYou are not a player.");
            return false;
        }
        if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)){
            player.sendMessage("§cYou dont have §b\"+1+\" diamonds§c.");
            return false;
        }
        if (args.length != 1 || !("head".equalsIgnoreCase(args[0]) || "tails".equalsIgnoreCase(args[0]))) {
            player.sendMessage(Casino.getCasinoPrefix()+"§cPlease use /coinflip {head/tails}.");
            return false;
        }
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        if (!player.getInventory().containsAtLeast(diamond, 1)) {
            player.sendMessage(Casino.getCasinoPrefix()+"§cYou dont have §b"+1+" diamonds§c.");
            return true;
        }
        for (ItemStack item : player.getInventory()) {
            if (item != null)
                item.setAmount(item.getAmount()-1);
        }
        Random random= new Random();
        boolean result = (random.nextBoolean()?"head":"tails").equalsIgnoreCase(args[0]);
        if (!(result)) {
            player.sendMessage(Casino.getCasinoPrefix()+"§cThe coinflip did not result in §b"
                    +args[0]+"§c. Try again!");
            return false;
        }
        player.sendMessage(Casino.getCasinoPrefix()+"§aThe coinflip resulted in §b"
                +args[0]+"§a. Congratulations!");
        Inventory win = Bukkit.createInventory
                (null, 9, "§aCongratulations!");
        ItemStack glass = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        for (int i=0; i<9; i++) {
            win.setItem(i, glass);
        }
        win.setItem(3, diamond);
        win.setItem(5, diamond);
        player.openInventory(win);
        return false;
    }
}
