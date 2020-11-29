package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Health {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static Map<UUID,Double> healthMap = new HashMap<>();

    public Health(){

    }

    public boolean judgment(String firstString, LivingEntity target, Player player,String taskID){
        boolean bo = false;
        if(firstString.toLowerCase().contains("targetchange")){
            bo = targetChange(target);
        }
        return bo;
    }

    public boolean judgment(String firstString, Player player,String taskID){
        boolean bo = false;
        if(firstString.toLowerCase().contains("targetchange")){
            bo = targetChange(player);
        }
        return bo;
    }

    public boolean targetChange(LivingEntity target){
        boolean b = true;
        UUID targetUUID = target.getUniqueId();
        double maxHealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowHealth = target.getHealth();
        if(healthMap.get(targetUUID) == null){
            healthMap.put(targetUUID,maxHealth);
        }
        if(healthMap.get(targetUUID) != null && healthMap.get(targetUUID) != nowHealth){
            b = false;
            healthMap.put(targetUUID,nowHealth);
        }


        return b;
    }

}
