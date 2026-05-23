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

    public ItemStack consumeNextBetItem() {
        if (betItems.isEmpty()) {
            return null;
        }

        return betItems.removeFirst().clone();
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
                addStacked(stashItems, item);
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
                addStacked(this.stashItems, item);
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

    private void addStacked(List<ItemStack> items, ItemStack item) {
        ItemStack remaining = item.clone();

        for (ItemStack existing : items) {
            if (!existing.isSimilar(remaining)) continue;

            int freeSpace = existing.getMaxStackSize() - existing.getAmount();
            if (freeSpace <= 0) continue;

            int moveAmount = Math.min(freeSpace, remaining.getAmount());
            existing.setAmount(existing.getAmount() + moveAmount);
            remaining.setAmount(remaining.getAmount() - moveAmount);

            if (remaining.getAmount() <= 0) {
                return;
            }
        }

        while (remaining.getAmount() > 0) {
            ItemStack stack = remaining.clone();
            int amount = Math.min(remaining.getAmount(), stack.getMaxStackSize());
            stack.setAmount(amount);
            items.add(stack);
            remaining.setAmount(remaining.getAmount() - amount);
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
