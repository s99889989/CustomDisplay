package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.MobData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

public class PlaceholderMythic {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderMythic(){

    }

    public static String valueOf(LivingEntity entity, String inputString){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String outputString = "0";
        String uuidString = entity.getUniqueId().toString();

        if(MobManager.mythicMobs_mobID_Map.get(uuidString) != null){

            String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);

            MobData mobData = MobManager.mob_Data_Map.get(mobID);

            //獲取MM魔物ID
            if(inputString.toLowerCase().contains("<cd_mythic_id")){
                outputString = MobManager.mythicMobs_mobID_Map.get(uuidString);
            }
            //獲取MM魔物等級
            if(inputString.toLowerCase().contains("<cd_mythic_level")){
                if(MobManager.mythicMobs_Level_Map.get(uuidString) != null){
                    ActiveMob activeMob = MobManager.mythicMobs_ActiveMob_Map.get(uuidString);
                    outputString = String.valueOf(activeMob.getLevel());
                    //outputString = MobManager.mythicMobs_Level_Map.get(uuidString);
                }
            }
            //獲取MM魔物派系
            if(inputString.toLowerCase().contains("<cd_mythic_faction")){
                outputString =  mobData.getFaction();
            }
            //獲取MM魔物自訂屬性
            if(inputString.toLowerCase().contains("<cd_mythic_class_stats_")){
                String key = inputString.replace(" ","").replace("<cd_mythic_class_stats_","");
                outputString =  mobData.getCustomState(key);
            }

        }

        return outputString;
    }


}
