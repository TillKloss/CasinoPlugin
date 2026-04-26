package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

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

public class SlotMachineGame {

    public void startSlotMachine(Player player, JavaPlugin plugin, Consumer<SlotMachineResult> onFinish) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof CasinoInventoryHolder casinoHolder)
                || !casinoHolder.getType().equals("slot-machine")) return;

        Random random = new Random();

        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = 20;

            @Override
            public void run() {
                Inventory currentInventory = player.getOpenInventory().getTopInventory();

                if (!(currentInventory.getHolder() instanceof CasinoInventoryHolder holder)
                        || !holder.getType().equals("slot-machine")) {
                    cancel();
                    onFinish.accept(SlotMachineResult.cancelled());
                    return;
                }

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1.5f);
                SlotMachineMaterial random1 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];
                SlotMachineMaterial random2 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];
                SlotMachineMaterial random3 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];

                inventory.setItem(12, random1.createItem());
                inventory.setItem(13, random2.createItem());
                inventory.setItem(14, random3.createItem());

                ticks++;

                if (ticks >= maxTicks) {
                    cancel();

                    SlotMachineMaterial random4 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];
                    SlotMachineMaterial random5 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];
                    SlotMachineMaterial random6 = SlotMachineMaterial.values()[random.nextInt(SlotMachineMaterial.values().length)];


                    inventory.setItem(12, random4.createItem());
                    inventory.setItem(13, random5.createItem());
                    inventory.setItem(14, random6.createItem());

                    SlotMachineResult result = new SlotMachineResult(random4, random5, random6, false);

                    if (result.isWin()) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    } else if (result.isPayback()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.2f);
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 0.7f);
                    }

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        onFinish.accept(result);
                    }, 30L);
                }

            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}
