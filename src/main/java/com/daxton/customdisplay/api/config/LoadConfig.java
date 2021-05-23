package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class LoadConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public LoadConfig(){

    }

    /**丟入玩家，讀取玩家設定檔**/
    public static FileConfiguration getPlayerConfig(Player player){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String uuidString = player.getUniqueId().toString();
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if(ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml") == null){
            ConfigMapManager.getFileConfigurationMap().put("Players_"+uuidString+".yml", playerConfig);
        }
        return playerConfig;
    }
    /**讀取職業設定**/
    public static FileConfiguration getClassConfig(String className){
        FileConfiguration classConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Main_Default_Player.yml");
        if(ConfigMapManager.getFileConfigurationMap().get("Class_Main_"+className+".yml") != null){
            classConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Main_"+className+".yml");
        }
        return classConfig;
    }

    /**讀取Level設定**/
    public FileConfiguration getLevelConfig(String levelName){
        File levelFilePatch = new File(cd.getDataFolder(),"Class/Level/"+levelName+".yml");
        FileConfiguration levelConfig = YamlConfiguration.loadConfiguration(levelFilePatch);
        return levelConfig;
    }

    /**讀取Point設定**/
    public FileConfiguration getAttrPointConfig(String pointName){
        File pointAttrFile = new File(cd.getDataFolder(),"Class/Attributes/Point/"+pointName+".yml");
        FileConfiguration pointAttrConfig = YamlConfiguration.loadConfiguration(pointAttrFile);
        return pointAttrConfig;
    }

}
