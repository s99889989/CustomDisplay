package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class MythicMobSpawnListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        PlaceholderManager.getMythicMobs_Level_Map().put(event.getEntity().getUniqueId(), String.valueOf(event.getMobLevel()));

    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event){
        LivingEntity killer = event.getKiller();
        if(killer != null){
            String uuidString = killer.getUniqueId().toString();
            File file = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            if(fileConfiguration.contains(uuidString+".Level.base_exp")){
                int soc = fileConfiguration.getInt(uuidString+".Level.base_exp");
                fileConfiguration.set(uuidString+".Level.base_exp",soc+10);
            }
            try {
                fileConfiguration.save(file);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }




    }

}
