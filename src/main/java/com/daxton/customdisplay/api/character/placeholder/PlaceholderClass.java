package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlaceholderClass {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderClass(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        if(entity instanceof Player){
            Player player = (Player) entity;
            outputString = entityPlayer(player, inputString);
        }

        return outputString;
    }

    public String entityPlayer(Player player, String inputString){
        String outputString = "0";
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
        String value = inputString.replace(" ","").replace("<cd_class_level_","").replace("<cd_class_point_","").replace("<cd_class_attr_point_","").replace("<cd_class_attr_stats_","").replace("<cd_class_eqm_stats_","");

        if(playerConfig != null){
            if(inputString.toLowerCase().contains("_class_name")){
                outputString = playerConfig.getString(uuidString+".Class_Name");
            }
        }

        if(playerData != null){
            /**目前魔量**/
            if(inputString.toLowerCase().contains("_class_nowmana")){

                if(PlayerManager.player_nowMana.get(uuidString) != null){
                    outputString = String.valueOf(PlayerManager.player_nowMana.get(uuidString));

                }
            }
            /**最高魔量**/
            if(inputString.toLowerCase().contains("_class_maxmana")){

                outputString = String.valueOf(playerData.maxmana);

            }
            if(inputString.toLowerCase().contains("_class_level_")){
                if(playerData.level_Map.get(value) != null){
                    outputString = playerData.level_Map.get(value);
                }
            }
            if(inputString.toLowerCase().contains("_class_point_")){
                if(playerData.point_Map.get(value) != null){
                    outputString = playerData.point_Map.get(value);
                }
            }
            if(inputString.toLowerCase().contains("_class_attr_point_")){
                if(playerData.attributes_Point_Map.get(value) != null){
                    if(playerData.attributes_Point_Map2.get(value) != null){
                        try {
                            double attrNubmer = Double.parseDouble(playerData.attributes_Point_Map.get(value));
                            double addNumber = Double.parseDouble(playerData.attributes_Point_Map2.get(value));
                            outputString = String.valueOf(attrNubmer+addNumber);
                        }catch (NumberFormatException exception){
                            outputString = playerData.attributes_Point_Map.get(value);
                        }
                    }else {
                        outputString = playerData.attributes_Point_Map.get(value);
                    }
                }
            }
            if(inputString.toLowerCase().contains("_class_attr_stats_")){
                if(playerData.attributes_Stats_Map.get(value) != null){
                    if(playerData.attributes_Stats_Map2.get(value) != null){
                        try {
                            double attrNubmer = Double.parseDouble(playerData.attributes_Stats_Map.get(value));
                            double addNumber = Double.parseDouble(playerData.attributes_Stats_Map2.get(value));
                            outputString = String.valueOf(attrNubmer+addNumber);
                        }catch (NumberFormatException exception){
                            outputString = playerData.attributes_Stats_Map.get(value);
                        }
                    }else {
                        outputString = playerData.attributes_Stats_Map.get(value);
                    }
                }
            }
            if(inputString.toLowerCase().contains("_class_eqm_stats_")){
                if(playerData.equipment_Stats_Map.get(value) != null){
                    if(playerData.equipment_Stats_Map2.get(value) != null){
                        try {
                            double attrNubmer = Double.parseDouble(playerData.equipment_Stats_Map.get(value));
                            double addNumber = Double.parseDouble(playerData.equipment_Stats_Map2.get(value));
                            outputString = String.valueOf(attrNubmer+addNumber);
                        }catch (NumberFormatException exception){
                            outputString = playerData.equipment_Stats_Map.get(value);
                        }
                    }else {
                        outputString = playerData.equipment_Stats_Map.get(value);
                    }


                }
            }
            if(inputString.toLowerCase().contains("_class_skill_")){
                String key = inputString.replace(" ","").replace("<cd","").replace("_class_skill_","");
                if(playerData.skills_Map.get(key) != null){
                    outputString = playerData.skills_Map.get(key);
                }
            }
            if(inputString.toLowerCase().contains("_class_bind_name_")){
                if(playerData.binds_Map.get(value+"_skillName") != null){
                    outputString = playerData.binds_Map.get(value+"_skillName");
                }
            }
            if(inputString.toLowerCase().contains("_class_bind_use_")){
                if(playerData.binds_Map.get(value+"_use") != null){
                    outputString = playerData.binds_Map.get(value+"_use");
                }
            }

        }



        return outputString;
    }



}
