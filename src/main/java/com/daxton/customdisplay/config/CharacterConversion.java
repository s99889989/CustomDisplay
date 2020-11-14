package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.util.ConfigUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CharacterConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public FileConfiguration configuration;

    public ConfigurationSection characterSection;


    public CharacterConversion() {
        new ConfigUtil("Character\\Example.yml");
        //if(file.isDirectory());
        File loadFile = new File(cd.getDataFolder(),"Character");
        String[] list1 = loadFile.list();
        for (String key : list1) {
            //cd.getLogger().info(key);
            File filekey = new File(cd.getDataFolder(),"Character\\"+key);
            FileConfiguration configkey = YamlConfiguration.loadConfiguration(filekey);
            ConfigurationSection configSection = configkey.getConfigurationSection("");
            for(String keySection : configSection.getKeys(false)){

            }
        }


    }
}
