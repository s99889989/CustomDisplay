package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class PlaceholderClass {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderClass(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        if(entity instanceof Player){
            Player player = (Player) entity;
            outputString = entityPlayer(player, inputString);
        }else {
            outputString = entityOther(entity, inputString);
        }
        return outputString;
    }

    public String entityPlayer(Player player, String inputString){
        String outputString = "0";
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        String value = inputString.replace(" ","").replace("<cd_class_level_","").replace("<cd_class_point_","").replace("<cd_class_attr_point_","").replace("<cd_class_attr_stats_","").replace("<cd_class_eqm_stats_","");

        if(playerConfig != null){
            if(inputString.toLowerCase().contains("_class_name")){
                outputString = playerConfig.getString(uuidString+".Class_Name");
            }
        }
        cd.getLogger().info(inputString+" : "+value);
        if(playerData != null){
            if(inputString.toLowerCase().contains("_class_level_")){
                if(playerData.level_Map.get(value) != null){
                    outputString = playerData.level_Map.get(value);
                }
            }
            if(inputString.toLowerCase().contains("_class_point_")){

            }
            if(inputString.toLowerCase().contains("_class_attr_point_")){

            }
            if(inputString.toLowerCase().contains("_class_attr_stats_")){

            }
            if(inputString.toLowerCase().contains("_class_eqm_stats_")){

            }
        }

        return outputString;
    }

    public String entityOther(LivingEntity entity, String inputString){
        String outputString = "0";
        String uuidString = entity.getUniqueId().toString();



        return outputString;
    }

}
