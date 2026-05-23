package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FreeSpinState {
    private int remainingSpins;
    private final SlotSymbol bonusSymbol;
    private final List<ItemStack> baseBetItems;
    private int totalMultiplier;

    public FreeSpinState(int remainingSpins, SlotSymbol bonusSymbol, List<ItemStack> baseBetItems) {
        this.remainingSpins = remainingSpins;
        this.bonusSymbol = bonusSymbol;
        this.baseBetItems = cloneItems(baseBetItems);
    }

    public boolean isActive() {
        return remainingSpins > 0;
    }

    public int getRemainingSpins() {
        return remainingSpins;
    }

    public SlotSymbol getBonusSymbol() {
        return bonusSymbol;
    }

    public List<ItemStack> getBaseBetItems() {
        return cloneItems(baseBetItems);
    }

    public int getTotalMultiplier() {
        return totalMultiplier;
    }

    public void addMultiplier(int multiplier) {
        totalMultiplier += multiplier;
    }

    public void addSpins(int spins) {
        remainingSpins += spins;
    }

    public void consumeSpin() {
        if (remainingSpins > 0) {
            remainingSpins--;
        }
    }

    private List<ItemStack> cloneItems(List<ItemStack> items) {
        List<ItemStack> clones = new ArrayList<>();

        for (ItemStack item : items) {
            if (item != null) {
                clones.add(item.clone());
            }
        }

        return clones;
    }
}
