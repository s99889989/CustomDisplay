package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class LocParticle3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public LocParticle3(){

    }

    public void set(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        if(target == null){
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        PotionEffectType potion = actionMapHandle.getPotionEffectType(new String[]{"potion"},PotionEffectType.INCREASE_DAMAGE);

        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},200);

        int amplifir = actionMapHandle.getInt(new String[]{"amplifir","ap"},1);

        boolean ambient = actionMapHandle.getBoolean(new String[]{"ambient","ab"},false);

        boolean particles = actionMapHandle.getBoolean(new String[]{"particles","p"},false);

        setParticle(target, potion, duration, amplifir, ambient, particles);
    }

    public void setParticle(LivingEntity livingEntity, PotionEffectType potion, int duration, int amplifier, boolean ambient, boolean particles){

        livingEntity.addPotionEffect(new PotionEffect(potion, duration, amplifier, ambient, particles));
        //livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(5 * 20.0D), 10, false, false));
    }


}
