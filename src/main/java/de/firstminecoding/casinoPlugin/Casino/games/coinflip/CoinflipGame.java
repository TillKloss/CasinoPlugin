package de.firstminecoding.casinoPlugin.Casino.games.coinflip;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Random;
import java.util.function.Consumer;

public class CoinflipGame {
    public void startCoinflip(Player player, JavaPlugin plugin, Consumer<CoinflipSide> onFinish) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof CasinoInventoryHolder casinoHolder)
                || !casinoHolder.getType().equals("coinflip")) return;

        Random random = new Random();

        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = 20;

            @Override
            public void run() {
                Inventory currentInventory = player.getOpenInventory().getTopInventory();

                if (!(currentInventory.getHolder() instanceof CasinoInventoryHolder currentHolder)
                || !currentHolder.getType().equals("coinflip")) {
                    cancel();
                    onFinish.accept(null);
                    return;
                }
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1.5f);
                CoinflipCustomHead randomAnim = CoinflipCustomHead.values()[random.nextInt(CoinflipCustomHead.values().length)];

                inventory.setItem(13, randomAnim.createItem());

                ticks++;

                if (ticks >= maxTicks) {
                    cancel();

                    CoinflipSide result = random.nextBoolean() ? CoinflipSide.HEADS : CoinflipSide.TAILS;
                    inventory.setItem(13, CoinflipCustomHead.valueOf(result.name()).createItem());

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        onFinish.accept(result);
                    }, 20L);
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}
