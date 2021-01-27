package com.daxton.customdisplay.api.character.stringconversion;

import org.bukkit.entity.LivingEntity;

public class ConversionMessage {

    public ConversionMessage(){

    }

    public String valueOf(LivingEntity self, String inputString){
        String outputString = "";
        if(inputString.contains("{#") && inputString.contains("}")){

            outputString = new ConversionColor().valueOf(inputString);

        }

        return outputString;
    }


}
