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
    /**設定單個屬性點**/
    public void setOneMap(Player player,String attrName,int amount){



    }


}
