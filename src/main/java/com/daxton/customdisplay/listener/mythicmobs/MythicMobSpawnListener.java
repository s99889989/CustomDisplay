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
import org.bukkit.entity.Parrot;
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
        new MobConfig().setMod(activeMob);

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
        if(killer instanceof Parrot){
            Parrot parrot = (Parrot) killer;
            if(parrot.getOwner() != null && parrot.getOwner() instanceof Player){
                player = ((Player) parrot.getOwner()).getPlayer();
            }
        }


        if(player != null){
            //cd.getLogger().info(player.getName());
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_mythic_kill_mob_id>",mobID);
            new PlayerTrigger(player).onMythicMobDeath(player,target);

        }




    }

}
