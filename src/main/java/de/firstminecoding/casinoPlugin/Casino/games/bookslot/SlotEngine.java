package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SlotEngine {
    private static final boolean TESTING_SCATTER_BOOST = false;
    private static final int TESTING_EXTRA_SCATTERS_PER_REEL = 0;
    private static final double HIGH_BONUS_SYMBOL_CHANCE = 0.35;
    private static final List<SlotSymbol> HIGH_BONUS_SYMBOLS = List.of(
            SlotSymbol.PHARAOH,
            SlotSymbol.CLEOPATRA,
            SlotSymbol.SPHINX,
            SlotSymbol.RING
    );
    private static final List<SlotSymbol> LOW_BONUS_SYMBOLS = List.of(
            SlotSymbol.ACE,
            SlotSymbol.KING,
            SlotSymbol.QUEEN,
            SlotSymbol.JACK,
            SlotSymbol.TEN
    );

    private final List<Reel> reels = List.of(
            reel(
                    SlotSymbol.ACE, SlotSymbol.KING, SlotSymbol.QUEEN, SlotSymbol.WILD,
                    SlotSymbol.PHARAOH, SlotSymbol.TEN, SlotSymbol.RING, SlotSymbol.JACK,
                    SlotSymbol.SCATTER, SlotSymbol.CLEOPATRA, SlotSymbol.ACE, SlotSymbol.SPHINX,
                    SlotSymbol.KING, SlotSymbol.TEN, SlotSymbol.QUEEN, SlotSymbol.RING,
                    SlotSymbol.ACE, SlotSymbol.JACK, SlotSymbol.TEN, SlotSymbol.ACE,
                    SlotSymbol.KING, SlotSymbol.QUEEN, SlotSymbol.JACK, SlotSymbol.TEN,
                    SlotSymbol.RING, SlotSymbol.ACE
            ),
            reel(
                    SlotSymbol.QUEEN, SlotSymbol.TEN, SlotSymbol.CLEOPATRA, SlotSymbol.ACE,
                    SlotSymbol.SCATTER, SlotSymbol.KING, SlotSymbol.RING, SlotSymbol.JACK,
                    SlotSymbol.SPHINX, SlotSymbol.QUEEN, SlotSymbol.TEN, SlotSymbol.PHARAOH,
                    SlotSymbol.ACE, SlotSymbol.KING, SlotSymbol.WILD, SlotSymbol.RING,
                    SlotSymbol.JACK, SlotSymbol.TEN, SlotSymbol.QUEEN, SlotSymbol.TEN,
                    SlotSymbol.ACE, SlotSymbol.JACK, SlotSymbol.KING, SlotSymbol.TEN,
                    SlotSymbol.RING, SlotSymbol.QUEEN
            ),
            reel(
                    SlotSymbol.TEN, SlotSymbol.RING, SlotSymbol.KING, SlotSymbol.PHARAOH,
                    SlotSymbol.ACE, SlotSymbol.QUEEN, SlotSymbol.SPHINX, SlotSymbol.JACK,
                    SlotSymbol.WILD, SlotSymbol.CLEOPATRA, SlotSymbol.TEN, SlotSymbol.KING,
                    SlotSymbol.SCATTER, SlotSymbol.ACE, SlotSymbol.QUEEN, SlotSymbol.RING,
                    SlotSymbol.JACK, SlotSymbol.TEN, SlotSymbol.ACE, SlotSymbol.TEN,
                    SlotSymbol.QUEEN, SlotSymbol.JACK, SlotSymbol.KING, SlotSymbol.TEN,
                    SlotSymbol.RING, SlotSymbol.ACE
            ),
            reel(
                    SlotSymbol.KING, SlotSymbol.ACE, SlotSymbol.JACK, SlotSymbol.SPHINX,
                    SlotSymbol.QUEEN, SlotSymbol.TEN, SlotSymbol.RING, SlotSymbol.CLEOPATRA,
                    SlotSymbol.ACE, SlotSymbol.WILD, SlotSymbol.KING, SlotSymbol.PHARAOH,
                    SlotSymbol.TEN, SlotSymbol.QUEEN, SlotSymbol.SCATTER, SlotSymbol.JACK,
                    SlotSymbol.RING, SlotSymbol.TEN, SlotSymbol.KING, SlotSymbol.TEN,
                    SlotSymbol.ACE, SlotSymbol.QUEEN, SlotSymbol.JACK, SlotSymbol.TEN,
                    SlotSymbol.RING, SlotSymbol.ACE
            ),
            reel(
                    SlotSymbol.JACK, SlotSymbol.QUEEN, SlotSymbol.TEN, SlotSymbol.RING,
                    SlotSymbol.ACE, SlotSymbol.SPHINX, SlotSymbol.KING, SlotSymbol.CLEOPATRA,
                    SlotSymbol.SCATTER, SlotSymbol.TEN, SlotSymbol.QUEEN, SlotSymbol.WILD,
                    SlotSymbol.PHARAOH, SlotSymbol.ACE, SlotSymbol.RING, SlotSymbol.KING,
                    SlotSymbol.JACK, SlotSymbol.TEN, SlotSymbol.QUEEN, SlotSymbol.TEN,
                    SlotSymbol.ACE, SlotSymbol.JACK, SlotSymbol.KING, SlotSymbol.TEN,
                    SlotSymbol.RING, SlotSymbol.ACE
            )
    );

    private static Reel reel(SlotSymbol... symbols) {
        List<SlotSymbol> strip = new ArrayList<>(List.of(symbols));

        if (TESTING_SCATTER_BOOST) {
            for (int i = 0; i < TESTING_EXTRA_SCATTERS_PER_REEL; i++) {
                strip.add(SlotSymbol.SCATTER);
            }
        }

        return new Reel(strip);
    }

    public SlotResult spin() {
        SlotSymbol[][] grid = new SlotSymbol[SlotResult.ROWS][SlotResult.REELS];
        List<Integer> startIndexes = new ArrayList<>();

        for (int reelIndex = 0; reelIndex < reels.size(); reelIndex++) {
            Reel reel = reels.get(reelIndex);
            int startIndex = ThreadLocalRandom.current().nextInt(reel.size());
            startIndexes.add(startIndex);

            for (int row = 0; row < SlotResult.ROWS; row++) {
                grid[row][reelIndex] = reel.getVisibleSymbol(startIndex, row);
            }
        }

        return new SlotResult(grid, startIndexes, evaluateWinningLines(grid));
    }

    public SlotSymbol[] spinReel(int reelIndex) {
        Reel reel = reels.get(reelIndex);
        int startIndex = ThreadLocalRandom.current().nextInt(reel.size());
        SlotSymbol[] visibleSymbols = new SlotSymbol[SlotResult.ROWS];

        for (int row = 0; row < SlotResult.ROWS; row++) {
            visibleSymbols[row] = reel.getVisibleSymbol(startIndex, row);
        }

        return visibleSymbols;
    }

    public SlotSymbol chooseBonusSymbol() {
        List<SlotSymbol> bonusSymbols = ThreadLocalRandom.current().nextDouble() < HIGH_BONUS_SYMBOL_CHANCE
                ? HIGH_BONUS_SYMBOLS
                : LOW_BONUS_SYMBOLS;

        return bonusSymbols.get(ThreadLocalRandom.current().nextInt(bonusSymbols.size()));
    }

    public SlotResult expandBonusSymbol(SlotResult result, SlotSymbol bonusSymbol) {
        SlotSymbol[][] expandedGrid = copyGrid(result);
        List<Integer> expandedReels = new ArrayList<>();

        for (int reel = 0; reel < SlotResult.REELS; reel++) {
            if (!containsSymbolOnReel(result, reel, bonusSymbol)) {
                continue;
            }

            expandedReels.add(reel);
            for (int row = 0; row < SlotResult.ROWS; row++) {
                expandedGrid[row][reel] = bonusSymbol;
            }
        }

        if (expandedReels.isEmpty()) {
            return result;
        }

        return new SlotResult(expandedGrid, result.getStartIndexes(), evaluateExpandedBonusLines(expandedGrid, bonusSymbol), expandedReels);
    }

    public SlotResult addBonusExpansionReel(SlotResult result, SlotSymbol bonusSymbol, int extraReel) {
        SlotSymbol[][] expandedGrid = copyGrid(result);
        List<Integer> expandedReels = new ArrayList<>(result.getExpandedReels());

        if (!expandedReels.contains(extraReel)) {
            expandedReels.add(extraReel);
        }

        for (int row = 0; row < SlotResult.ROWS; row++) {
            expandedGrid[row][extraReel] = bonusSymbol;
        }

        return new SlotResult(expandedGrid, result.getStartIndexes(), evaluateExpandedBonusLines(expandedGrid, bonusSymbol), expandedReels);
    }

    public int chooseRandomNonExpandedReel(SlotResult result) {
        List<Integer> candidates = new ArrayList<>();

        for (int reel = 0; reel < SlotResult.REELS; reel++) {
            if (!result.getExpandedReels().contains(reel)) {
                candidates.add(reel);
            }
        }

        if (candidates.isEmpty()) {
            return -1;
        }

        return candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
    }

    private SlotSymbol[][] copyGrid(SlotResult result) {
        SlotSymbol[][] copy = new SlotSymbol[SlotResult.ROWS][SlotResult.REELS];

        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                copy[row][reel] = result.getSymbol(row, reel);
            }
        }

        return copy;
    }

    private boolean containsSymbolOnReel(SlotResult result, int reel, SlotSymbol symbol) {
        for (int row = 0; row < SlotResult.ROWS; row++) {
            if (result.getSymbol(row, reel) == symbol) {
                return true;
            }
        }

        return false;
    }

    private List<WinningLine> evaluateWinningLines(SlotSymbol[][] grid) {
        List<WinningLine> winningLines = new ArrayList<>();

        for (Payline payline : Payline.values()) {
            WinningLine winningLine = evaluatePayline(grid, payline);

            if (winningLine != null) {
                winningLines.add(winningLine);
            }
        }

        return winningLines;
    }

    private List<WinningLine> evaluateExpandedBonusLines(SlotSymbol[][] grid, SlotSymbol bonusSymbol) {
        List<WinningLine> winningLines = new ArrayList<>();

        for (Payline payline : Payline.values()) {
            int matches = 0;
            List<Integer> winningSlots = new ArrayList<>();

            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                int row = payline.getRow(reel);
                SlotSymbol symbol = grid[row][reel];

                if (symbol == bonusSymbol || symbol.isWild()) {
                    matches++;
                    winningSlots.add(BookSlotGUI.getReelSlot(row, reel));
                }
            }

            int multiplier = bonusSymbol.getPayoutMultiplier(matches);
            if (multiplier > 0) {
                winningLines.add(new WinningLine(payline, bonusSymbol, matches, multiplier, winningSlots));
            }
        }

        return winningLines;
    }

    private WinningLine evaluatePayline(SlotSymbol[][] grid, Payline payline) {
        SlotSymbol paySymbol = findPaySymbol(grid, payline);

        if (paySymbol == null) {
            return null;
        }

        int matches = 0;
        List<Integer> winningSlots = new ArrayList<>();

        for (int reel = 0; reel < SlotResult.REELS; reel++) {
            int row = payline.getRow(reel);
            SlotSymbol symbol = grid[row][reel];

            if (symbol.isScatter()) {
                break;
            }

            if (symbol == paySymbol || symbol.isWild()) {
                matches++;
                winningSlots.add(BookSlotGUI.getReelSlot(row, reel));
                continue;
            }

            break;
        }

        int multiplier = paySymbol.getPayoutMultiplier(matches);
        if (multiplier <= 0) {
            return null;
        }

        return new WinningLine(payline, paySymbol, matches, multiplier, winningSlots);
    }

    private SlotSymbol findPaySymbol(SlotSymbol[][] grid, Payline payline) {
        for (int reel = 0; reel < SlotResult.REELS; reel++) {
            SlotSymbol symbol = grid[payline.getRow(reel)][reel];

            if (symbol.isWild()) {
                continue;
            }

            if (symbol.isScatter()) {
                return null;
            }

            return symbol;
        }

        return SlotSymbol.PHARAOH;
    }
}
