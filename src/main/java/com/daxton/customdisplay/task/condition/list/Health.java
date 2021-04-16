package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Health {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity self = null;
    private LivingEntity target = null;

    private String firstString = "";

    private String taskID = "";

    private String aimsKey = "";

    private static Map<UUID,Double> healthMap = new HashMap<>();

    public Health(){

    }

    public void setHealth(LivingEntity self,LivingEntity target, String firstString, String aimsKey, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        this.aimsKey = aimsKey;
    }

    public boolean get(){
        boolean b = false;
        if(firstString.toLowerCase().contains("targetchange")){
            if(this.aimsKey.toLowerCase().contains("target")){
                b = targetChange(target);
            }else {
                b = targetChange(self);
            }

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
