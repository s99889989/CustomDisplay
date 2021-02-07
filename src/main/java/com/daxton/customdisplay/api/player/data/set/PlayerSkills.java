package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerSkills {



    public PlayerSkills(){

    }

    /**把技能存在Map**/
    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        try {
            List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Skills").getKeys(false));
            if(!(attrStatsList.isEmpty()) && attrStatsList.size() > 0){
                for(String attrName : attrStatsList){
                    int level = playerConfig.getInt(uuidString+".Skills."+attrName+".level");
                    int use = playerConfig.getInt(uuidString+".Skills."+attrName+".use");
                    point_Map.put(attrName+"_level",String.valueOf(level));
                    point_Map.put(attrName+"_use",String.valueOf(use));

                }
            }
        }catch (Exception exception){

        }

    }

    /**設定單個技能等級**/
    public void setOneMap(Player player, String attrName, int amount){

        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            Map<String,String> skills_Map = playerData.skills_Map;
            if(!(skills_Map.isEmpty()) && skills_Map.size() > 0){
                try {
                    int nowValue = Integer.valueOf(skills_Map.get(attrName));
                    int newValue = nowValue + amount;
                    if(newValue >= 0){
                        skills_Map.put(attrName,String.valueOf(newValue));

                    }
                }catch (NumberFormatException exception){

                }
            }

        }


    }

}
