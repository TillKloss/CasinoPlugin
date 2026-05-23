package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import java.util.List;

public class SlotResult {
    public static final int ROWS = 3;
    public static final int REELS = 5;

    private final SlotSymbol[][] symbols;
    private final List<Integer> startIndexes;
    private final List<WinningLine> winningLines;
    private final List<Integer> expandedReels;
    private final int totalMultiplier;

    public SlotResult(SlotSymbol[][] symbols, List<Integer> startIndexes, List<WinningLine> winningLines) {
        this(symbols, startIndexes, winningLines, List.of());
    }

    public SlotResult(SlotSymbol[][] symbols, List<Integer> startIndexes, List<WinningLine> winningLines, List<Integer> expandedReels) {
        this.symbols = symbols;
        this.startIndexes = List.copyOf(startIndexes);
        this.winningLines = List.copyOf(winningLines);
        this.expandedReels = List.copyOf(expandedReels);
        this.totalMultiplier = winningLines.stream()
                .mapToInt(WinningLine::getMultiplier)
                .sum();
    }

    public SlotSymbol getSymbol(int row, int reel) {
        return symbols[row][reel];
    }

    public SlotSymbol[] getReelSymbols(int reel) {
        SlotSymbol[] reelSymbols = new SlotSymbol[ROWS];

        for (int row = 0; row < ROWS; row++) {
            reelSymbols[row] = symbols[row][reel];
        }

        return reelSymbols;
    }

    public List<Integer> getStartIndexes() {
        return startIndexes;
    }

    public List<WinningLine> getWinningLines() {
        return winningLines;
    }

    public List<Integer> getExpandedReels() {
        return expandedReels;
    }

    public boolean hasExpandedReels() {
        return !expandedReels.isEmpty();
    }

    public boolean hasWins() {
        return !winningLines.isEmpty();
    }

    public int getTotalMultiplier() {
        return totalMultiplier;
    }

    public int countScatters() {
        int scatters = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int reel = 0; reel < REELS; reel++) {
                if (symbols[row][reel].isScatter()) {
                    scatters++;
                }
            }
        }

        return scatters;
    }
}
