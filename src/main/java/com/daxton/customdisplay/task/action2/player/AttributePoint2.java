package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributesPoint;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AttributePoint2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public AttributePoint2(){

    }

    public void setAttributePoint(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        String type = customLineConfig.getString(new String[]{"type"},"minecraft",self,target);

        int amount = customLineConfig.getInt(new String[]{"amount","a"},1,self,target);

        if(type.toLowerCase().equals("minecraft")){

        }else {
            if(self instanceof Player){
                Player player = (Player) self;
                addPoint(player, type, amount);
            }

        }
    }


    public void addPoint(Player player, String type, int amount){

        new PlayerAttributesPoint().setOneMap(player,type,amount);


    }


}
