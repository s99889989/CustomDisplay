package com.daxton.customdisplay.task.action2.server;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class LoggerInfo3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    public LoggerInfo3(){

    }

    public void setLoggerInfo(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        sendLoggerInfo(message);
    }

    public void sendLoggerInfo(String message){
        cd.getLogger().info(message);
    }


}
