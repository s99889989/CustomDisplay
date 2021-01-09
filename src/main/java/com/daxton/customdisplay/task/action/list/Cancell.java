package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Cancell {

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private String cancellName;
    private boolean enable =false;

    public Cancell(){

    }

    public void setCancell(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;

        setOther();
    }
    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("name=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    cancellName = strings[1];

                }
            }

            if(allString.toLowerCase().contains("enable=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    enable = Boolean.valueOf(strings[1]);

                }
            }

        }

        if(self != null && self instanceof Player){
            String uuidString = self.getUniqueId().toString();
            if(cancellName.toLowerCase().contains("playeritemheldevent")){
                PlayerDataMap.even_Cancel_Map.put(uuidString,enable);
            }
            if(cancellName.toLowerCase().contains("playerswaphanditemsevent")){
                PlayerDataMap.even_Cancel_Map.put(uuidString,enable);
            }


        }



    }

    public void setCancellOther(){



    }

}
