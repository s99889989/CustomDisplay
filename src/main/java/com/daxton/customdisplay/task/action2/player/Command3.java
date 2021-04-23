package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;


public class Command3 {

    /**讓玩家執行指令**/
    public Command3(){

    }

    public void setCommand(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**獲得內容**/
        String message = actionMapHandle.getString(new String[]{"message","m"},"");


        if(self instanceof Player){
            Player player = (Player) self;
            sendCommand(player,message);
        }


    }

    public void sendCommand(Player player,String command){

        player.performCommand(command);
    }

}
