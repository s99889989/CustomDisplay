package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;

import java.util.Map;


public class LocationDamage3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public LocationDamage3(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        if(target == null){
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},1);

//        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);
//
//        if(!(livingEntityList.isEmpty())){
//            for(LivingEntity livingEntity : livingEntityList){
//
//
//
//            }
//
//        }
        setDamage(self, target, amount);
    }

    public void setDamage(LivingEntity self, LivingEntity target,double amount){

        target.damage(amount, self);
    }




}
