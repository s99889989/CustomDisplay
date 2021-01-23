package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerPoint {

    public PlayerPoint(){

    }

    /**初始化點數**/
    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();


        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Point").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int value = playerConfig.getInt(uuidString+".Point."+attrName);
                point_Map.put(attrName,String.valueOf(value));
            }
        }

    }
    /**設定單個點數**/
    public void setOneMap(Player player,String attrName,int amount){


        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            Map<String,String> point_Map = playerData.point_Map;
            if(!(point_Map.isEmpty()) && point_Map.size() > 0){
                int nowValue = Integer.valueOf(point_Map.get(attrName));
                int newValue = nowValue + amount;
                if(newValue >= 0){
                    point_Map.put(attrName,String.valueOf(newValue));
                }
            }

        }


    }

}
