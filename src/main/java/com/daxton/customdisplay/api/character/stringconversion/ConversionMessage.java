package com.daxton.customdisplay.api.character.stringconversion;

import org.bukkit.entity.LivingEntity;

public class ConversionMessage {

    public ConversionMessage(){

    }

    public static String valueOf(String inputString){
        String outputString = "";
        if(inputString.contains("{#") && inputString.contains("}")){

            outputString = ConversionColor.valueOf(inputString);

        }

        return outputString;
    }


}
