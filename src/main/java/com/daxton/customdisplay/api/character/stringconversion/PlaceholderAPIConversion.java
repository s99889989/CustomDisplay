package com.daxton.customdisplay.api.character.stringconversion;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlaceholderAPIConversion {


    public PlaceholderAPIConversion(){

    }

    public String valueOf(LivingEntity livingEntity, String inputString){
        String outputString = "";
        if(livingEntity instanceof Player){
            Player player = (Player) livingEntity;
            outputString = PlaceholderAPI.setPlaceholders(player,inputString);
        }else {
            outputString = inputString;
        }
        return outputString;
    }

}
