package com.maria.autovenderminerio.listeners;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.api.MinedOreEvent;
import com.maria.autovenderminerio.methods.MinedOreMethods;
import com.maria.autovenderminerio.ores.Ore;
import com.maria.autovenderminerio.ores.OreManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MinedOreListener implements Listener {

    protected SelfSellOrePlugin main;

    private final OreManager oreManager;

    private final MinedOreMethods minedOreMethods;

    public MinedOreListener(SelfSellOrePlugin main) {
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, main);

        oreManager = main.getOreManager();
        minedOreMethods = main.getMinedOreMethods();
    }

    @EventHandler
    void playerMinedOre(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();

        ItemStack blockItem = new ItemStack(block.getType(), 1, block.getData());

        Ore ore = oreManager.getOre(blockItem);

        if (ore == null)
            return;

        String bonusVIP = main.getBonusManager().getBonusVIP(p);
        double[] playerBonus = main.getBonusManager().getBonus(p, ore.getPrice());

        MinedOreEvent minedOreEvent = new MinedOreEvent(p, ore, ore.getPrice(), bonusVIP, playerBonus);
        Bukkit.getPluginManager().callEvent(minedOreEvent);

        if (!minedOreEvent.isCancelled() && !minedOreMethods.playerIsInLockedWorld(p)) {
            minedOreMethods.playerMining(p, ore, block);
            e.setCancelled(true);
            block.setType(Material.AIR);
        }

    }

}
