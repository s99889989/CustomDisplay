package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class PotionEffect3 {

    public PotionEffect3(){

    }

    public static void set(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        PotionEffectType potion = actionMapHandle.getPotionEffectType(new String[]{"potion"},PotionEffectType.INCREASE_DAMAGE);

        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},200);

        int amplifir = actionMapHandle.getInt(new String[]{"amplifir","ap"},1);

        boolean ambient = actionMapHandle.getBoolean(new String[]{"ambient","ab"},false);

        boolean particles = actionMapHandle.getBoolean(new String[]{"particles","p"},false);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> setParticle(livingEntity, potion, duration, amplifir, ambient, particles));


    }

    public static void setParticle(LivingEntity livingEntity, PotionEffectType potion, int duration, int amplifier, boolean ambient, boolean particles){

        livingEntity.addPotionEffect(new PotionEffect(potion, duration, amplifier, ambient, particles));
        //livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(5 * 20.0D), 10, false, false));
    }


}
