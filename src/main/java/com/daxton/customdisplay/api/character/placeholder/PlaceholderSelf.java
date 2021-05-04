package com.daxton.customdisplay.api.character.placeholder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlaceholderSelf {

    public PlaceholderSelf(){

    }

    public static String valueOf(LivingEntity entity,LivingEntity target, String inputString){
        String outputString = "0";
        String key = inputString.replace("_self","").replace(">","");

        if(entity instanceof Player){
            if(key.toLowerCase().contains("<cd_base_")){
                outputString = PlaceholderBase.valueOf(entity,target,key);
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
                outputString = PlaceholderBase.valueOf(entity,target,key);
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
