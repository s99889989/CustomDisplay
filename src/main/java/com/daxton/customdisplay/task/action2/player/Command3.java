package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;


public class Command3 {

    /**讓玩家執行指令**/
    public Command3(){

    }

    public static void setCommand(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListSelf();

        targetList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){

                Player player = (Player) livingEntity;

                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, player, target);

                /**獲得內容**/
                String message = actionMapHandle2.getString(new String[]{"message","m"},"");

                sendCommand(player,message);
            }
        });


    }

    public static void sendCommand(Player player,String command){

        player.performCommand(command);
    }

}
