package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.LoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerPoint {

    public PlayerPoint(){

    }

    public void setMap(Player player, Map<String,String> point_Map){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Point").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int value = playerConfig.getInt(uuidString+".Point."+attrName);
                point_Map.put(attrName,String.valueOf(value));
            }
        }

    }

}
