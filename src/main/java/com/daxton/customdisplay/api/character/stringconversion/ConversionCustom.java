package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.StringFind;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class ConversionCustom {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ConversionCustom(){

    }

    public static String valueOf(LivingEntity self, LivingEntity target, String inputString){

        String outputString = "";
        inputString = inputString.replace(" ","").replace("&","");
        List<String> stringList = new ConfigFind().getCharacterMessageList("Character",inputString);
        for(int i = 0 ; i < stringList.size() ; i++){
            String stringMessage = stringList.get(i);
            String headKey = new StringFind().getAction(stringMessage);
            String content = new StringFind().getContent(stringMessage);
            String targetKey = new StringFind().getTarget(stringMessage);
            if(i == 0){
                if(headKey.toLowerCase().contains("content")){
                    outputString = CustomConversion(self, target, headKey, content,targetKey);
                }else {
                    outputString = " X"+inputString+"X ";
                    break;
                }
            }else {
                outputString = CustomChange(self,target,headKey, outputString, content);
            }

        }

        return outputString;
    }

    public static String CustomConversion(LivingEntity self, LivingEntity target, String headKey, String content,String targetKey){
        String outputString = "";
        if(headKey.toLowerCase().contains("content")){
            if(content.contains("%")){
                content = ConversionPlaceholderAPI.valueOf(self,target,content,targetKey);
            }
            if(content.contains("&")){
                content = ConversionMain.valueOf(self,target,content);
            }
            if(content.contains("<") && content.contains(">")){
                content = ConversionPlaceholder.valueOf(self,target,content);
            }
            outputString = content;
        }


        return outputString;
    }

    public static String CustomChange(LivingEntity self, LivingEntity target, String headKey, String content,String changeContent){
        String outputString = "";

        if(headKey.toLowerCase().contains("conver")){
            outputString = ConversionChange.valueOf(self,target,content,changeContent);
        }


        if(headKey.toLowerCase().contains("math")){

            outputString = ConversionMath.valueOf(content,changeContent);
        }


        return outputString;
    }

}
