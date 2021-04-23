package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.set.PlayerAttributesPoint;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class AttributePoint3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public AttributePoint3(){

    }

    public void setAttributePoint(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

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
