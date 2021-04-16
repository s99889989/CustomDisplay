package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ModMessage {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ModMessage(){

    }

    public void setMessage(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**獲得內容**/
        String message = customLineConfig.getString(new String[]{"message","m"},"",self,target);

        int position = customLineConfig.getInt(new String[]{"position","p"},1,self,target);

        if(self instanceof Player){
            Player player = (Player) self;
            sendMessage(player, message, position);
        }

    }

    public void sendMessage(Player player,  String message, int position){

        CustomDisplay.customDisplay.sendMessage(player, position+message);

    }
    
}
