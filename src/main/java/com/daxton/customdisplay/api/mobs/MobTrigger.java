package com.daxton.customdisplay.api.mobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MobTrigger {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();


    private LivingEntity self = null;
    private LivingEntity target = null;
    private double damageNumber = 0;

    private Map<String, List<String>> action_Trigger_Map = new HashMap<>();

    public MobTrigger(LivingEntity self){
        this.self =self;
        UUID uuid = self.getUniqueId();
        if(MobManager.getMob_Data_Map().get(uuid) != null){
            action_Trigger_Map = MobManager.getMob_Data_Map().get(uuid).getAction_Trigger_Map();
        }

    }

    public MobTrigger(LivingEntity self,double damageNumber){
        this.self =self;
        this.damageNumber = damageNumber;
        UUID uuid = self.getUniqueId();
        if(MobManager.getMob_Data_Map().get(uuid) != null){
            action_Trigger_Map = MobManager.getMob_Data_Map().get(uuid).getAction_Trigger_Map();
        }

    }

    public void onDamaged(){
        for(String actionString : action_Trigger_Map.get("~ondamaged")){
            new JudgmentAction2().execute(self,target,actionString,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public void onDeath(){
        for(String actionString : action_Trigger_Map.get("~ondeath")){
            new JudgmentAction2().execute(self,target,actionString,damageNumber,String.valueOf((int)(Math.random()*100000)));
        }
    }

    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
