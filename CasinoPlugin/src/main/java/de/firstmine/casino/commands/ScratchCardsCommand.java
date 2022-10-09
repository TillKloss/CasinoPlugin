package de.firstmine.casino.commands;

import de.firstmine.casino.Casino;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ScratchCardsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Casino.getServerPrefix() + "§cYou are not a player.");
            return true;
        }
        if (args.length != 1) {
            player.sendMessage(Casino.getCasinoPrefix() + "§cPlease use /scratchcards {1-5}.");
            return true;
        }
        try {
            int amount = Integer.parseInt(args[0]);
            if(!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), amount)){
                player.sendMessage("§cYou dont have §b"+amount+" diamonds§c.");
                return true;
            }
            if (!(5 >= amount && 1 <= amount)) {
                player.sendMessage(Casino.getCasinoPrefix() + "§cPlease use /scratchcards {1-5}.");
                return true;
            }
            for(ItemStack invItem : player.getInventory().getContents()) {
                if(invItem != null) {
                    if(invItem.getType().equals(Material.DIAMOND)) {
                        int preAmount = invItem.getAmount();
                        int newAmount = Math.max(0, preAmount - amount);
                        amount = Math.max(0, amount - preAmount);
                        invItem.setAmount(newAmount);
                        if(amount == 0) {
                            break;
                        }
                    } else {
                        player.sendMessage(Casino.getCasinoPrefix()+"§cYou dont have §b"+amount+" diamonds§c.");
                        break;
                    }
                }
            }
            amount = Integer.parseInt(args[0]);
            int[] win = new int[amount];
            for (int j = 0; j < amount; j++) {
                Random random = new Random();
                int[] arr = new int[3];
                for (int i = 0; i < 3; i++) {
                    int result = random.nextInt(9, 15);
                    arr[i] = result + 1;
                }
                if (!((arr[0] == arr[1]))) {
                    win[j] = 0;
                    continue;
                }
                win[j] = arr[0];
            }
            System.out.println(Arrays.toString(win));
            for (int i = 1; i < amount + 1; i++)
                player.sendMessage(Casino.getCasinoPrefix() +
                        "§aGame " + i + ": §b" + win[i - 1] + " diamonds§a.");
            Inventory inv = Bukkit.createInventory(null, 9, "§aCongratulations!");
            for (int i = 0; i < 9; i++) inv.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE));

            for (int i = 0; i < amount; i++)
                inv.setItem(i + 2, new ItemStack(Material.DIAMOND, win[i]));
            player.openInventory(inv);
            return false;
        } catch (NumberFormatException e) {
            player.sendMessage(Casino.getCasinoPrefix() + "§cPlease use /scratchcards {1-5}.");
            return false;
        }
    }
}
