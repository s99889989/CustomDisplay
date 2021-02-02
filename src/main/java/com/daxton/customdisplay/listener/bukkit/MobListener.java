package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MobListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private double damageNumber = 0;
    private double damagedNumber = 0;

    private String playerUUIDString = "";
    private List<String> levelNameList = new ArrayList<>();
    private File playerFilePatch;
    private FileConfiguration playerConfig;
    private File levelFilePatch;
    private FileConfiguration levelConfig;

    private String setModType = "";
    private String killType = "";
    private int amonut = 0;

    @EventHandler
    public void MobSpawn(EntitySpawnEvent event){
        //cd.getLogger().info("BK: "+event.getEntityType().toString());
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

    /**怪物死亡**/
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity target = event.getEntity();

        String entityType = event.getEntityType().toString();

        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

            Player player = null;
            if(nEvent.getDamager() instanceof LivingEntity){
                player = EntityFind.convertKillerPlayer((LivingEntity) nEvent.getDamager());
            }else {
                player = EntityFind.convertPlayer(nEvent.getDamager());
            }
            if(player != null){

                String uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_kill_mob_type>",entityType);
                new PlayerTrigger(player).onMobDeath(player,target);
            }
        }

    }





    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event){
        final Entity entity = event.getEntity();


    }


}
