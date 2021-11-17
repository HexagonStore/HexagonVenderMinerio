package com.maria.autovenderminerio.ores;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.rewards.RewardManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreManager {

    private final SelfSellOrePlugin main;
    private final FileConfiguration config;

    private final RewardManager rewardManager;

    @Getter
    private final Map<String, Ore> oreMap;

    public OreManager(SelfSellOrePlugin main) {
        this.main = main;

        config = main.getOresFile().getConfig();

        rewardManager = main.getRewardManager();

        oreMap = new HashMap<>();

        setupOre();
    }

    private void addOre(String path, Ore ore) {
        oreMap.put(path, ore);
    }

    public Ore getOre(ItemStack block) {
        return oreMap.entrySet().stream().map(Map.Entry::getValue).filter(ore -> ore.getOre().isSimilar(block)).findFirst().orElse(null);
    }

    private void setupOre() {
        for (String path : config.getConfigurationSection("Minerios").getKeys(false)) {
            ConfigurationSection key = config.getConfigurationSection("Minerios." + path);

            Material material = Material.valueOf(key.getString("Bloco").toUpperCase().split(":")[0]);
            int data = Integer.parseInt(key.getString("Bloco").split(":")[1]);
            double price = key.getDouble("Valor");

            List<String> rewardList = key.getStringList("Recompensas");
            ItemStack block = new ItemStack(material, 1, (short) data);

            Ore ore = new Ore(block, rewardList, price);
            addOre(path, ore);
        }

    }

}
