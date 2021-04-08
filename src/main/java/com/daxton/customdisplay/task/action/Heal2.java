package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class Heal2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    public Heal2(){


    }

    public void setHeal(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**補量**/
        double amount = customLineConfig.getDouble(new String[]{"amount","a"},10,self,target);
        List<LivingEntity> targetList = customLineConfig.getLivingEntityList(self,target);

        for(LivingEntity livingEntity : targetList){
            giveHeal(livingEntity,amount);
        }


    }

    public void giveHeal(LivingEntity livingEntity,double amount){

        double giveHealth = livingEntity.getHealth()+amount;
        double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if(giveHealth > maxHealth){
            giveHealth = giveHealth - (giveHealth - maxHealth);
        }

        livingEntity.setHealth(giveHealth);
    }


}
