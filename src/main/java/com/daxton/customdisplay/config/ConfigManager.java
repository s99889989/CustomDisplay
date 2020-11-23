package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.util.ConfigUtil;
import com.daxton.customdisplay.util.FolderConfigUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;
    public FileConfiguration boos_bar_config;
    public FileConfiguration language;


    public ConfigManager(CustomDisplay plugin){
        cd = plugin;
        new FolderConfigUtil();

        config = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boos_bar_config = ConfigMapManager.getFileConfigurationMap().get("BoosBarDisplay.yml");
        language = ConfigMapManager.getFileConfigurationMap().get("Language_"+config.getString("Language")+".yml");

    }

}
