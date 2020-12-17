package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LoggerInfo {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private LivingEntity self = null;

    private LivingEntity target = null;

    public LoggerInfo(){

    }

    public void setLoggerInfo(LivingEntity self,LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }

        }
        sendLoggerInfo();
    }

    public void sendLoggerInfo(){
        cd.getLogger().info(message);
    }


}
