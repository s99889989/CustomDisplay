package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import org.bukkit.entity.LivingEntity;

import java.util.Arrays;

public class Equals {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Equals(){

    }

    public boolean setEquals(LivingEntity self, LivingEntity target, String firstString, String taskID){
        boolean outputBoolean = false;

        String[] strings = firstString.split("=");
        if(strings.length == 3){
            String keyLeft = strings[1];
            String keyRight = strings[2].replace("]","");
            keyLeft = ConversionMain.valueOf(self,target,keyLeft);
            keyRight = ConversionMain.valueOf(self,target,keyRight);
            if(keyLeft.equals(keyRight)){
                outputBoolean = true;
            }

        }
        return outputBoolean;
    }


}
