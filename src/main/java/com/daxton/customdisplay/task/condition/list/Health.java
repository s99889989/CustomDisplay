package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
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

    private LivingEntity self = null;
    private LivingEntity target = null;

    private String firstString = "";
    private String aims = "self";
    private String taskID = "";

    private static Map<UUID,Double> healthMap = new HashMap<>();

    public Health(){

    }

    public void setHealth(LivingEntity self,LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
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

    public void setOther(){
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }
    }

    public boolean get(){
        boolean b = false;
        if(firstString.toLowerCase().contains("targetchange")){
            if(aims.toLowerCase().contains("target")){
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
