package de.firstminecoding.casinoPlugin.Casino.games.dice;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.function.Consumer;

public class DiceGame {

    public void startDice(Player player, JavaPlugin plugin, Consumer<Integer> onFinish) {
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)
                || !holder.getType().equals("dice")) return;

        Random random = new Random();

        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = 20;

            @Override
            public void run() {
                Inventory currentInventory = player.getOpenInventory().getTopInventory();

                if (!(currentInventory.getHolder() instanceof CasinoInventoryHolder currentHolder)
                        || !currentHolder.getType().equals("dice")) {
                    cancel();
                    onFinish.accept(-1);
                    return;
                }

                int animValue = random.nextInt(6) + 1;
                inventory.setItem(13, DiceCustomHead.fromNumber(animValue).createItem());

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1.5f);

                ticks++;

                if (ticks >= maxTicks) {
                    cancel();

                    int result = random.nextInt(6) + 1;
                    inventory.setItem(13, DiceCustomHead.fromNumber(result).createItem());

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        onFinish.accept(result);
                    }, 20L);
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}