package com.maria.autovenderminerio.methods;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.bonus.BonusManager;
import com.maria.autovenderminerio.ores.Ore;
import com.maria.autovenderminerio.rewards.RewardManager;
import com.maria.autovenderminerio.utils.ActionBarAPI;
import com.maria.autovenderminerio.utils.Format;
import com.maria.autovenderminerio.utils.TranslateItems;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MinedOreMethods {

    protected SelfSellOrePlugin main;

    private final RewardManager rewardManager;
    private final BonusManager bonusManager;

    private final FileConfiguration config;

    public MinedOreMethods(SelfSellOrePlugin main) {
        this.main = main;

        rewardManager = main.getRewardManager();
        bonusManager = main.getBonusManager();

        config = main.getBonusFile().getConfig();
    }

    public void playerMining(Player player, Ore ore, Block block) {
        double price = ore.getPrice();
        double[] bonusPercentage = bonusManager.getBonus(player, price);
        double percentageResult = bonusPercentage[0];

        price += percentageResult;
        price *= getFortune(player);

        main.getEconomy().depositPlayer(player, price);
        sendActionBar(player, ore, block);
        sendReward(player, ore);
    }

    private void sendActionBar(Player player, Ore ore, Block block) {
        double price = ore.getPrice();
        double[] bonusPercentage = bonusManager.getBonus(player, price);
        double percentageResult = bonusPercentage[0];
        double percentage = bonusPercentage[1];

        price += percentageResult;
        price *= getFortune(player);

        String vip = bonusManager.getBonusVIP(player);
        String style = config.getString("Estilo").replace("{bonus}", Format.formatNumber(percentageResult)).replace("{porcentagem}", "" + percentage + "%").replace("{vip}", vip);
        String blockType = TranslateItems.valueOf(block.getType(), block.getData()).toString();

        String actionBar = main.getConfig().getString("ActionBar").replace("&", "§").replace("{valor}", Format.formatNumber(price)).replace("{bloco}", blockType).replace("{bonus}", style);

        ActionBarAPI.sendActionBar(actionBar, player);
    }

    public void sendReward(Player player, Ore ore) {
        rewardManager.raffleReward(player, ore);
    }

    public boolean playerIsInLockedWorld(Player player) {
        final List<String> lockedWorld = main.getConfig().getStringList("Mundos bloqueados");
        return lockedWorld.contains(player.getWorld().getName());
    }

    private int getFortune(Player player) {
        ItemStack item = player.getItemInHand();
        if (item != null && item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1;

        return 1;
    }

}
