package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.mob.MobConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MythicMobSpawnListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        ActiveMob activeMob = event.getMob();
        String mobID = activeMob.getMobType();
        String uuidString = event.getEntity().getUniqueId().toString();

        MobManager.getMobID_Map().put(uuidString,mobID);

        PlaceholderManager.getMythicMobs_Level_Map().put(event.getEntity().getUniqueId(), String.valueOf(event.getMobLevel()));


        new MobConfig().createNewFile(mobID);


    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event){
        String mobID = event.getMob().getMobType();


        LivingEntity target = (LivingEntity) event.getEntity();
        LivingEntity killer = event.getKiller();
        Player player = null;
        if(killer instanceof Player){
            player = (Player) killer;
        }

        if(player != null){
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_mythic_kill_mob_id>",mobID);
            new PlayerTrigger(player).onMobDeath(player,target);

        }




    }

}
