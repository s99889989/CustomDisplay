package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.List;


public class LocationDamage2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public LocationDamage2(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        if(target == null){
            return;
        }
        double amount = customLineConfig.getDouble(new String[]{"amount","a"},1,self,target);

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
