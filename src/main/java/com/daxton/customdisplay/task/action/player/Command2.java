package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Command2 {

    /**讓玩家執行指令**/
    public Command2(){

    }

    public void setCommand(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**獲得內容**/
        String message = customLineConfig.getString(new String[]{"message","m"},"",self,target);


        if(self instanceof Player){
            Player player = (Player) self;
            sendCommand(player,message);
        }


    }

    public void sendCommand(Player player,String command){

        player.performCommand(command);
    }

}
