package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;

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
                    message = new StringConversionMain().valueOf(self,target,strings[1]);
                }
            }

        }
        sendLoggerInfo();
    }

    public void sendLoggerInfo(){
        cd.getLogger().info(message);
    }


}
