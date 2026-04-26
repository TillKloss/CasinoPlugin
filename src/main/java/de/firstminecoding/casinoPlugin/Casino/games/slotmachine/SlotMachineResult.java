package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

public class SlotMachineResult {
    private final SlotMachineMaterial first;
    private final SlotMachineMaterial second;
    private final SlotMachineMaterial third;
    private final boolean cancelled;

    public SlotMachineResult(SlotMachineMaterial first, SlotMachineMaterial second, SlotMachineMaterial third, boolean cancelled) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.cancelled = cancelled;
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
        return first == second && second == third && first != SlotMachineMaterial.COAL;
    }
    public boolean isPayback() {
        if (isWin()) return false;
        return (
                (first == second && first != SlotMachineMaterial.COAL) ||
                (first == third && first != SlotMachineMaterial.COAL) ||
                (second == third && second != SlotMachineMaterial.COAL)
        );
    }

    public int getMultiplier() {
        if (isWin()) return first.getTripleMultiplier();

        if (isPayback()) {
            if (first == second && first != SlotMachineMaterial.COAL) return first.getDoubleMultiplier();
            if (first == third && first != SlotMachineMaterial.COAL) return first.getDoubleMultiplier();
            if (second == third && second != SlotMachineMaterial.COAL) return second.getDoubleMultiplier();
        }

        return 0;
    }
}
