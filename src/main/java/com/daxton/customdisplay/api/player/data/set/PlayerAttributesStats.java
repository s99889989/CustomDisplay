package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerAttributesStats {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerAttributesStats(){

    }

    public void setMap(Player player, Map<String,String> attributes_Stats_Map){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Attributes_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                File attrFile = new File(cd.getDataFolder(),"Class/Attributes/EntityStats/"+attrName+".yml");
                FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFile);
                List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                for(String attrName2 : attrStatsNameList){
                    attributes_Stats_Map.put(attrName2,"0");
                }

            }

        }

    }

    public void setFormula(Player player, Map<String,String> attributes_Stats_Map){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        List<String> attrStatsList = playerConfig.getStringList(uuidString+".Attributes_Stats");
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                File attrFile = new File(cd.getDataFolder(),"Class/Attributes/EntityStats/"+attrName+".yml");
                FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFile);
                List<String> attrStatsNameList = new ArrayList<>(attrConfig.getConfigurationSection(attrName).getKeys(false));
                for(String attrName2 : attrStatsNameList){
                    String value = attrConfig.getString(attrName+"."+attrName2+".formula");
                    if(value != null){
                        //cd.getLogger().info(value);
                        attributes_Stats_Map.put(attrName2,"0");
                    }
                }

            }

        }

    }

}
