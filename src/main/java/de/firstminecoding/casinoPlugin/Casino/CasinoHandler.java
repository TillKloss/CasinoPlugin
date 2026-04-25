package de.firstminecoding.casinoPlugin.Casino;

import de.firstminecoding.casinoPlugin.Casino.Games.SlotMachineGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CasinoHandler {
    private final JavaPlugin plugin;
    private final Map<UUID, CasinoSession> sessions = new HashMap<>();

    public CasinoHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public CasinoSession getSession(Player player) {
        return sessions.computeIfAbsent(
                player.getUniqueId(),
                uuid -> new CasinoSession(uuid)
        );
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void openCasinoInventory(Player player) {
        player.openInventory(new CasinoGUI().createCasinoInventory());
    }

    public void openSlotMachineInventory(Player player) {
        player.openInventory(new CasinoGUI().createSlotMachineInventory(getSession(player)));
    }

    public void openBetInventory(Player player) {
        player.openInventory(new CasinoGUI().createBetInventory(getSession(player)));
    }

    public void openStashInventory(Player player) {
        player.openInventory(new CasinoGUI().createStashInventory(getSession(player)));
    }

    public void handleBetSave(Player player, Inventory inventory) {
        saveBetFromInventory(player, inventory);
        openSlotMachineInventory(player);
    }

    public void saveBetFromInventory(Player player, Inventory inventory) {
        List<ItemStack> betItems = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null && item.getType() != Material.AIR) {
                betItems.add(item.clone());
            }
        }

        getSession(player).setBetItems(betItems);
    }

    public void handlePayoutClose(Player player) {
        openSlotMachineInventory(player);
    }

    public List<ItemStack> multiplyItems(List<ItemStack> items, int multiplier) {
        if (multiplier <= 0) return Collections.emptyList();

        List<ItemStack> rewards = new ArrayList<>();

        for (ItemStack item : items) {
            int total = item.getAmount() * multiplier;

            while (total > 0) {
                ItemStack reward = item.clone();

                int amount = Math.min(total, reward.getMaxStackSize());
                reward.setAmount(amount);

                rewards.add(reward);
                total -= amount;
            }
        }
        return rewards;
    }

    public void startSlotMachine(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)
                || !holder.getType().equals("slot-machine")) return;

        CasinoSession session = getSession(player);

        if (session.isSpinning()) return;
        if (!session.hasBet()) return;
        session.setSpinning(true);

        new CasinoGUI().setSpinButton(inventory, Material.RED_STAINED_GLASS_PANE, Component.text("...", NamedTextColor.RED, TextDecoration.BOLD));

        SlotMachineGame slotMachineGame = new SlotMachineGame();
        slotMachineGame.startSlotMachine(player, plugin, result -> {
            new CasinoGUI().setSpinButton(inventory, Material.LIME_STAINED_GLASS_PANE,
                    Component.text("SPIN", NamedTextColor.GREEN, TextDecoration.BOLD)
            );
            session.setSpinning(false);

            if (result.isCancelled()) return;

            if (result.isPayback()) {
                openSlotMachineInventory(player);
                return;
            }

            int multiplier = result.getMultiplier();

            if (multiplier > 0) {
                List<ItemStack> rewards = multiplyItems(session.getBetItems(), multiplier);
                player.openInventory(new CasinoGUI().createPayoutInventory(rewards));
            } else {
                session.clearBet();
                openSlotMachineInventory(player);
            }
        });
    }

    public List<ItemStack> getRemainingPayoutItems(Inventory inventory) {
        List<ItemStack> remaining = new ArrayList<>();

        for (int i=0;i<50;i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null && item.getType() != Material.AIR) {
                remaining.add(item.clone());
                inventory.setItem(i, null);
            }
        }
        return remaining;
    }

    public void stashRemainingPayout(Player player, Inventory inventory) {
        List<ItemStack> remaining = getRemainingPayoutItems(inventory);

        if (remaining.isEmpty()) return;

        getSession(player).addToStash(remaining);
    }

    public void saveStashFromInventory(Player player, Inventory inventory) {
        List<ItemStack> stashItems = new ArrayList<>();

        for (int i=0;i<52;i++) {
            ItemStack item = inventory.getItem(i);

            if(item != null && item.getType() != Material.AIR) {
                stashItems.add(item.clone());
            }
        }

        getSession(player).setStashItems(stashItems);
    }

    public void collectAllStashItems(Player player, Inventory inventory) {
        List<ItemStack> remaining = new ArrayList<>();

        for (int i=0;i<52;i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR) continue;

            Map<Integer, ItemStack> notAdded = player.getInventory().addItem(item.clone());

            if (notAdded.isEmpty()) {
                inventory.setItem(i, null);
            } else {
                remaining.addAll(notAdded.values());
            }
        }

        getSession(player).setStashItems(remaining);
        player.openInventory(new CasinoGUI().createStashInventory(getSession(player)));
    }

    public void handleStashClose(Player player) {
        openCasinoInventory(player);
    }
}
