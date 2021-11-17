package com.maria.autovenderminerio.files;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.utils.DateManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@Setter
public class BonusFile {

    protected SelfSellOrePlugin main;

    private DateManager file;
    private FileConfiguration config;

    public BonusFile(SelfSellOrePlugin main) {
        this.main = main;

        file = new DateManager(main, null, "bonus.yml", false);
        config = file.getConfig();
    }

    public void saveBonusFile() {
        try {
            file.saveConfig();

        } catch (Exception ignored) {
        }
    }

}
