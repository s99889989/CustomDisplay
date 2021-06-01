package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class Cancell3 {



    public Cancell3(){

    }

    public void setCancell(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String cancellName = actionMapHandle.getString(new String[]{"name"},"");

        boolean enable = actionMapHandle.getBoolean(new String[]{"enable"}, false);

        setOther(self, cancellName, enable);
    }
    public void setOther(LivingEntity self, String cancellName, boolean enable){


        if(self != null && self instanceof Player){
            Player player = (Player) self;
            String uuidString = self.getUniqueId().toString();
            if(cancellName.toLowerCase().contains("playeritemheldevent")){
                PlayerManager.even_Cancel_Map.put(uuidString,enable);
            }
            if(cancellName.toLowerCase().contains("playerswaphanditemsevent")){
                //player.sendMessage("開關"+enable);
                PlayerManager.even_Cancel_Map.put(uuidString,enable);
            }


        }



    }



}
