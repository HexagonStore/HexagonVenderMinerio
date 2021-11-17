package com.maria.autovenderminerio.bonus;

import lombok.Getter;
import lombok.val;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BonusManager {

    private final FileConfiguration config;

    @Getter
    private final List<Bonus> bonusList;

    @Getter
    private final Map<String, String> bonusName;

    public BonusManager(FileConfiguration config) {
        this.config = config;

        bonusList = new ArrayList<>();
        bonusName = new HashMap<>();

        setupBonus();
    }

    private void addBonus(Bonus bonus) {
        bonusList.add(bonus);
    }

    private void setupBonus() {
        for (String bonusConfig : config.getStringList("Bonus")) {
            String bonusPermission = bonusConfig.split(":")[0];
            String bonusVIP = bonusConfig.split(":")[1].replace("&", "§");
            double bonusPercentage = Double.parseDouble(bonusConfig.split(":")[2]);

            Bonus bonus = new Bonus(bonusPermission, bonusVIP, bonusPercentage);
            addBonus(bonus);
        }

    }

    public double[] getBonus(Player player, double price) {
        for (Bonus bonus : bonusList) {
            String permission = bonus.getBonusPermission();
            String vip = bonus.getBonusVIP();
            double percentage = bonus.getBonusPercentage();

            if (player.hasPermission(permission)) {
                bonusName.put(player.getName(), vip);
                return new double[]{price * percentage / 100, percentage};
            }

        }
        return new double[]{0,0};
    }

    public String getBonusVIP(Player player) {
        return bonusName.getOrDefault(player.getName(), "");
    }

}
