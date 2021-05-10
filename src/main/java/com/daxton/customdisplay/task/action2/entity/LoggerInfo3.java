package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class LoggerInfo3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    public LoggerInfo3(){

    }

    public static void setLoggerInfo(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);



        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        livingEntityList.forEach(livingEntity -> {
            ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);

            String message = actionMapHandle2.getString(new String[]{"message","m"},"");

            CustomDisplay.getCustomDisplay().getLogger().info(message);
        });

    }


}
