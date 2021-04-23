package com.daxton.customdisplay.task.condition2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;

public class Contains2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private boolean result = false;

    public Contains2(){

    }

    public Contains2(LivingEntity self, LivingEntity target, String content, String taskID){

        if(content.contains("~")){
            String stringLeft = content.substring(0, content.indexOf("~"));
            String stringRight = content.substring(content.indexOf("~")+1);

            stringLeft = StringConversion.getString(self,target,stringLeft);
            stringRight = StringConversion.getString(self,target,stringRight);
            if(stringLeft.contains(stringRight)){
                result = true;
            }
        }
    }


    public boolean isResult() {
        return result;
    }
}
