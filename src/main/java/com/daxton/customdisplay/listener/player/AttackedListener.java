package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

import static org.bukkit.entity.EntityType.ARMOR_STAND;


public class AttackedListener implements Listener {

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            if(playerData != null){
                playerData.runAction("~ondamaged",event);
            }
        }else {
            return;
        }

    }



}
