package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;

public class PlaceholderMythic {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderMythic(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        String uuidString = entity.getUniqueId().toString();
        String key = inputString.replace(" ","").replace("<cd_mythic_class_stats_","");

        if(MobManager.mythicMobs_mobID_Map.get(uuidString) != null){

            String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);
            if(inputString.toLowerCase().contains("<cd_mythic_id")){
                outputString = MobManager.mythicMobs_mobID_Map.get(uuidString);
            }
            if(inputString.toLowerCase().contains("<cd_mythic_level")){
                outputString = MobManager.mythicMobs_Level_Map.get(uuidString);
            }

            if(inputString.toLowerCase().contains("<cd_mythic_class_stats_")){
                outputString = MobManager.mythicMobs_Attr_Map.get(mobID+"."+key);
            }





        }

        return outputString;
    }


}
