package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Command {

    /**讓玩家執行指令**/
    public Command(){

    }

    public void setCommand(LivingEntity self, LivingEntity target, String firstString, String taskID){

        /**獲得內容**/
        String message = new StringFind().getKeyValue(self,target,firstString,"[];","m=","message=");
        /**獲得目標**/
        String aims = new StringFind().getKeyValue(self,target,firstString,"[]; ","@=");



        if(aims.toLowerCase().contains("target")){
            if(target instanceof Player){
                Player player = (Player) self;
                sendCommand(player,message);
            }
        }else if(aims.toLowerCase().contains("self")){
            if(self instanceof Player){
                Player player = (Player) self;
                sendCommand(player,message);
            }
        }else {
            if(self instanceof Player){
                Player player = (Player) self;
                sendCommand(player,message);
            }
        }

    }

    public void sendCommand(Player player,String command){

        player.performCommand(command);
    }

}
