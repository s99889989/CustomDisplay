package com.daxton.customdisplay.task.action.server;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;

public class LoggerInfo2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    public LoggerInfo2(){

    }

    public void setLoggerInfo(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        String message = customLineConfig.getString(new String[]{"message","m"},"",self,target);

        sendLoggerInfo(message);
    }

    public void sendLoggerInfo(String message){
        cd.getLogger().info(message);
    }


}
