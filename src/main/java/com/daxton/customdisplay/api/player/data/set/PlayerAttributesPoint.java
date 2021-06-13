package com.daxton.customdisplay.api.player.data.set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerAttributesPoint {

    public PlayerAttributesPoint(){

    }
    /**初始化屬性點**/
    public void setMap(Player player, Map<String,String> attributes_Point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();


        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Attributes_Point").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                int value = playerConfig.getInt(uuidString+".Attributes_Point."+attrName);
                attributes_Point_Map.put(attrName,String.valueOf(value));
            }
        }

    }



}
