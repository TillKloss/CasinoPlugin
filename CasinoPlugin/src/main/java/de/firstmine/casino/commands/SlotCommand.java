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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SlotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Casino.getServerPrefix()+"§cYou are not a player.");
            return false;
        }
        if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
            player.sendMessage(Casino.getCasinoPrefix() + "§cA spin costs §b1 diamond§c.");
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
        Random random = new Random();
        int[] arr = new int[3];
        for (int i=0;i<3;i++) {
            int result = random.nextInt(3);
            arr[i] = result+1;
        }
        if (!((arr[0] == arr[1]) && (arr[0] == arr[2]))) {
            player.sendMessage(Casino.getCasinoPrefix()+" §f[§b"+arr[0]+"§f][§b"+arr[1]+"§f][§b"+arr[2]+"§f]");
            return false;
        }
        player.sendMessage(Casino.getCasinoPrefix()+" §f[§b"+arr[0]+"§f][§b"+arr[1]+"§f][§b"+arr[2]+"§f]");
        Inventory win = Bukkit.createInventory(null, 9, "§aCongratulations!");
        for (int i=0; i<9; i++) win.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        for (int i=2; i<7; i++) win.setItem(i, diamond);
        player.openInventory(win);
        return false;
    }
}
