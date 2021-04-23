package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class ModMessage3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ModMessage3(){

    }

    public void setMessage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**獲得內容**/
        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        int position = actionMapHandle.getInt(new String[]{"position","p"},1);

        if(self instanceof Player){
            Player player = (Player) self;
            sendMessage(player, message, position);
        }

    }

    public void sendMessage(Player player,  String message, int position){

        CustomDisplay.customDisplay.sendMessage(player, position+message);

    }
    
}
