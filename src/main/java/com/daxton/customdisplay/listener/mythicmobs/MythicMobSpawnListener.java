package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.mob.MobConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.ticxo.modelengine.api.events.*;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.component.ModelOption;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MythicMobSpawnListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        ActiveMob activeMob = event.getMob();
        new MobConfig().setMod(activeMob,event.getMobLevel());


        cd.getLogger().info("等級: "+event.getMobLevel());

    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event){
        String mobID = event.getMob().getMobType();


        LivingEntity target = (LivingEntity) event.getEntity();
        LivingEntity killer = event.getKiller();

        Player player = null;
        player = EntityFind.convertKillerPlayer(killer);


        if(player != null){

            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_mythic_kill_mob_id>",mobID);
            new PlayerTrigger(player).onMythicMobDeath(player,target);

        }




    }




}
