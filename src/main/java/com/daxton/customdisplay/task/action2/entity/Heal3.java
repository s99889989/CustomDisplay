package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class Heal3 {

    public Heal3(){


    }

    public static void setHeal(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**補量**/
        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},10);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListSelf();

        targetList.forEach(livingEntity -> giveHeal(livingEntity,amount));



    }

    public static void giveHeal(LivingEntity livingEntity,double amount){

        double giveHealth = livingEntity.getHealth()+amount;
        double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if(giveHealth > maxHealth){
            giveHealth = giveHealth - (giveHealth - maxHealth);
        }

        livingEntity.setHealth(giveHealth);
    }


}
