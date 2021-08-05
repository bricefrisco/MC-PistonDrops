package com.bfrisco.pistondrops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Set;

public class PistonDrops extends JavaPlugin implements Listener {
    private final HashMap<Material, NewDrop> data = new HashMap<>();

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            NewDrop d = data.get(block.getType());
            if (d == null) continue;
            block.setType(Material.AIR);
            if (d.getNewDrop() != null) {
                block.getWorld().dropItem(block.getLocation(), new ItemStack(d.getNewDrop(), d.getQuantity()));
            }
        }
    }

    public void loadConfig() {
        ConfigurationSection items = getConfig().getConfigurationSection("items-to-change");
        if (items == null) throw new IllegalArgumentException("Config must have items-to-change!");
        Set<String> itemNames = items.getKeys(false);
        if (itemNames.isEmpty()) throw new IllegalArgumentException("Found no items in the config!");

        for (String itemName : itemNames) {
            ConfigurationSection changeDropSection = getConfig().getConfigurationSection("items-to-change." + itemName);
            if (changeDropSection == null) {
                getLogger().warning(itemName + " has no new-drop or quantity specified! Will skip this item.");
                continue;
            }

            String dropName = changeDropSection.getString("new-drop");
            if (dropName == null || dropName.trim().isEmpty()) {
                getLogger().warning(itemName + " has no new-drop specified! Will skip this item.");
                continue;
            }

            int quantity = changeDropSection.getInt("quantity");

            Material item;
            Material drop;

            try {
                item = Material.valueOf(itemName);
                if (!dropName.equalsIgnoreCase("none")) {
                    drop = Material.valueOf(dropName);
                } else {
                    drop = null;
                }
            } catch (Exception e) {
                getLogger().warning("Either " + itemName + " or " + dropName + " is an invalid material! Skipping this item.");
                continue;
            }

            data.put(item, new NewDrop(drop, quantity));
        }
    }
}
