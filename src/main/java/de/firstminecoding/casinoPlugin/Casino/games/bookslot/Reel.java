package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import java.util.List;

public class Reel {
    private final List<SlotSymbol> symbols;

    public Reel(List<SlotSymbol> symbols) {
        if (symbols.size() < 3) {
            throw new IllegalArgumentException("A reel needs at least 3 symbols.");
        }

        this.symbols = List.copyOf(symbols);
    }

    public int size() {
        return symbols.size();
    }

    public SlotSymbol getVisibleSymbol(int startIndex, int row) {
        return symbols.get((startIndex + row) % symbols.size());
    }
}
