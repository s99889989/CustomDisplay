package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Cancell2 {



    public Cancell2(){

    }

    public void setCancell(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){


        String cancellName = customLineConfig.getString(new String[]{"name"},"",self,target);

        boolean enable = customLineConfig.getBoolean(new String[]{"enable"}, false,self,target);

        setOther(self, cancellName, enable);
    }
    public void setOther(LivingEntity self, String cancellName, boolean enable){


        if(self != null && self instanceof Player){
            Player player = (Player) self;
            String uuidString = self.getUniqueId().toString();
            if(cancellName.toLowerCase().contains("playeritemheldevent")){
                PlayerDataMap.even_Cancel_Map.put(uuidString,enable);
            }
            if(cancellName.toLowerCase().contains("playerswaphanditemsevent")){
                //player.sendMessage("開關"+enable);
                PlayerDataMap.even_Cancel_Map.put(uuidString,enable);
            }


        }



    }



}
