package de.firstminecoding.casinoPlugin.Casino.core;

import de.firstminecoding.casinoPlugin.Casino.bet.BetHandler;
import de.firstminecoding.casinoPlugin.Casino.games.bookslot.BookSlotHandler;
import de.firstminecoding.casinoPlugin.Casino.games.coinflip.CoinflipHandler;
import de.firstminecoding.casinoPlugin.Casino.games.dice.DiceHandler;
import de.firstminecoding.casinoPlugin.Casino.games.slotmachine.SlotMachineHandler;
import de.firstminecoding.casinoPlugin.Casino.gui.CasinoGUI;
import de.firstminecoding.casinoPlugin.Casino.payout.PayoutHandler;
import de.firstminecoding.casinoPlugin.Casino.stash.StashHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CasinoHandler {
    private final JavaPlugin plugin;
    private final Map<UUID, CasinoSession> sessions = new HashMap<>();
    private final StashHandler stashHandler;
    private final SlotMachineHandler slotMachineHandler;
    private final BookSlotHandler bookSlotHandler;
    private final CoinflipHandler coinflipHandler;
    private final DiceHandler diceHandler;
    private final PayoutHandler payoutHandler;
    private final BetHandler betHandler;

    public CasinoHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.stashHandler = new StashHandler(this);
        this.slotMachineHandler = new SlotMachineHandler(this, plugin);
        this.bookSlotHandler = new BookSlotHandler(this);
        this.coinflipHandler = new CoinflipHandler(this, plugin);
        this.diceHandler = new DiceHandler(this, plugin);
        this.payoutHandler = new PayoutHandler(this);
        this.betHandler = new BetHandler(this);
    }

    public CasinoSession getSession(Player player) {
        return sessions.computeIfAbsent(
                player.getUniqueId(),
                uuid -> new CasinoSession(uuid)
        );
    }

    public StashHandler getStashHandler() {
        return stashHandler;
    }
    public SlotMachineHandler getSlotMachineHandler() {
        return slotMachineHandler;
    }
    public CoinflipHandler getCoinflipHandler() {return coinflipHandler;}
    public DiceHandler getDiceHandler() {return diceHandler;}
    public BookSlotHandler getBookSlotHandler() {return bookSlotHandler;}
    public PayoutHandler getPayoutHandler() {return payoutHandler;}
    public BetHandler getBetHandler() {return betHandler;}

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void openCasinoInventory(Player player) {
        player.openInventory(new CasinoGUI().createCasinoInventory(getSession(player)));
    }


}
