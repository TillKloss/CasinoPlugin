package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class BookSlotHandler {
    private static final int[] REEL_STOP_STEPS = {8, 14, 20, 26, 32};
    private static final double BONUS_ASSIST_CHANCE = 0.5;
    private static final long POST_REEL_RESULT_DELAY_TICKS = 8L;
    private static final int WIN_LINE_PAUSE_STEPS = 2;

    private final CasinoHandler casinoHandler;
    private final SlotEngine slotEngine = new SlotEngine();
    private final Set<UUID> spinningPlayers = new HashSet<>();
    private final Set<UUID> autoSpinPlayers = new HashSet<>();
    private final Map<UUID, FreeSpinState> freeSpinStates = new HashMap<>();
    private final Map<UUID, List<ItemStack>> activeBetItems = new HashMap<>();

    public BookSlotHandler(CasinoHandler casinoHandler) {
        this.casinoHandler = casinoHandler;
    }

    public void openBookSlotInventory(Player player) {
        player.openInventory(new BookSlotGUI().createBookSlotInventory(casinoHandler.getSession(player), freeSpinStates.get(player.getUniqueId())));
    }

    public void toggleAutoSpin(Player player, Inventory inventory) {
        UUID playerId = player.getUniqueId();
        BookSlotGUI gui = new BookSlotGUI();
        FreeSpinState freeSpinState = freeSpinStates.get(playerId);

        if (freeSpinState != null && freeSpinState.isActive()) {
            blockAutoSpin(playerId, inventory, gui);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.9f);
            return;
        }

        if (autoSpinPlayers.remove(playerId)) {
            gui.displayAutoSpin(inventory, false);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.6f, 1.2f);
            return;
        }

        if (!casinoHandler.getSession(player).hasBet()) {
            gui.displayAutoSpin(inventory, false);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.8f, 0.8f);
            return;
        }

        autoSpinPlayers.add(playerId);
        gui.displayAutoSpin(inventory, true);

        if (!spinningPlayers.contains(playerId)) {
            spin(player, inventory);
        }
    }

    public void spin(Player player, Inventory inventory) {
        UUID playerId = player.getUniqueId();
        FreeSpinState freeSpinState = freeSpinStates.get(playerId);
        boolean freeSpin = freeSpinState != null && freeSpinState.isActive();

        if (!freeSpin && !casinoHandler.getSession(player).hasBet()) {
            disableAutoSpin(playerId, inventory, new BookSlotGUI());
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.8f, 0.8f);
            return;
        }

        if (!spinningPlayers.add(playerId)) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.8f);
            return;
        }

        SlotResult result = slotEngine.spin();
        BookSlotGUI gui = new BookSlotGUI();
        List<ItemStack> betItems = freeSpin ? freeSpinState.getBaseBetItems() : consumeBet(player, gui, inventory);

        if (freeSpin) {
            freeSpinState.consumeSpin();
            gui.displayFreeSpinState(inventory, freeSpinState);
        } else {
            activeBetItems.put(playerId, betItems);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.8f, 1.2f);

        new BukkitRunnable() {
            private int step = 0;
            private final boolean[] stoppedReels = new boolean[SlotResult.REELS];

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                for (int reel = 0; reel < SlotResult.REELS; reel++) {
                    if (stoppedReels[reel]) {
                        continue;
                    }

                    if (step >= REEL_STOP_STEPS[reel]) {
                        gui.displayReel(inventory, reel, result.getReelSymbols(reel));
                        stoppedReels[reel] = true;
                    } else {
                        gui.displayReel(inventory, reel, slotEngine.spinReel(reel));
                    }
                }

                playSpinSound(player, step);

                if (step >= REEL_STOP_STEPS[SlotResult.REELS - 1]) {
                    gui.displayResult(inventory, result);
                    finishSpinAfterReelPause(player, inventory, gui, result, playerId, freeSpin, freeSpinState);
                    cancel();
                    return;
                }

                step++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 0L, 2L);
    }

    private void finishSpinAfterReelPause(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, boolean freeSpin, FreeSpinState freeSpinState) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    return;
                }

                finishSpin(player, inventory, gui, result, playerId, freeSpin, freeSpinState, freeSpin);
            }
        }.runTaskLater(casinoHandler.getPlugin(), POST_REEL_RESULT_DELAY_TICKS);
    }

    private void handleExpansion(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, FreeSpinState freeSpinState) {
        SlotResult expandedResult = slotEngine.expandBonusSymbol(result, freeSpinState.getBonusSymbol());
        if (!expandedResult.hasExpandedReels()) {
            completeSpin(player, inventory, gui, playerId, freeSpinState);
            return;
        }

        if (expandedResult.getExpandedReels().size() == 2) {
            animateBonusAssist(player, inventory, gui, expandedResult, playerId, freeSpinState);
            return;
        }

        animateExpansion(player, inventory, gui, result, expandedResult, playerId, freeSpinState);
    }

    private void animateBonusAssist(Player player, Inventory inventory, BookSlotGUI gui, SlotResult expandedResult, UUID playerId, FreeSpinState freeSpinState) {
        SlotSymbol bonusSymbol = freeSpinState.getBonusSymbol();
        boolean assisted = ThreadLocalRandom.current().nextDouble() < BONUS_ASSIST_CHANCE;
        int extraReel = assisted ? slotEngine.chooseRandomNonExpandedReel(expandedResult) : -1;
        SlotResult finalResult = extraReel >= 0
                ? slotEngine.addBonusExpansionReel(expandedResult, bonusSymbol, extraReel)
                : expandedResult;

        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.7f, 1.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.55f, 0.7f);

        new BukkitRunnable() {
            private int blinkStep = 0;

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                if (blinkStep % 2 == 0) {
                    gui.displayBoardSymbol(inventory, bonusSymbol);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.45f, 0.85f + (blinkStep * 0.09f));
                } else {
                    gui.displayResult(inventory, expandedResult);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25f, 1.25f + (blinkStep * 0.05f));
                }

                if (blinkStep >= 9) {
                    gui.displayResult(inventory, finalResult);

                    if (extraReel >= 0) {
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.75f, 1.45f);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.8f);
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.55f, 1.1f);
                    }

                    finishExpandedSpin(player, inventory, gui, finalResult, playerId, freeSpinState);
                    cancel();
                    return;
                }

                blinkStep++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 2L, 4L);
    }

    private void animateExpansion(Player player, Inventory inventory, BookSlotGUI gui, SlotResult originalResult, SlotResult expandedResult, UUID playerId, FreeSpinState freeSpinState) {
        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.8f, 1.4f);

        new BukkitRunnable() {
            private int blinkStep = 0;

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                if (blinkStep % 2 == 0) {
                    displayReels(gui, inventory, expandedResult, expandedResult.getExpandedReels());
                } else {
                    displayReels(gui, inventory, originalResult, expandedResult.getExpandedReels());
                }

                if (blinkStep >= 5) {
                    gui.displayResult(inventory, expandedResult);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.9f, 1.8f);
                    finishExpandedSpin(player, inventory, gui, expandedResult, playerId, freeSpinState);
                    cancel();
                    return;
                }

                blinkStep++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 2L, 4L);
    }

    private void displayReels(BookSlotGUI gui, Inventory inventory, SlotResult result, List<Integer> reels) {
        for (int reel : reels) {
            gui.displayReel(inventory, reel, result.getReelSymbols(reel));
        }
    }

    private void finishExpandedSpin(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, FreeSpinState freeSpinState) {
        freeSpinState.addMultiplier(result.getTotalMultiplier());
        moveWinToStash(player, freeSpinState.getBaseBetItems(), result.getTotalMultiplier());
        gui.displayWinInfo(inventory, result, freeSpinState);

        if (!result.hasWins()) {
            completeSpin(player, inventory, gui, playerId, freeSpinState);
            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1.65f);
        animateWinningLines(player, inventory, gui, result, result.getWinningLines(), playerId, true, freeSpinState, false, false);
    }

    private void finishSpin(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, boolean freeSpin, FreeSpinState freeSpinState, boolean expandAfterPostProcessing) {
        if (freeSpin && freeSpinState != null) {
            freeSpinState.addMultiplier(result.getTotalMultiplier());
        }
        moveWinToStash(player, getPayoutBetItems(playerId, freeSpinState), result.getTotalMultiplier());
        gui.displayWinInfo(inventory, result, freeSpin ? freeSpinState : null);

        if (!result.hasWins()) {
            finishPostLineAnimations(player, inventory, gui, result, playerId, freeSpin, freeSpinState, expandAfterPostProcessing);
            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1.5f);
        animateWinningLines(player, inventory, gui, result, result.getWinningLines(), playerId, freeSpin, freeSpinState, expandAfterPostProcessing, true);
    }

    private void animateWinningLines(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, List<WinningLine> winningLines, UUID playerId, boolean freeSpin, FreeSpinState freeSpinState, boolean expandAfterPostProcessing, boolean postProcessAfterLines) {
        new BukkitRunnable() {
            private int lineIndex = 0;
            private int blinkStep = 0;
            private int pauseStep = 0;
            private WinningLine currentLine;

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                if (pauseStep > 0) {
                    if (currentLine != null) {
                        gui.displayResultSlots(inventory, result, currentLine.getWinningSlots());
                    }
                    pauseStep--;
                    return;
                }

                WinningLine winningLine = winningLines.get(lineIndex);
                currentLine = winningLine;

                if (blinkStep % 2 == 0) {
                    gui.displayDiamondSlots(inventory, winningLine.getWinningSlots());
                } else {
                    gui.displayResultSlots(inventory, result, winningLine.getWinningSlots());
                }

                if (blinkStep >= 7) {
                    lineIndex++;
                    gui.displayResultSlots(inventory, result, winningLine.getWinningSlots());

                    if (lineIndex >= winningLines.size()) {
                        gui.displayWinInfo(inventory, result, freeSpin ? freeSpinState : null);
                        if (postProcessAfterLines) {
                            finishPostLineAnimations(player, inventory, gui, result, playerId, freeSpin, freeSpinState, expandAfterPostProcessing);
                        } else {
                            completeSpin(player, inventory, gui, playerId, freeSpinState);
                        }
                        cancel();
                        return;
                    }

                    blinkStep = 0;
                    pauseStep = WIN_LINE_PAUSE_STEPS;
                    return;
                }

                blinkStep++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 2L, 4L);
    }

    private void finishPostLineAnimations(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, boolean freeSpin, FreeSpinState freeSpinState, boolean expandAfterPostProcessing) {
        if (result.countScatters() >= 3) {
            animateScatterTrigger(player, inventory, gui, result, playerId, freeSpin, freeSpinState, expandAfterPostProcessing);
            return;
        }

        if (!result.hasWins() && !expandAfterPostProcessing) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.6f, 1.2f);
        }

        continueAfterPostProcessing(player, inventory, gui, result, playerId, freeSpinState, expandAfterPostProcessing);
    }

    private void continueAfterPostProcessing(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, FreeSpinState freeSpinState, boolean expandAfterPostProcessing) {
        if (expandAfterPostProcessing && freeSpinState != null) {
            handleExpansion(player, inventory, gui, result, playerId, freeSpinState);
            return;
        }

        completeSpin(player, inventory, gui, playerId, freeSpinState);
    }

    private void animateScatterTrigger(Player player, Inventory inventory, BookSlotGUI gui, SlotResult result, UUID playerId, boolean freeSpin, FreeSpinState freeSpinState, boolean expandAfterPostProcessing) {
        Set<Integer> scatterSlots = getScatterSlots(result);

        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.8f, 1.2f);

        new BukkitRunnable() {
            private int blinkStep = 0;

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                if (blinkStep % 2 == 0) {
                    gui.displayDiamondSlots(inventory, scatterSlots);
                } else {
                    gui.displayResult(inventory, result);
                }

                if (blinkStep >= 7) {
                    gui.displayResult(inventory, result);
                    if (freeSpin && freeSpinState != null) {
                        addFreeSpins(player, inventory, gui, result.countScatters(), freeSpinState, playerId);
                        continueAfterPostProcessing(player, inventory, gui, result, playerId, freeSpinStates.get(playerId), expandAfterPostProcessing);
                    } else {
                        startFreeSpins(player, inventory, gui, result.countScatters(), playerId);
                    }
                    cancel();
                    return;
                }

                blinkStep++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 2L, 4L);
    }

    private void startFreeSpins(Player player, Inventory inventory, BookSlotGUI gui, int scatters, UUID playerId) {
        int freeSpins = getFreeSpinAward(scatters);
        List<ItemStack> betItems = activeBetItems.getOrDefault(playerId, Collections.emptyList());

        FreeSpinState freeSpinState = new FreeSpinState(freeSpins, slotEngine.chooseBonusSymbol(), betItems);
        freeSpinStates.put(playerId, freeSpinState);
        blockAutoSpin(playerId, inventory, gui);

        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1.8f);
        animateBonusSymbolSelection(player, inventory, gui, freeSpinState, playerId);
    }

    private void animateBonusSymbolSelection(Player player, Inventory inventory, BookSlotGUI gui, FreeSpinState freeSpinState, UUID playerId) {
        gui.displayFreeSpinState(inventory, freeSpinState);

        new BukkitRunnable() {
            private int step = 0;

            @Override
            public void run() {
                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    cancel();
                    return;
                }

                if (step < 16) {
                    gui.displayBonusSymbol(inventory, slotEngine.chooseBonusSymbol());

                    if (step % 2 == 0) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25f, 1.6f + (step * 0.03f));
                    }
                } else {
                    gui.displayFreeSpinState(inventory, freeSpinState);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.7f);
                    completeSpin(player, inventory, gui, playerId, freeSpinState);
                    cancel();
                    return;
                }

                step++;
            }
        }.runTaskTimer(casinoHandler.getPlugin(), 2L, 2L);
    }

    private void addFreeSpins(Player player, Inventory inventory, BookSlotGUI gui, int scatters, FreeSpinState freeSpinState, UUID playerId) {
        freeSpinState.addSpins(getFreeSpinAward(scatters));
        freeSpinStates.put(playerId, freeSpinState);

        gui.displayFreeSpinState(inventory, freeSpinState);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.8f);
    }

    private int getFreeSpinAward(int scatters) {
        return switch (Math.min(scatters, 5)) {
            case 3 -> 10;
            case 4 -> 15;
            default -> 20;
        };
    }

    private Set<Integer> getScatterSlots(SlotResult result) {
        Set<Integer> scatterSlots = new HashSet<>();

        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                if (result.getSymbol(row, reel).isScatter()) {
                    scatterSlots.add(BookSlotGUI.getReelSlot(row, reel));
                }
            }
        }

        return scatterSlots;
    }

    private void completeSpin(Player player, Inventory inventory, BookSlotGUI gui, UUID playerId, FreeSpinState freeSpinState) {
        if (freeSpinState != null && !freeSpinState.isActive()) {
            freeSpinStates.remove(playerId);
            gui.displayFreeSpinState(inventory, null);
        }

        if (freeSpinState == null || !freeSpinState.isActive()) {
            activeBetItems.remove(playerId);
        }

        spinningPlayers.remove(playerId);
        continueAutoSpin(player, inventory, gui, playerId);
    }

    private void continueAutoSpin(Player player, Inventory inventory, BookSlotGUI gui, UUID playerId) {
        FreeSpinState activeFreeSpinState = freeSpinStates.get(playerId);
        if (activeFreeSpinState != null && activeFreeSpinState.isActive()) {
            blockAutoSpin(playerId, inventory, gui);
            return;
        }

        if (!autoSpinPlayers.contains(playerId)) {
            return;
        }

        if (!casinoHandler.getSession(player).hasBet()) {
            disableAutoSpin(playerId, inventory, gui);
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!autoSpinPlayers.contains(playerId)) {
                    return;
                }

                if (!canContinue(player, inventory)) {
                    abortSpin(playerId);
                    return;
                }

                if (!casinoHandler.getSession(player).hasBet()) {
                    disableAutoSpin(playerId, inventory, gui);
                    return;
                }

                spin(player, inventory);
            }
        }.runTaskLater(casinoHandler.getPlugin(), 10L);
    }

    private void disableAutoSpin(UUID playerId, Inventory inventory, BookSlotGUI gui) {
        autoSpinPlayers.remove(playerId);
        gui.displayAutoSpin(inventory, false);
    }

    private void blockAutoSpin(UUID playerId, Inventory inventory, BookSlotGUI gui) {
        autoSpinPlayers.remove(playerId);
        gui.displayAutoSpinBlocked(inventory);
    }

    private void abortSpin(UUID playerId) {
        spinningPlayers.remove(playerId);
        autoSpinPlayers.remove(playerId);
    }

    private List<ItemStack> consumeBet(Player player, BookSlotGUI gui, Inventory inventory) {
        ItemStack betItem = casinoHandler.getSession(player).consumeNextBetItem();
        gui.displayBetPreview(inventory, casinoHandler.getSession(player));

        if (betItem == null) {
            return Collections.emptyList();
        }

        return List.of(betItem);
    }

    private List<ItemStack> getPayoutBetItems(UUID playerId, FreeSpinState freeSpinState) {
        if (freeSpinState != null) {
            return freeSpinState.getBaseBetItems();
        }

        return activeBetItems.getOrDefault(playerId, Collections.emptyList());
    }

    private void moveWinToStash(Player player, List<ItemStack> betItems, int totalMultiplier) {
        List<ItemStack> rewards = calculateRewards(betItems, totalMultiplier);
        if (!rewards.isEmpty()) {
            casinoHandler.getSession(player).addToStash(rewards);
        }
    }

    private List<ItemStack> calculateRewards(List<ItemStack> betItems, int totalMultiplier) {
        if (totalMultiplier <= 0) {
            return Collections.emptyList();
        }

        List<ItemStack> rewards = new ArrayList<>();

        for (ItemStack item : betItems) {
            int total = item.getAmount() * totalMultiplier;

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

    private boolean canContinue(Player player, Inventory inventory) {
        if (!player.isOnline()) {
            return false;
        }

        Inventory openInventory = player.getOpenInventory().getTopInventory();
        if (openInventory != inventory) {
            return false;
        }

        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)) {
            return false;
        }

        return holder.getType().equals("book-slot");
    }

    private void playSpinSound(Player player, int step) {
        if (isReelStopStep(step)) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.9f, 1.2f + (step * 0.02f));
            return;
        }

        if (step % 3 == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25f, 1.8f);
        }
    }

    private boolean isReelStopStep(int step) {
        for (int stopStep : REEL_STOP_STEPS) {
            if (step == stopStep) {
                return true;
            }
        }

        return false;
    }
}
