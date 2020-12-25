package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.Potion;

import java.util.UUID;

public class MobListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private double damageNumber = 0;
    private double damagedNumber = 0;

    @EventHandler
    public void MobSpawn(EntitySpawnEvent event){
//        if(event.getEntity() instanceof LivingEntity){
//            LivingEntity livingEntity = (LivingEntity) event.getEntity();
//            UUID uuid = event.getEntity().getUniqueId();
//            MobManager.getMob_Data_Map().put(uuid,new MobData(livingEntity));
//        }else {
//            return;
//        }



    }

    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.MONITOR
    )
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            UUID uuid = event.getEntity().getUniqueId();
            String uuidString = uuid.toString();
            damagedNumber = event.getFinalDamage();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>",String.valueOf(damagedNumber));


        }else {
            return;
        }

    }

//    @EventHandler
//    public void onAttacked(EntityDamageByEntityEvent event){
//        if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
//            LivingEntity livingEntity = (LivingEntity) event.getEntity();
//            UUID uuid = event.getEntity().getUniqueId();
//            this.damageNumber = event.getFinalDamage();
//
//        }else {
//            return;
//        }
//
//    }
    /**怪物死亡**/
    @EventHandler
    public void onDeath(EntityDeathEvent event) {


//        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
//            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
//            Player player = null;
//            Entity killer = nEvent.getDamager();
//            if(killer instanceof Arrow){
//                if(((Arrow) nEvent.getDamager()).getShooter() instanceof Player){
//                    Entity entity1 = (Player) ((Arrow) nEvent.getDamager()).getShooter();
//                    cd.getLogger().info(entity1.getName());
//                }
//            }else if(killer instanceof ThrownPotion){
//                if(((ThrownPotion) killer).getShooter() instanceof Player){
//                    player = (Player) ((ThrownPotion) killer).getShooter();
//                    player.sendMessage("藥水");
//                }
//            }
//
//
//        }

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
