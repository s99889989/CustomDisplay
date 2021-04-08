package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LocParticle {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public LocParticle(){

    }

    public void set(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        if(target == null){
            return;
        }

        PotionEffectType potion = customLineConfig.getPotionEffectType(new String[]{"potion"},PotionEffectType.INCREASE_DAMAGE,self,target);

        int duration = customLineConfig.getInt(new String[]{"duration","dt"},200,self,target);

        int amplifir = customLineConfig.getInt(new String[]{"amplifir","ap"},1,self,target);

        boolean ambient = customLineConfig.getBoolean(new String[]{"ambient","ab"},false,self,target);

        boolean particles = customLineConfig.getBoolean(new String[]{"particles","p"},false,self,target);

        setParticle(target, potion, duration, amplifir, ambient, particles);
    }

    public void setParticle(LivingEntity livingEntity, PotionEffectType potion, int duration, int amplifier, boolean ambient, boolean particles){

        livingEntity.addPotionEffect(new PotionEffect(potion, duration, amplifier, ambient, particles));
        //livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(5 * 20.0D), 10, false, false));
    }


}
