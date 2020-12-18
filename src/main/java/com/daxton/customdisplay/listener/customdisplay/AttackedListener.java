package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;


public class AttackedListener implements Listener {

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player & event.getDamager() instanceof LivingEntity){
            Player player = ((Player) event.getEntity()).getPlayer();
            LivingEntity target = (LivingEntity) event.getDamager();

            new PlayerTrigger(player).onDamaged(player,target);

        }else {
            return;
        }


    }




}
