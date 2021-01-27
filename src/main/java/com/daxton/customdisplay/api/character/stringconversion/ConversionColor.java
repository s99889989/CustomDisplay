package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.entity.LivingEntity;

public class ConversionColor {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ConversionColor(){

    }

    public String valueOf(String inputString){
        String outputString = "";
        if(inputString.length() == 9){
            String key1 = inputString.substring(2,3);
            String key2 = inputString.substring(3,4);
            String key3 = inputString.substring(4,5);
            String key4 = inputString.substring(5,6);
            String key5 = inputString.substring(6,7);
            String key6 = inputString.substring(7,8);
            inputString = "§x§"+key1+"§"+key2+"§"+key3+"§"+key4+"§"+key5+"§"+key6;
        }
        outputString = inputString;
        return outputString;
    }


}
