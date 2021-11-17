package com.maria.autovenderminerio;

import com.maria.autovenderminerio.bonus.BonusManager;
import com.maria.autovenderminerio.commands.OreReloadCommand;
import com.maria.autovenderminerio.files.BonusFile;
import com.maria.autovenderminerio.files.OresFile;
import com.maria.autovenderminerio.files.RewardsFile;
import com.maria.autovenderminerio.listeners.MinedOreListener;
import com.maria.autovenderminerio.methods.MinedOreMethods;
import com.maria.autovenderminerio.methods.RewardMethods;
import com.maria.autovenderminerio.ores.OreManager;
import com.maria.autovenderminerio.rewards.RewardManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SelfSellOrePlugin extends JavaPlugin {

    private Economy economy = null;

    private OresFile oresFile;
    private BonusFile bonusFile;
    private RewardsFile rewardsFile;

    private OreManager oreManager;
    private BonusManager bonusManager;
    private RewardManager rewardManager;

    private MinedOreMethods minedOreMethods;
    private RewardMethods rewardMethods;

    @Override
    public void onEnable() {
        if (!hasVault())
            return;

        saveDefaultConfig();
        String pluginName = getDescription().getName();

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§6[" + pluginName + "] §fIniciado com sucesso");
        console.sendMessage("§6[" + pluginName + "] §fEntre em meu Discord");
        console.sendMessage("§6[" + pluginName + "] §fDiscord: §6https://discord.gg/N5TnsKUfQ8");
        console.sendMessage("§6[" + pluginName + "] §fCriado por §6Maria_BR");
        setupEconomy();
        loadFiles();
        registerObjects();
        registerFunctions();
    }

    @Override
    public void onDisable() {
        String pluginName = getDescription().getName();

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§6[" + pluginName + "] §fDesligado");
        console.sendMessage("§6[" + pluginName + "] §fEntre em meu Discord");
        console.sendMessage("§6[" + pluginName + "] §fDiscord: §6https://discord.gg/N5TnsKUfQ8");
        console.sendMessage("§6[" + pluginName + "] §fCriado por §6Maria_BR");
    }

    private void registerFunctions() {
        new OreReloadCommand(this);
        new MinedOreListener(this);
    }

    private void registerObjects() {
        long millis = System.currentTimeMillis();

        rewardMethods = new RewardMethods();

        rewardManager = new RewardManager(this);
        oreManager = new OreManager(this);
        bonusManager = new BonusManager(bonusFile.getConfig());

        minedOreMethods = new MinedOreMethods(this);

        System.out.println("[" + getDescription().getName() + "] Loaded's 5 objects at " + (System.currentTimeMillis() - millis) + "ms");
    }

    private void loadFiles() {
        oresFile = new OresFile(this);
        bonusFile = new BonusFile(this);
        rewardsFile = new RewardsFile(this);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> econ = getServer().getServicesManager().getRegistration(Economy.class);

        if (econ == null)
            return false;

        economy = econ.getProvider();
        return economy != null;
    }

    private boolean hasVault() {
        String pluginName = getDescription().getName();

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("§6[" + pluginName + "] §cNão foi possível Hookar com o §6Vault§c.");
            getServer().getPluginManager().disablePlugin(this);
            return false;

        }
        Bukkit.getConsoleSender().sendMessage("§6[" + pluginName + "] §aVault §fHookado com sucesso.");
        return true;
    }

}
