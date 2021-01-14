package com.daxton.customdisplay.api.character.placeholder;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlaceholderSelf2 {

    public PlaceholderSelf2(){

    }

    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "";
        String key = inputString.replace("_self","").replace(">","");

        if(entity instanceof Player){
            if(key.toLowerCase().contains("<cd_base_")){
                outputString = new PlaceholderBase().valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_player_")){
                outputString = new PlaceholderPlayer().valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_class_")){

            }
            if(key.toLowerCase().contains("<cd_mmocore_")){

            }
        }else {
            if(key.toLowerCase().contains("<cd_base_")){
                outputString = new PlaceholderBase().valueOf(entity,key);
            }
            if(key.toLowerCase().contains("<cd_mythic_")){

            }
        }
        return outputString;
    }

}
