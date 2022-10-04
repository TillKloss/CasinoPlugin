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

import java.util.Random;



public class CoinflipCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(Casino.getCasinoPrefix()+"§cBitte benutze /coinflip {kopf/zahl}.");
            }else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("kopf") || args[0].equalsIgnoreCase("zahl")){
                    ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
                    ItemStack[] inv = player.getInventory().getContents();
                    boolean check = false;
                    for (int i=0; i<36;i++) {
                        if (!(player.getInventory().getItem(i) == null)) {
                            if (player.getInventory().getItem(i).equals(diamond)) {
                                player.getInventory().setItem(i, null);
                                check = true;
                                Random random = new Random();
                                String[] arr = {"Kopf", "Zahl"};
                                int result = random.nextInt(arr.length);
                                if (arr[result].equalsIgnoreCase(args[0])) {
                                    player.sendMessage(Casino.getCasinoPrefix()+"§aDer Münzwurf ergab §b"
                                            +arr[result]+"§a. Herzlichen Glückwunsch!");
                                    Inventory win = Bukkit.createInventory
                                            (null, 9, "§aHerzlichen Glückwunsch!");
                                    ItemStack glass = new ItemStack(Material.GLASS_PANE, 1, (short) 14);
                                    win.setItem(0, glass);
                                    win.setItem(1, glass);
                                    win.setItem(2, glass);
                                    win.setItem(3, diamond);
                                    win.setItem(4, glass);
                                    win.setItem(5, diamond);
                                    win.setItem(6, glass);
                                    win.setItem(7, glass);
                                    win.setItem(8, glass);
                                    player.openInventory(win);
                                    break;
                                }else {
                                    player.sendMessage(Casino.getCasinoPrefix()+"§cDer Münzwurf ergab §b"
                                            +arr[result]+"§c. Versuche es nochmal!");
                                    break;
                                }
                            }
                        }else {
                            continue;
                        }

                    }
                    if (!(check)) {
                        player.sendMessage(Casino.getCasinoPrefix()+"§cEin Münzwurf kostet §b1 Diamant§c.");
                    }
                }

            } else {
                player.sendMessage(Casino.getCasinoPrefix()+"§cBitte benutze /coinflip {kopf/zahl}.");
            }

        }else {
            sender.sendMessage(Casino.getServerPrefix()+"§cDu bist kein Spieler.");
        }
        return false;
    }
}
//test