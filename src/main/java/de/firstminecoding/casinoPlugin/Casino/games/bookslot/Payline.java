package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

public enum Payline {
    MIDDLE("Middle", new int[]{1, 1, 1, 1, 1}),
    TOP("Top", new int[]{0, 0, 0, 0, 0}),
    BOTTOM("Bottom", new int[]{2, 2, 2, 2, 2}),
    V("V", new int[]{0, 1, 2, 1, 0}),
    INVERTED_V("Inverted V", new int[]{2, 1, 0, 1, 2}),
    ZIGZAG_DOWN("Zigzag Down", new int[]{0, 0, 1, 2, 2}),
    ZIGZAG_UP("Zigzag Up", new int[]{2, 2, 1, 0, 0}),
    STAIRS_DOWN("Stairs Down", new int[]{0, 1, 1, 1, 2}),
    STAIRS_UP("Stairs Up", new int[]{2, 1, 1, 1, 0}),
    CENTER_WAVE("Center Wave", new int[]{1, 0, 1, 2, 1});

    private final String displayName;
    private final int[] rows;

    Payline(String displayName, int[] rows) {
        this.displayName = displayName;
        this.rows = rows;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRow(int reel) {
        return rows[reel];
    }
}
