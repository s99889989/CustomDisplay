package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.LoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerSkills {

    public PlayerSkills(){

    }

    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();

        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Skills").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int level = playerConfig.getInt(uuidString+".Skills."+attrName+".level");
                int use = playerConfig.getInt(uuidString+".Skills."+attrName+".level");
                point_Map.put(attrName+"_level",String.valueOf(level));
                point_Map.put(attrName+"_use",String.valueOf(use));
            }
        }

    }

}
