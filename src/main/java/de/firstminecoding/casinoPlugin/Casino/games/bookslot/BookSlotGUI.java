package de.firstminecoding.casinoPlugin.Casino.games.bookslot;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoPanes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.List;

public class BookSlotGUI {
    private static final int[][] REEL_SLOTS = {
            {11, 12, 13, 14, 15},
            {20, 21, 22, 23, 24},
            {29, 30, 31, 32, 33}
    };

    public static int getReelSlot(int row, int reel) {
        return REEL_SLOTS[row][reel];
    }

    public Inventory createBookSlotInventory(CasinoSession session) {
        return createBookSlotInventory(session, null);
    }

    public Inventory createBookSlotInventory(CasinoSession session, FreeSpinState freeSpinState) {
        Component inventoryTitle = Component.text("Casino - Book Slot", NamedTextColor.GOLD, TextDecoration.BOLD);
        Inventory inventory = Bukkit.createInventory(new CasinoInventoryHolder("book-slot"), 54, inventoryTitle);

        ItemStack border = frame();

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, border);
        }

        fillTopControls(inventory, session);
        fillReels(inventory);
        fillSideInfo(inventory);
        fillBottomControls(inventory);
        displayAutoSpin(inventory, false);
        displayFreeSpinState(inventory, freeSpinState);
        if (freeSpinState != null && freeSpinState.isActive()) {
            displayAutoSpinBlocked(inventory);
        }

        return inventory;
    }

    public void displayResult(Inventory inventory, SlotResult result) {
        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                inventory.setItem(REEL_SLOTS[row][reel], symbol(result.getSymbol(row, reel)));
            }
        }
    }

    public void displayResultSlots(Inventory inventory, SlotResult result, Collection<Integer> slots) {
        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                int slot = REEL_SLOTS[row][reel];
                if (slots.contains(slot)) {
                    inventory.setItem(slot, symbol(result.getSymbol(row, reel)));
                }
            }
        }
    }

    public void displayHighlightedResult(Inventory inventory, SlotResult result, Collection<Integer> highlightedSlots) {
        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                int slot = REEL_SLOTS[row][reel];
                inventory.setItem(slot, symbol(result.getSymbol(row, reel), highlightedSlots.contains(slot)));
            }
        }
    }

    public void displayDiamondSlots(Inventory inventory, Collection<Integer> slots) {
        for (int slot : slots) {
            inventory.setItem(slot, new ItemStack(Material.DIAMOND));
        }
    }

    public void displayReel(Inventory inventory, int reel, SlotSymbol[] symbols) {
        for (int row = 0; row < SlotResult.ROWS; row++) {
            inventory.setItem(REEL_SLOTS[row][reel], symbol(symbols[row]));
        }
    }

    public void displayBoardSymbol(Inventory inventory, SlotSymbol slotSymbol) {
        ItemStack item = symbol(slotSymbol);

        for (int row = 0; row < SlotResult.ROWS; row++) {
            for (int reel = 0; reel < SlotResult.REELS; reel++) {
                inventory.setItem(REEL_SLOTS[row][reel], item);
            }
        }
    }

    public void displayWinInfo(Inventory inventory, SlotResult result) {
        ItemStack win = withLore(BookSlotCustomHead.LAST_WIN.createItem(),
                List.of(
                        Component.text("Current win: " + result.getTotalMultiplier() + "x", NamedTextColor.GRAY),
                        Component.text("Lines: " + result.getWinningLines().size(), NamedTextColor.GRAY),
                        Component.text("Scatters: " + result.countScatters(), NamedTextColor.GRAY)
                ));

        inventory.setItem(49, win);
        inventory.setItem(50, win);
        inventory.setItem(51, win);
    }

    public void displayWinInfo(Inventory inventory, SlotResult result, FreeSpinState freeSpinState) {
        if (freeSpinState == null) {
            displayWinInfo(inventory, result);
            return;
        }

        ItemStack win = withLore(BookSlotCustomHead.LAST_WIN.createItem(),
                List.of(
                        Component.text("Free spins win: " + freeSpinState.getTotalMultiplier() + "x", NamedTextColor.GRAY),
                        Component.text("Last spin: " + result.getTotalMultiplier() + "x", NamedTextColor.GRAY),
                        Component.text("Remaining: " + freeSpinState.getRemainingSpins(), NamedTextColor.GRAY)
                ));

        inventory.setItem(49, win);
        inventory.setItem(50, win);
        inventory.setItem(51, win);
    }

    public void displayFreeSpinState(Inventory inventory, FreeSpinState freeSpinState) {
        int remainingSpins = freeSpinState != null && freeSpinState.isActive()
                ? freeSpinState.getRemainingSpins()
                : 0;

        inventory.setItem(47, number(remainingSpins / 10));
        inventory.setItem(48, number(remainingSpins % 10));

        if (freeSpinState != null) {
            ItemStack bonus = freeSpinState.getBonusSymbol().createItem();
            inventory.setItem(17, bonus);
            inventory.setItem(26, bonusLabelItem());
            inventory.setItem(35, bonus);
            return;
        }

        ItemStack feature = featureItem();
        inventory.setItem(17, feature);
        inventory.setItem(26, slotInfoItem());
        inventory.setItem(35, feature);
    }

    public void displayBonusSymbol(Inventory inventory, SlotSymbol symbol) {
        ItemStack bonus = symbol.createItem();
        inventory.setItem(17, bonus);
        inventory.setItem(26, bonusLabelItem());
        inventory.setItem(35, bonus);
    }

    public void displayBetPreview(Inventory inventory, CasinoSession session) {
        for (int slot = 2; slot <= 6; slot++) {
            inventory.setItem(slot, frame());
        }

        fillBetPreview(inventory, session);
    }

    public void displayAutoSpin(Inventory inventory, boolean enabled) {
        ItemStack auto = autoSpinItem(enabled);
        inventory.setItem(46, auto);
        inventory.setItem(52, auto);
    }

    public void displayAutoSpinBlocked(Inventory inventory) {
        ItemStack auto = BookSlotCustomHead.AUTO_SPIN.createItem();
        ItemMeta meta = auto.getItemMeta();

        if (meta != null) {
            meta.displayName(Component.text(">> Auto Spin <<", NamedTextColor.RED, TextDecoration.BOLD));
            meta.lore(List.of(
                    Component.text("Disabled during free spins", NamedTextColor.RED),
                    Component.text("Free spins are played manually", NamedTextColor.GRAY)
            ));
            auto.setItemMeta(meta);
        }

        inventory.setItem(46, auto);
        inventory.setItem(52, auto);
    }

    private void fillTopControls(Inventory inventory, CasinoSession session) {
        ItemStack bet = withLore(BookSlotCustomHead.BET.createItem(),
                List.of(
                        Component.text("Edit the current item bet", NamedTextColor.GRAY),
                        Component.text("Up to 5 stacks", NamedTextColor.GRAY)
                ));

        ItemStack info = withLore(BookSlotCustomHead.SPIN_INFO.createItem(),
                List.of(
                        Component.text("Normal Spin", NamedTextColor.GRAY),
                        Component.text("Free Spins: 00", NamedTextColor.GRAY),
                        Component.text("Wins are moved to stash", NamedTextColor.GRAY)
                ));

        inventory.setItem(0, bet);
        inventory.setItem(1, info);
        fillBetPreview(inventory, session);
        inventory.setItem(7, info);
        inventory.setItem(8, CasinoPanes.RED.createItem());
    }

    private void fillBetPreview(Inventory inventory, CasinoSession session) {
        List<ItemStack> betItems = session.getBetItems();

        for (int i = 0; i < betItems.size() && i < 5; i++) {
            inventory.setItem(2 + i, betItems.get(i).clone());
        }
    }

    private void fillReels(Inventory inventory) {
        inventory.setItem(11, symbol(SlotSymbol.SCATTER));
        inventory.setItem(12, symbol(SlotSymbol.PHARAOH));
        inventory.setItem(13, symbol(SlotSymbol.CLEOPATRA));
        inventory.setItem(14, symbol(SlotSymbol.KING));
        inventory.setItem(15, symbol(SlotSymbol.ACE));

        inventory.setItem(20, symbol(SlotSymbol.SPHINX));
        inventory.setItem(21, symbol(SlotSymbol.WILD));
        inventory.setItem(22, symbol(SlotSymbol.RING));
        inventory.setItem(23, symbol(SlotSymbol.QUEEN));
        inventory.setItem(24, symbol(SlotSymbol.JACK));

        inventory.setItem(29, symbol(SlotSymbol.TEN));
        inventory.setItem(30, symbol(SlotSymbol.CLEOPATRA));
        inventory.setItem(31, symbol(SlotSymbol.ACE));
        inventory.setItem(32, symbol(SlotSymbol.SCATTER));
        inventory.setItem(33, symbol(SlotSymbol.PHARAOH));
    }

    private void fillSideInfo(Inventory inventory) {
        ItemStack paytableHigh = withLore(BookSlotCustomHead.PAYOUT_TABLE.createItem(),
                List.of(
                        Component.text("Symbol Multipliers", NamedTextColor.GOLD),
                        Component.text("5 / 4 / 3 of a kind", NamedTextColor.GRAY),
                        Component.text("Pharaoh: 20x / 5x / 2x", NamedTextColor.YELLOW),
                        Component.text("Cleopatra: 15x / 4x / 2x", NamedTextColor.LIGHT_PURPLE),
                        Component.text("Sphinx: 10x / 3x / 1x", NamedTextColor.AQUA),
                        Component.text("Ring: 8x / 3x / 1x", NamedTextColor.GOLD)
                ));
        ItemStack paytableLow = withLore(BookSlotCustomHead.PAYOUT_TABLE.createItem(),
                List.of(
                        Component.text("Low Symbols", NamedTextColor.GOLD),
                        Component.text("A: 5x / 2x / 1x", NamedTextColor.RED),
                        Component.text("K: 4x / 2x / 1x", NamedTextColor.GOLD),
                        Component.text("Q: 3x / 1x / 1x", NamedTextColor.LIGHT_PURPLE),
                        Component.text("J: 3x / 1x / 1x", NamedTextColor.AQUA),
                        Component.text("10: 2x / 1x / 1x", NamedTextColor.GRAY)
                ));
        ItemStack paytableRules = withLore(BookSlotCustomHead.PAYOUT_TABLE.createItem(),
                List.of(
                        Component.text("Rules", NamedTextColor.GOLD),
                        Component.text("10 paylines, left to right", NamedTextColor.GRAY),
                        Component.text("Wild substitutes normal symbols", NamedTextColor.GRAY),
                        Component.text("3/4/5 Scatters = 10/15/20 FS", NamedTextColor.GRAY),
                        Component.text("Free spins choose bonus symbol", NamedTextColor.GRAY)
                ));

        ItemStack feature = featureItem();

        ItemStack info = slotInfoItem();

        inventory.setItem(9, paytableHigh);
        inventory.setItem(18, paytableLow);
        inventory.setItem(27, paytableRules);
        inventory.setItem(17, feature);
        inventory.setItem(26, info);
        inventory.setItem(35, feature);
    }

    private void fillBottomControls(Inventory inventory) {
        ItemStack bet = withLore(BookSlotCustomHead.BET.createItem(),
                List.of(Component.text("Edit the item bet", NamedTextColor.GRAY)));

        ItemStack freeSpinDigit = withLore(BookSlotCustomHead.NUMBER_ZERO.createItem(),
                List.of(
                        Component.text("Shows remaining free spins", NamedTextColor.GRAY),
                        Component.text("Trigger: 3+ Scatters anywhere", NamedTextColor.GRAY)
                ));

        ItemStack win = withLore(BookSlotCustomHead.LAST_WIN.createItem(),
                List.of(
                        Component.text("Current win: 0x", NamedTextColor.GRAY),
                        Component.text("Big Win: 10x+", NamedTextColor.GRAY),
                        Component.text("Mega Win: 50x+", NamedTextColor.GRAY)
                ));

        ItemStack spin = CasinoPanes.LIME.createItem(
                Component.text("W - SPIN", NamedTextColor.GREEN, TextDecoration.BOLD));

        ItemMeta spinMeta = spin.getItemMeta();
        if (spinMeta != null) {
            spinMeta.lore(List.of(
                    Component.text("Starts a visual Book Slot spin", NamedTextColor.GRAY),
                    Component.text("Wins are moved to stash", NamedTextColor.GRAY)
            ));
            spin.setItemMeta(spinMeta);
        }

        inventory.setItem(38, spin);
        inventory.setItem(39, spin);
        inventory.setItem(40, spin);
        inventory.setItem(41, spin);
        inventory.setItem(42, spin);

        inventory.setItem(45, bet);
        inventory.setItem(47, freeSpinDigit);
        inventory.setItem(48, freeSpinDigit);
        inventory.setItem(49, win);
        inventory.setItem(50, win);
        inventory.setItem(51, win);
        inventory.setItem(53, CasinoPanes.RED.createItem());
    }

    private ItemStack autoSpinItem(boolean enabled) {
        ItemStack auto = BookSlotCustomHead.AUTO_SPIN.createItem();
        ItemMeta meta = auto.getItemMeta();

        if (meta != null) {
            meta.displayName(enabled
                    ? Component.text("Auto Spin", NamedTextColor.GREEN, TextDecoration.BOLD)
                    : Component.text("Auto Spin", NamedTextColor.GREEN, TextDecoration.BOLD));
            meta.lore(enabled
                    ? List.of(
                            Component.text("Enabled", NamedTextColor.GREEN),
                            Component.text("Spins until the bet queue is empty", NamedTextColor.GRAY),
                            Component.text("Disables when free spins trigger", NamedTextColor.GRAY)
                    )
                    : List.of(
                            Component.text("Click to enable", NamedTextColor.GRAY),
                            Component.text("Uses one bet stack per spin", NamedTextColor.GRAY),
                            Component.text("Free spins disable auto spin", NamedTextColor.GRAY)
                    ));
            meta.setEnchantmentGlintOverride(enabled);
            auto.setItemMeta(meta);
        }

        return auto;
    }

    private ItemStack symbol(SlotSymbol symbol) {
        return symbol(symbol, false);
    }

    private ItemStack symbol(SlotSymbol symbol, boolean highlighted) {
        ItemStack item = symbol.createItem();
        if (!highlighted) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            item.setItemMeta(meta);
        }

        return item;
    }

    private ItemStack number(int number) {
        BookSlotCustomHead head = switch (Math.max(0, Math.min(9, number))) {
            case 1 -> BookSlotCustomHead.NUMBER_ONE;
            case 2 -> BookSlotCustomHead.NUMBER_TWO;
            case 3 -> BookSlotCustomHead.NUMBER_THREE;
            case 4 -> BookSlotCustomHead.NUMBER_FOUR;
            case 5 -> BookSlotCustomHead.NUMBER_FIVE;
            case 6 -> BookSlotCustomHead.NUMBER_SIX;
            case 7 -> BookSlotCustomHead.NUMBER_SEVEN;
            case 8 -> BookSlotCustomHead.NUMBER_EIGHT;
            case 9 -> BookSlotCustomHead.NUMBER_NINE;
            default -> BookSlotCustomHead.NUMBER_ZERO;
        };

        return withLore(head.createItem(),
                List.of(
                        Component.text("Shows remaining free spins", NamedTextColor.GRAY),
                        Component.text("Trigger: 3+ Scatters anywhere", NamedTextColor.GRAY)
                ));
    }

    private ItemStack featureItem() {
        return createItem(Material.ITEM_FRAME,
                Component.text("Bonus Symbol", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
                List.of(
                        Component.text("Shows the expanding symbol during free spins", NamedTextColor.GRAY),
                        Component.text("2+ matching symbols expand that reel", NamedTextColor.GRAY),
                        Component.text("Empty until free spins start", NamedTextColor.GRAY)
                ));
    }

    private ItemStack bonusLabelItem() {
        return createItem(Material.NAME_TAG,
                Component.text("Bonus Symbol", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD),
                List.of(
                        Component.text("Selected for these free spins", NamedTextColor.GRAY),
                        Component.text("Matching reels expand later", NamedTextColor.GRAY)
                ));
    }

    private ItemStack slotInfoItem() {
        return withLore(BookSlotCustomHead.SLOT_INFO.createItem(),
                List.of(
                        Component.text("Reels: 5", NamedTextColor.GRAY),
                        Component.text("Rows: 3", NamedTextColor.GRAY),
                        Component.text("Payout goes directly to stash", NamedTextColor.GRAY)
                ));
    }

    private ItemStack createItem(Material material, Component name, List<Component> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(name);
            meta.lore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private ItemStack frame() {
        ItemStack item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(Component.empty());
            item.setItemMeta(meta);
        }

        return item;
    }

    private ItemStack withLore(ItemStack item, List<Component> lore) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.lore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}
