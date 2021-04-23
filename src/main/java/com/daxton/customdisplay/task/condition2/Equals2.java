package com.daxton.customdisplay.task.condition2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringConversion;
import org.bukkit.entity.LivingEntity;

public class Equals2 {

    private boolean result = false;

    public Equals2(){

    }

    public Equals2(LivingEntity self, LivingEntity target, String content, String taskID){
        if(content.contains("~")){
            String stringLeft = content.substring(0, content.indexOf("~"));
            String stringRight = content.substring(content.indexOf("~")+1);

            stringLeft = StringConversion.getString(self,target,stringLeft);
            stringRight = StringConversion.getString(self,target,stringRight);

            if(stringLeft.equals(stringRight)){
                result = true;
            }
        }
    }

    public boolean isResult() {
        return result;
    }

}
