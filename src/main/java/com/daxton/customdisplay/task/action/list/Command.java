package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;


public class Command {

    /**讓玩家執行指令**/
    public Command(){

    }

    public void setCommand(LivingEntity self, LivingEntity target, String firstString, String taskID){

        /**獲得內容**/
        String message = new StringFind().getKeyValue2(self,target,firstString,"[];","","m=","message=");

        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    sendCommand(player,message);
                }
            }
        }


    }

    public void sendCommand(Player player,String command){

        player.performCommand(command);
    }

}
