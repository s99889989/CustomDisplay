package com.daxton.customdisplay.task.condition2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Compare2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private boolean result = false;

    public Compare2(){

    }

    public Compare2(LivingEntity self, LivingEntity target, String content, String taskID){

        String stringLeft = "";
        String stringRight = "";

        if(content.contains(">")){
            stringLeft = content.substring(0, content.indexOf(">"));
            stringRight = content.substring(content.indexOf(">")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft > numberRight){
                result = true;
            }
        }else if(content.contains("<")){
            stringLeft = content.substring(0, content.indexOf("<"));
            stringRight = content.substring(content.indexOf("<")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft < numberRight){
                result = true;
            }
        }else if(content.contains("~")){

            stringLeft = content.substring(0, content.indexOf("~"));
            stringRight = content.substring(content.indexOf("~")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft == numberRight){
                result = true;
            }


        }


    }

    public static boolean valueOf(LivingEntity self, LivingEntity target, String content, String taskID){
        boolean result = false;

        String stringLeft = "";
        String stringRight = "";

        if(content.contains(">")){
            stringLeft = content.substring(0, content.indexOf(">"));
            stringRight = content.substring(content.indexOf(">")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft > numberRight){
                result = true;
            }
        }else if(content.contains("<")){
            stringLeft = content.substring(0, content.indexOf("<"));
            stringRight = content.substring(content.indexOf("<")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft < numberRight){
                result = true;
            }
        }else if(content.contains("~")){

            stringLeft = content.substring(0, content.indexOf("~"));
            stringRight = content.substring(content.indexOf("~")+1);

            double numberLeft = StringConversion.getDouble(self,target,0,stringLeft);
            double numberRight = StringConversion.getDouble(self,target,0,stringRight);
            if(numberLeft == numberRight){
                result = true;
            }


        }
        return result;
    }

    public boolean isResult() {
        return result;
    }






}
