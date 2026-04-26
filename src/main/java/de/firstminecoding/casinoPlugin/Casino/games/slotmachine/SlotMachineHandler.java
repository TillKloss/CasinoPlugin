package de.firstminecoding.casinoPlugin.Casino.games.slotmachine;

import de.firstminecoding.casinoPlugin.Casino.core.CasinoHandler;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoInventoryHolder;
import de.firstminecoding.casinoPlugin.Casino.core.CasinoSession;
import de.firstminecoding.casinoPlugin.Casino.payout.PayoutGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static de.firstminecoding.casinoPlugin.Casino.util.RewardUtil.multiplyItems;

public class SlotMachineHandler {
    private final CasinoHandler casinoHandler;
    private final JavaPlugin plugin;

    public SlotMachineHandler(CasinoHandler casinoHandler, JavaPlugin plugin) {
        this.casinoHandler = casinoHandler;
        this.plugin = plugin;
    }

    public void openSlotMachineInventory(Player player) {
        player.openInventory(new SlotMachineGUI().createSlotMachineInventory(casinoHandler.getSession(player)));
    }

    public void openBetInventory(Player player) {
        player.openInventory(new SlotMachineGUI().createBetInventory(casinoHandler.getSession(player)));
    }

    public void handleBetSave(Player player, Inventory inventory) {
        saveBetFromInventory(player, inventory);
        openSlotMachineInventory(player);
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

    public void startSlotMachine(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(inventory.getHolder() instanceof CasinoInventoryHolder holder)
                || !holder.getType().equals("slot-machine")) return;

        CasinoSession session = casinoHandler.getSession(player);

        if (session.isSpinning()) return;
        if (!session.hasBet()) return;
        session.setSpinning(true);

        new SlotMachineGUI().setSpinButton(inventory, Material.RED_STAINED_GLASS_PANE,
                Component.text("...", NamedTextColor.RED, TextDecoration.BOLD));

        SlotMachineGame slotMachineGame = new SlotMachineGame();
        slotMachineGame.startSlotMachine(player, plugin, result -> {
            new SlotMachineGUI().setSpinButton(inventory, Material.LIME_STAINED_GLASS_PANE,
                    Component.text("SPIN", NamedTextColor.GREEN, TextDecoration.BOLD)
            );
            session.setSpinning(false);

            if (result.isCancelled()) return;

            if (result.isPayback()) {
                openSlotMachineInventory(player);
                return;
            }

            int multiplier = result.getMultiplier();

            if (multiplier > 0) {
                List<ItemStack> rewards = multiplyItems(session.getBetItems(), multiplier);
                player.openInventory(new PayoutGUI().createPayoutInventory(rewards, "slot-machine"));
            } else {
                session.clearBet();
                openSlotMachineInventory(player);
            }
        });
    }
}
