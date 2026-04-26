package de.firstminecoding.casinoPlugin.Casino.games.coinflip;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.payout.PayoutGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static de.firstminecoding.casinoPlugin.Casino.util.RewardUtil.multiplyItems;


public class CoinflipHandler {
    private final CasinoHandler casinoHandler;
    private final JavaPlugin plugin;

    public CoinflipHandler(CasinoHandler casinoHandler, JavaPlugin plugin) {
        this.casinoHandler = casinoHandler;
        this.plugin = plugin;
    }

    public void openCoinflipInventory(Player player) {
        player.openInventory(new CoinflipGUI().createCoinflipInventory(casinoHandler.getSession(player)));
    }

    public void openBetInventory(Player player) {
        player.openInventory(new CoinflipGUI().createBetInventory(casinoHandler.getSession(player)));
    }

    public void handleBetSave(Player player, Inventory inventory) {
        saveBetFromInventory(player, inventory);
        openCoinflipInventory(player);
    }

    public void saveBetFromInventory(Player player, Inventory inventory) {
        List<ItemStack> betItems = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null && item.getType() != Material.AIR) {
                betItems.add(item.clone());
            }
        }

        casinoHandler.getSession(player).setBetItems(betItems);
    }

    public void selectSide(Player player, CoinflipSide side) {
        CasinoSession session = casinoHandler.getSession(player);

        if (session.isCoinflipRunning()) return;
        session.setSelectedCoinflipSide(side);
        openCoinflipInventory(player);
    }

    public void startCoinflip(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)
                || !holder.getType().equals("coinflip")) return;

        CasinoSession session = casinoHandler.getSession(player);

        if (session.isCoinflipRunning()) return;
        if (session.getSelectedCoinflipSide() == null) return;
        if (!session.hasBet()) return;
        session.setCoinflipRunning(true);

        new CoinflipGUI().setStartButton(inventory, Material.RED_STAINED_GLASS_PANE,
                Component.text("...", NamedTextColor.RED, TextDecoration.BOLD));

        CoinflipSide selected = session.getSelectedCoinflipSide();
        CoinflipGame coinflipGame = new CoinflipGame();
        coinflipGame.startCoinflip(player, plugin, result -> {
            session.setCoinflipRunning(false);

            if (result == null) return;

            if (selected == result) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                List<ItemStack> rewards = multiplyItems(session.getBetItems(), 1);
                player.openInventory(new PayoutGUI().createPayoutInventory(rewards, "coinflip"));
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 0.7f);
                session.clearBet();
                openCoinflipInventory(player);
            }
        });
    }
}
