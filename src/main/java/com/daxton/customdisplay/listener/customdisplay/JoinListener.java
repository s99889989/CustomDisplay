package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));

        if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onjoin") != null){
            new PlayerTrigger(player).onJoin(player);
        }



    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onsneak") != null){
                new PlayerTrigger(player).onSneak(player);
            }
        }else {
            if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onstandup") != null){
                new PlayerTrigger(player).onStandup(player);
            }
        }


    }




}
