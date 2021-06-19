package de.cooperr.cooperrutils.util;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings implements Listener {

    private final CooperrUtils plugin;
    private final Inventory inventory;

    private final Component inventoryName = Component.text("Settings", NamedTextColor.DARK_BLUE, TextDecoration.BOLD);

    private final Map<Integer, Runnable> settingsRunnables = new HashMap<>();
    private final Map<Integer, ItemStack> itemStacks = new HashMap<>();

    public Settings(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        inventory = plugin.getServer().createInventory(null, 45, inventoryName);
    }

    public void addSetting(int slot, Material material, Component displayName, List<Component> lore, Runnable runnable) {

        settingsRunnables.put(slot, runnable);

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemStack.setItemMeta(itemMeta);

        itemStacks.put(slot, itemStack);
    }

    public void openGui(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!event.getView().title().equals(inventoryName) || !event.getClick().isLeftClick()) {
            return;
        }

        event.setCancelled(true);

        itemStacks.forEach((slot, itemStack) -> {
            if (itemStack.equals(event.getCurrentItem())) {
                settingsRunnables.get(slot).run();
            }
        });
    }
}
