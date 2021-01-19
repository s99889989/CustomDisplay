package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.LoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerBinds {

    public PlayerBinds(){

    }

    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();


        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Binds").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                String skillName = playerConfig.getString(uuidString+".Binds."+attrName+".SkillName");
                int use = playerConfig.getInt(uuidString+".Binds."+attrName+".UseLevel");
                point_Map.put(attrName+"_skillName",skillName);
                point_Map.put(attrName+"_use",String.valueOf(use));
            }
        }

    }

}
