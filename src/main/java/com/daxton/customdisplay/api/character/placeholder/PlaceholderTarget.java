package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlaceholderTarget {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlaceholderTarget(){

    }

    public static String valueOf(LivingEntity entity, String inputString){
        String outputString = "0";
        String key = inputString.replace("_target","").replace(">","");

        if(entity instanceof Player){
            if(key.toLowerCase().contains("<cd_base_")){
                outputString = PlaceholderBase.valueOf(entity,null,key);
            }
            if(key.toLowerCase().contains("<cd_player_")){
                outputString = PlaceholderPlayer.valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_class_")){
                outputString = PlaceholderClass.valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_mmocore_")){
                outputString = PlaceholderMMOCore.valueOf(entity);
            }
            if(key.toLowerCase().contains("<cd_attribute_")){
                outputString = PlaceholderAttributes.valueOf(entity, key);
            }
        }else {
            if(key.toLowerCase().contains("<cd_base_")){
                outputString = PlaceholderBase.valueOf(entity,null,key);
            }
            if(key.toLowerCase().contains("<cd_mythic_")){
                outputString = PlaceholderMythic.valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_modelengine_")){
                outputString = PlaceholderModelEngine.valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_attribute_")){
                outputString = PlaceholderAttributes.valueOf(entity, key);
            }
        }
        return outputString;
    }

}
