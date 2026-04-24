package de.firstminecoding.casinoPlugin.Casino;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CasinoInventoryHolder implements InventoryHolder {
    private final String type;

    public CasinoInventoryHolder(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
