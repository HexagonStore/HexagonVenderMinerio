package com.maria.autovenderminerio.rewards;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.methods.RewardMethods;
import com.maria.autovenderminerio.ores.Ore;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RewardManager {

    protected SelfSellOrePlugin main;
    private final FileConfiguration config;

    private final RewardMethods rewardMethods;

    @Getter
    private final Map<String, Reward> rewardMap;

    public RewardManager(SelfSellOrePlugin main) {
        this.main = main;

        config = main.getRewardsFile().getConfig();

        rewardMethods = main.getRewardMethods();

        rewardMap = new HashMap<>();

        setupRewards();
    }

    private void addReward(String path, Reward reward) {
        rewardMap.put(path, reward);
    }

    public Reward getReward(String key) {
        return rewardMap.get(key);
    }

    public void raffleReward(Player player, Ore ore) {
        List<Reward> rewardList = new ArrayList<>();
        Random random = new Random();
        Reward reward = null;

        for (String rewards : ore.getRewards()) {
            reward = getReward(rewards);
            rewardList.add(reward);

        }
        Reward randomReward = rewardList.get(random.nextInt(rewardList.size()));
        if (rewardMethods.nextInt(0, 100) <= randomReward.getChance()) {
            for (String commands : randomReward.getCommands())
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("{player}", player.getName()));

            player.sendTitle(randomReward.getTitleLine1(), randomReward.getTitleLine2());
            player.playSound(player.getLocation(), randomReward.getRewardSound(), 1.0F, 1.0F);
        }

    }

    private void setupRewards() {
        for (String path : config.getConfigurationSection("Recompensas").getKeys(false)) {
            ConfigurationSection key = config.getConfigurationSection("Recompensas." + path);

            String titleLine1 = key.getString("Title.Linha1").replace("&", "§");
            String titleLine2 = key.getString("Title.Linha2").replace("&", "§");
            Sound sound = Sound.valueOf(key.getString("Som adquirida").toUpperCase());
            List<String> commands = key.getStringList("Comandos");
            commands = commands.stream().map(cmds -> cmds.replace("&", "§")).collect(Collectors.toList());
            int chance = key.getInt("Chance");

            Reward reward = new Reward(titleLine1, titleLine2, sound, commands, chance);
            addReward(path, reward);
        }

    }

}
