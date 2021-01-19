package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.NumberUtil;
import org.bukkit.entity.LivingEntity;

public class ConversionMain {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ConversionMain(){

    }

    public String valueOf(LivingEntity self, LivingEntity target, String inputString){
        String outputString = "";
        int num = NumberUtil.appearNumber(inputString, "&");
        if(num%2 == 0){
            for(int i = 0; i < num/2 ; i++){
                int head = inputString.indexOf("&");
                int tail = inputString.indexOf("&",head+1);
                if(inputString.contains("&")){
                    String change = new ConversionCustom().valueOf(self,target,inputString.substring(head,tail+1));
                    inputString = inputString.replace(inputString.substring(head,tail+1),change);
                }
            }
        }
        outputString = inputString;
        return outputString;
    }


}
