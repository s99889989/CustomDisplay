package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ModMessage3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ModMessage3(){

    }

    public static void setMessage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        int position = actionMapHandle.getInt(new String[]{"position","p"},1);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {

            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);

                //獲得內容
                String message = actionMapHandle2.getString(new String[]{"message","m"},"");

                sendMessage(player, message, position);
            }

        });



    }

    public static void sendMessage(Player player,  String message, int position){

        CustomDisplay.customDisplay.sendMessage(player, position+message);

    }
    
}
