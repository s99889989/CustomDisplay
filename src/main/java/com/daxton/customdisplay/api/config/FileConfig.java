package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileConfig {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    File file = null;

    FileConfiguration fileConfiguration = null;

    public FileConfig(String filePatch){
        file = new File(cd.getDataFolder(),filePatch);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

    }

    public FileConfiguration getConfig(){
        return fileConfiguration;
    }

    public ConfigurationSection getSection(String patch){
        return fileConfiguration.getConfigurationSection(patch);
    }

}
