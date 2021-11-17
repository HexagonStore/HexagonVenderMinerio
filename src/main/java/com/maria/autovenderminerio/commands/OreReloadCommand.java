package com.maria.autovenderminerio.commands;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.files.BonusFile;
import com.maria.autovenderminerio.files.OresFile;
import com.maria.autovenderminerio.files.RewardsFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class OreReloadCommand implements CommandExecutor {

    protected SelfSellOrePlugin main;

    private final OresFile oresFile;
    private final RewardsFile rewardsFile;
    private final BonusFile bonusFile;

    public OreReloadCommand(SelfSellOrePlugin main) {
        this.main = main;

        main.getCommand("minerios").setExecutor(this);

        oresFile = main.getOresFile();
        rewardsFile = main.getRewardsFile();
        bonusFile = main.getBonusFile();
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] a) {
        if (!s.hasPermission("hexagon.minerios.admin")) {
            s.sendMessage("§cSem permissão.");
            return true;

        }
        if (a.length == 0) {
            s.sendMessage("§6§lHEXAGON §8» §cUtilize: /minerios reload");
            return true;

        }
        if (a[0].equalsIgnoreCase("reload") || a[0].equalsIgnoreCase("rl")) {
            String pluginName = main.getDescription().getName();

            long currentMillis = System.currentTimeMillis();
            Bukkit.getPluginManager().disablePlugin(main);
            Bukkit.getScheduler().cancelTasks(main);
            Bukkit.getServicesManager().unregisterAll(main);
            HandlerList.unregisterAll(main);

            Bukkit.getPluginManager().enablePlugin(main);
            main.reloadConfig();
            oresFile.getFile().reloadConfig();
            rewardsFile.getFile().reloadConfig();
            bonusFile.getFile().reloadConfig();

            s.sendMessage("§6§lHEXAGON §8» §aConfigurações recarregadas em §e" + (System.currentTimeMillis() - currentMillis) + "ms §acom sucesso.");
            s.sendMessage("§6§lHEXAGON §8» §aArquivos recarregados:");
            s.sendMessage(" §8» §econfig.yml");
            s.sendMessage(" §8» §e" + oresFile.getFile().getFileName());
            s.sendMessage(" §8» §e" + rewardsFile.getFile().getFileName());
            s.sendMessage(" §8» §e" + bonusFile.getFile().getFileName());

            System.out.println("[" + pluginName + "] Reloaded configuration files at " + (System.currentTimeMillis() - currentMillis) + "ms");
            System.out.println("[" + pluginName + "] Files reloadeds:");
            System.out.println("[" + pluginName + "]  config.yml");
            System.out.println("[" + pluginName + "]  " + oresFile.getFile().getFileName());
            System.out.println("[" + pluginName + "]  " + rewardsFile.getFile().getFileName());
            System.out.println("[" + pluginName + "]  " + bonusFile.getFile().getFileName());
        }
        return false;
    }

}
