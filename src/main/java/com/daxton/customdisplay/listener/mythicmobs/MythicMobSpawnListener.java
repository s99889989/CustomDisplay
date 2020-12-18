package com.daxton.customdisplay.listener.mythicmobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobSpawnListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler
    public void onMythicMobSpawn(MythicMobSpawnEvent event){
        PlaceholderManager.getMythicMobs_Level_Map().put(event.getEntity().getUniqueId(), String.valueOf(event.getMobLevel()));

    }

}
