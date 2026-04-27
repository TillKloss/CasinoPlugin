package de.firstminecoding.casinoPlugin.Casino.games.dice;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.payout.PayoutGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

import static de.firstminecoding.casinoPlugin.Casino.util.RewardUtil.multiplyItems;


public class DiceHandler {
    private final CasinoHandler casinoHandler;
    private final JavaPlugin plugin;

    public DiceHandler(CasinoHandler casinoHandler, JavaPlugin plugin) {
        this.casinoHandler = casinoHandler;
        this.plugin = plugin;
    }

    public void openDiceInventory(Player player) {
        player.openInventory(new DiceGUI().createDiceInventory(casinoHandler.getSession(player)));
    }

    public void selectNumber(Player player, int number) {
        CasinoSession session = casinoHandler.getSession(player);

        if (session.isDiceRolling()) return;

        session.toggleDiceNumber(number);
        openDiceInventory(player);
    }

    public void startDice(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)
                || !holder.getType().equals("dice")) return;

        CasinoSession session = casinoHandler.getSession(player);

        if (session.isDiceRolling()) return;
        if (!session.hasBet()) return;
        if (session.getSelectedDiceNumbers().isEmpty()) return;

        session.setDiceRolling(true);

        new DiceGUI().setRollButton(inventory, Material.RED_STAINED_GLASS_PANE,
                Component.text("...", NamedTextColor.RED, TextDecoration.BOLD));

        DiceGame game = new DiceGame();
        game.startDice(player, plugin, result -> {

            session.setDiceRolling(false);

            if (result == -1) return;

            Set<Integer> selected = session.getSelectedDiceNumbers();

            if (selected.contains(result)) {

                int size = selected.size();
                int multiplier = switch (size) {
                    case 1 -> 6;
                    case 2 -> 3;
                    case 3 -> 2;
                    default -> 0;
                };

                List<ItemStack> rewards = multiplyItems(session.getBetItems(), multiplier);

                player.openInventory(new PayoutGUI().createPayoutInventory(rewards, "dice"));

            } else {
                session.clearBet();
                openDiceInventory(player);
            }
        });
    }
}
