package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import java.util.List;

public class WinningLine {
    private final Payline payline;
    private final SlotSymbol symbol;
    private final int matches;
    private final int multiplier;
    private final List<Integer> winningSlots;

    public WinningLine(Payline payline, SlotSymbol symbol, int matches, int multiplier, List<Integer> winningSlots) {
        this.payline = payline;
        this.symbol = symbol;
        this.matches = matches;
        this.multiplier = multiplier;
        this.winningSlots = List.copyOf(winningSlots);
    }

    public Payline getPayline() {
        return payline;
    }

    public SlotSymbol getSymbol() {
        return symbol;
    }

    public int getMatches() {
        return matches;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public List<Integer> getWinningSlots() {
        return winningSlots;
    }
}
