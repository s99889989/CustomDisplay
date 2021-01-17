package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerLevel {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerLevel(){

    }

    public void setMap(Player player, Map<String,String> level_Map){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Level").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int value = playerConfig.getInt(uuidString+".Level."+attrName);
                level_Map.put(attrName,String.valueOf(value));
            }
        }

    }

}
