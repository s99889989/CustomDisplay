package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.mobs.MobData;
import com.daxton.customdisplay.api.mobs.MobTrigger;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.UUID;

public class MobListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private double damageNumber = 0;

    @EventHandler
    public void MobSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            UUID uuid = event.getEntity().getUniqueId();
            MobManager.getMob_Data_Map().put(uuid,new MobData(livingEntity));
        }else {
            return;
        }



    }

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            UUID uuid = event.getEntity().getUniqueId();
            this.damageNumber = event.getFinalDamage();
            if(new MobTrigger(livingEntity,damageNumber).getAction_Trigger_Map().get("~ondamaged") != null){
                new MobTrigger(livingEntity,damageNumber).onDamaged();
            }


        }else {
            return;
        }

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            UUID uuid = event.getEntity().getUniqueId();
            if(new MobTrigger(livingEntity,damageNumber).getAction_Trigger_Map().get("~ondeath") != null){
                new MobTrigger(livingEntity,damageNumber).onDeath();
            }
        }

    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event){
        final Entity entity = event.getEntity();


    }

//    @EventHandler
//    public void MythicMobSpawn(MythicMobSpawnEvent event){
//
//
//
//    }

}