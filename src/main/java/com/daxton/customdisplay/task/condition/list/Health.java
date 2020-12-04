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

    private Player player;

    private LivingEntity target;

    private String firstString = "";

    private String taskID = "";

    private static Map<UUID,Double> healthMap = new HashMap<>();

    public Health(){

    }

    public void setHealth(Player player,LivingEntity target, String firstString, String taskID){
        this.player = player;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;

    }

    public void setHealth(Player player, String taskID, String firstString){
        this.player = player;
        this.firstString = firstString;
        this.taskID = taskID;

    }


    public boolean get(){
        boolean b = false;
        if(firstString.toLowerCase().contains("targetchange")){
            b = targetChange(target);
        }


        return b;
    }

    public boolean targetChange(LivingEntity target){
        boolean b = false;
        UUID targetUUID = target.getUniqueId();
        double maxHealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowHealth = target.getHealth();
        if(healthMap.get(targetUUID) == null){
            healthMap.put(targetUUID,maxHealth);
        }
        if(healthMap.get(targetUUID) != null && healthMap.get(targetUUID) != nowHealth){
            b = true;

            healthMap.put(targetUUID,nowHealth);
        }
        return b;
    }

}
