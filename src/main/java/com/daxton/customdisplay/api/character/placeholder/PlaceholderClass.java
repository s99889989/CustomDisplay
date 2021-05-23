package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlaceholderClass {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderClass(){

    }

    public static String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        if(entity instanceof Player){
            Player player = (Player) entity;
            outputString = entityPlayer(player, inputString);
        }

        return outputString;
    }

    public static String entityPlayer(Player player, String inputString){
        String outputString = "0";
        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

        String value = inputString.replace(" ","").replace("<cd_class_level_","").replace("<cd_class_point_","").replace("<cd_class_attr_point_","").replace("<cd_class_attr_stats_","").replace("<cd_class_eqm_stats_","");

        if(playerData != null){
            //職業顯示名稱
            if(inputString.toLowerCase().contains("_class_name")){
                outputString = playerData.getClassName();
            }
            //目前魔量
            if(inputString.toLowerCase().contains("_class_nowmana")){
                outputString = String.valueOf(playerData.getMana());
            }
            //最高魔量
            if(inputString.toLowerCase().contains("_class_maxmana")){
                outputString = String.valueOf(playerData.getMaxMana());
            }
            //職業等級
            if(inputString.toLowerCase().contains("_class_level_")){
                outputString = String.valueOf(playerData.getLevel(value));
            }
            //職業點數
            if(inputString.toLowerCase().contains("_class_point_")){
                outputString = String.valueOf(playerData.getPoint(value));
            }
            //屬性點數
            if(inputString.toLowerCase().contains("_class_attr_point_")){
                outputString = String.valueOf(playerData.getAttrPoint(value));
            }
            //人物狀態屬性
            if(inputString.toLowerCase().contains("_class_attr_stats_")){
                String attr = playerData.getState(value);
                outputString = ConversionMain.valueOf(player, null, attr);
            }
            //裝備屬性
            if(inputString.toLowerCase().contains("_class_eqm_stats_")){
                String eqm = playerData.getEqmState(value);
                outputString = ConversionMain.valueOf(player, null, eqm);
            }
            //技能
            if(inputString.toLowerCase().contains("_class_skill_")){
                String key = inputString.replace(" ","").replace("<cd","").replace("_class_skill_","");
                outputString = String.valueOf(playerData.getSkillLevel(key));
            }
            //綁定技能名稱
            if(inputString.toLowerCase().contains("_class_bind_name_")){
                outputString = playerData.getBindName(value);
            }
            //使用使用技能
            if(inputString.toLowerCase().contains("_class_bind_use_")){
                outputString = String.valueOf(playerData.getBindUseLevel(value));
            }

        }
        return outputString;
    }



}
