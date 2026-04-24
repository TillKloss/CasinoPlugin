package de.firstminecoding.casinoPlugin.Casino;

public class SlotMachineResult {
    private final SlotMachineMaterial first;
    private final SlotMachineMaterial second;
    private final SlotMachineMaterial third;
    private final boolean cancelled;

    public SlotMachineResult(SlotMachineMaterial first, SlotMachineMaterial second, SlotMachineMaterial third, boolean cancelled) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.cancelled = false;
    }

    private SlotMachineResult() {
        this.first = null;
        this.second = null;
        this.third = null;
        this.cancelled = true;
    }

    public static SlotMachineResult cancelled() {
        return new SlotMachineResult();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isWin() {
        return first == second && second == third;
    }

    public int getMultiplier() {
        return isWin() ? first.getMultiplier() : 0;
    }
}
