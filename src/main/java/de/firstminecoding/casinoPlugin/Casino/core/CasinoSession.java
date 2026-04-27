package de.firstminecoding.casinoPlugin.Casino.core;

import de.firstminecoding.casinoPlugin.Casino.games.coinflip.CoinflipSide;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CasinoSession {
    private final UUID uuid;
    private List<ItemStack> betItems = new ArrayList<>();
    private List<ItemStack> stashItems = new ArrayList<>();
    private Set<Integer> selectedDiceNumbers = new HashSet<>();
    private boolean spinning;
    private CoinflipSide selectedCoinflipSide;
    private boolean coinflipRunning;
    private boolean diceRolling;

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

    public void addToStash(List<ItemStack> items) {
        for (ItemStack item : items) {
            if (item != null) {
                stashItems.add(item.clone());
            }
        }
    }

    public void clearStash() {
        stashItems.clear();
    }

    public boolean hasStashItems() {
        return !stashItems.isEmpty();
    }

    public List<ItemStack> getStashItems() {
        List<ItemStack> copy = new ArrayList<>();

        for (ItemStack item : stashItems) {
            copy.add(item.clone());
        }

        return copy;
    }

    public void setStashItems(List<ItemStack> stashItems) {
        this.stashItems = new ArrayList<>();

        for (ItemStack item : stashItems) {
            if (item != null) {
                this.stashItems.add(item.clone());
            }
        }
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

    public Set<Integer> getSelectedDiceNumbers() {
        return new HashSet<>(selectedDiceNumbers);
    }

    public void toggleDiceNumber(int number) {
        if (selectedDiceNumbers.contains(number)) {
            selectedDiceNumbers.remove(number);
            return;
        }

        if (selectedDiceNumbers.size() >= 3) return;

        selectedDiceNumbers.add(number);
    }

    public void clearSelectedDiceNumbers() {
        selectedDiceNumbers.clear();
    }

    public boolean isDiceRolling() {
        return diceRolling;
    }

    public void setDiceRolling(boolean diceRolling) {
        this.diceRolling = diceRolling;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public CoinflipSide getSelectedCoinflipSide() {
        return selectedCoinflipSide;
    }

    public void setSelectedCoinflipSide(CoinflipSide selectedCoinflipSide) {
        this.selectedCoinflipSide = selectedCoinflipSide;
    }

    public boolean isCoinflipRunning() {
        return coinflipRunning;
    }

    public void setCoinflipRunning(boolean coinflipRunning) {
        this.coinflipRunning = coinflipRunning;
    }
}
