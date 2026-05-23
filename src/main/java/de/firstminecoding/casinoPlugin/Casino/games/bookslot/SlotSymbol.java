package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import org.bukkit.inventory.ItemStack;

public enum SlotSymbol {
    WILD(BookSlotCustomHead.SYMBOL_WILD, 10, true, false, 0, 0, 0),
    SCATTER(BookSlotCustomHead.SYMBOL_SCATTER, 9, false, true, 0, 0, 0),
    PHARAOH(BookSlotCustomHead.SYMBOL_PHARAOH, 8, false, false, 2, 5, 20),
    CLEOPATRA(BookSlotCustomHead.SYMBOL_CLEOPATRA, 7, false, false, 2, 4, 15),
    SPHINX(BookSlotCustomHead.SYMBOL_SPHINX, 6, false, false, 1, 3, 10),
    RING(BookSlotCustomHead.SYMBOL_RING, 5, false, false, 1, 3, 8),
    ACE(BookSlotCustomHead.SYMBOL_ACE, 4, false, false, 1, 2, 5),
    KING(BookSlotCustomHead.SYMBOL_KING, 3, false, false, 1, 2, 4),
    QUEEN(BookSlotCustomHead.SYMBOL_QUEEN, 2, false, false, 1, 1, 3),
    JACK(BookSlotCustomHead.SYMBOL_JOKER, 1, false, false, 1, 1, 3),
    TEN(BookSlotCustomHead.SYMBOL_TEN, 0, false, false, 1, 1, 2);

    private final BookSlotCustomHead head;
    private final int rank;
    private final boolean wild;
    private final boolean scatter;
    private final int payoutThree;
    private final int payoutFour;
    private final int payoutFive;

    SlotSymbol(BookSlotCustomHead head, int rank, boolean wild, boolean scatter, int payoutThree, int payoutFour, int payoutFive) {
        this.head = head;
        this.rank = rank;
        this.wild = wild;
        this.scatter = scatter;
        this.payoutThree = payoutThree;
        this.payoutFour = payoutFour;
        this.payoutFive = payoutFive;
    }

    public ItemStack createItem() {
        return head.createItem();
    }

    public int getRank() {
        return rank;
    }

    public boolean isWild() {
        return wild;
    }

    public boolean isScatter() {
        return scatter;
    }

    public int getPayoutMultiplier(int matches) {
        return switch (matches) {
            case 3 -> payoutThree;
            case 4 -> payoutFour;
            case 5 -> payoutFive;
            default -> 0;
        };
    }

    public boolean canBeFreeSpinBonusSymbol() {
        return !wild && !scatter;
    }
}
