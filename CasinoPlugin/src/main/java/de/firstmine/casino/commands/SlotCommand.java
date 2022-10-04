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
import java.util.Random;

public class SlotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
            ItemStack[] inv = player.getInventory().getContents();
            boolean check = false;
            for (int i=0; i<36;i++) {
                if (!(player.getInventory().getItem(i) == null)) {
                    if (player.getInventory().getItem(i).equals(diamond)) {
                        player.getInventory().setItem(i, null);
                        check = true;
                        Random random = new Random();
                        int[] arr = new int[3];
                        int range = 3;
                        for (int j=0; j<3; j++) {
                            arr[j] = random.nextInt(range);
                        }
                        player.sendMessage(Casino.getCasinoPrefix()+" §f[§b"+(arr[0]+1)+"§f][§b"+
                                (arr[1]+1)+"§f][§b"+(arr[2]+1)+"§f]");
                        if ((arr[0] == arr[1]) && (arr[0] == arr[2])) {
                            player.sendMessage(Casino.getCasinoPrefix()+"§aHerzlichen Glückwunsch!");
                            Inventory win = Bukkit.createInventory
                                    (null, 9, "§aHerzlichen Glückwunsch!");
                            ItemStack glass = new ItemStack(Material.GLASS_PANE, 1, (short) 14);
                            win.setItem(0, glass);
                            win.setItem(1, glass);
                            win.setItem(2, diamond);
                            win.setItem(3, diamond);
                            win.setItem(4, diamond);
                            win.setItem(5, diamond);
                            win.setItem(6, diamond);
                            win.setItem(7, glass);
                            win.setItem(8, glass);
                            player.openInventory(win);
                            break;
                        }else {
                            player.sendMessage(Casino.getCasinoPrefix()+"§cDu hast verloren!");
                            break;
                        }
                    }
                }else {
                    continue;
                }
            }
            if (!(check)) {
                player.sendMessage(Casino.getCasinoPrefix()+"§cEine Drehung kostet §b1 Diamant§c.");
            }
        }else {
            sender.sendMessage(Casino.getServerPrefix()+"§cDu bist kein Spieler.");
        }
        return false;
    }
}
