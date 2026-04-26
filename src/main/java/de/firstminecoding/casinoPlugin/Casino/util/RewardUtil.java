package de.firstminecoding.casinoPlugin.Casino.util;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewardUtil {
    public static List<ItemStack> multiplyItems(List<ItemStack> items, int multiplier) {
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
}
