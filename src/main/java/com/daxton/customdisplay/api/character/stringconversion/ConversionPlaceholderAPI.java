package com.daxton.customdisplay.api.character.stringconversion;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ConversionPlaceholderAPI {


    public ConversionPlaceholderAPI(){

    }

    public String valueOf(LivingEntity self,LivingEntity target, String inputString,String targetKey){
        String outputString = "";
        if(target != null && targetKey.toLowerCase().contains("@=target")){
            if(target instanceof Player){
                Player player = (Player) target;
                outputString = PlaceholderAPI.setPlaceholders(player,inputString);
            }
        }else {
            if(self instanceof Player){
                Player player = (Player) self;
                outputString = PlaceholderAPI.setPlaceholders(player,inputString);
            }
        }
        return outputString;
    }

}
