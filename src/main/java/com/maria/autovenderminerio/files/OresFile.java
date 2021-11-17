package com.maria.autovenderminerio.files;

import com.maria.autovenderminerio.SelfSellOrePlugin;
import com.maria.autovenderminerio.utils.DateManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@Setter
public class OresFile {

    protected SelfSellOrePlugin main;

    private DateManager file;
    private FileConfiguration config;

    public OresFile(SelfSellOrePlugin main) {
        this.main = main;

        file = new DateManager(main, null, "minerios.yml", false);
        config = file.getConfig();
    }

    public void saveOresFile() {
        try {
            file.saveConfig();

        } catch (Exception ignored) {}
    }

}
