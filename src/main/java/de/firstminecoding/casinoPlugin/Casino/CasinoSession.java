package de.firstminecoding.casinoPlugin.Casino;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CasinoSession {
    private final UUID uuid;
    private List<ItemStack> betItems = new ArrayList<>();
    private boolean spinning;

    public CasinoSession(UUID uuid) {
        this.uuid = uuid;
        spinning = false;
    }

    public void clearBet() {
        betItems.clear();
    }

    public boolean hasBet() {
        return !betItems.isEmpty();
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<ItemStack> getBetItems() {
        List<ItemStack> copy = new ArrayList<>();

        for (ItemStack item : betItems) {
            copy.add(item.clone());
        }

        return copy;
    }

    public void setBetItems(List<ItemStack> betItems) {
        this.betItems = new ArrayList<>();

        for (ItemStack item : betItems) {
            if (item != null) {
                this.betItems.add(item.clone());
            }
        }
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }
}
