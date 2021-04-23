package com.daxton.customdisplay.task.action2.profession;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.set.PlayerPoint;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class Point3 {

    public Point3(){

    }

    public void setPoint(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String type = actionMapHandle.getString(new String[]{"type"},"minecraft");

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);


        if(self instanceof Player){
            Player player = (Player) self;
            if(type.contains("minecraft")){

            }else {
                addPoint(player, type, amount);
            }
        }
    }



    public void addPoint(Player player, String type, int amount){

        new PlayerPoint().setOneMap(player,type,amount);


    }


}
