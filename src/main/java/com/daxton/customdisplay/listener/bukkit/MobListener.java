package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

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
                player = Convert.convertKillerPlayer((LivingEntity) nEvent.getDamager());
            }else {
                player = Convert.convertPlayer(nEvent.getDamager());
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

    /**無攻擊者的被攻擊傷害**/
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamaged(EntityDamageEvent event){
        FileConfiguration configuration = ConfigMapManager.getFileConfigurationMap().get("Character_System_NoDamagerDamage.yml");
        boolean bb = configuration.getBoolean("NoDamagerDamage.enable");

        if(bb && event.getCause().toString().contains("CUSTOM")){
            LivingEntity entity = (LivingEntity) event.getEntity();
            Location location = entity.getLocation();
            Hologram hologram = HologramsAPI.createHologram(cd, location.add(0,entity.getHeight(),0));

            String number = new NumberUtil(event.getFinalDamage(),configuration.getString("NoDamagerDamage.decimal")).getDecimalString();
            String numberString = configuration.getString("NoDamagerDamage.content");
            numberString = numberString.replace("{number}",number);
            numberString = "\uF80B"+numberString+"\uF80B";
            numberString = new NumberUtil().NumberHead2(numberString,configuration.getString("NoDamagerDamage.head_conversion"));
            numberString = new NumberUtil().NumberUnits2(numberString,configuration.getString("NoDamagerDamage.units_conversion"));

            String[] containKeyList = configuration.getString("NoDamagerDamage.double_conversion").split(",");
            for(String containKey : containKeyList){
                String[] containKeyList2 = containKey.split(">");
                if(containKeyList2.length == 2){
                    numberString = numberString.replace(containKeyList2[0],containKeyList2[1]);
                }
            }

            hologram.appendTextLine(numberString);
            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                int tickRun = 0;
                @Override
                public void run() {
                    if(tickRun > configuration.getInt("NoDamagerDamage.duration")){
                        hologram.delete();
                        cancel();
                        return;
                    }
                    hologram.teleport(location.add(0,configuration.getDouble("NoDamagerDamage.tpHeight"),0));
                    tickRun++;
                }
            };
            bukkitRunnable.runTaskTimer(cd,0,configuration.getInt("NoDamagerDamage.period"));
        }


    }


}
