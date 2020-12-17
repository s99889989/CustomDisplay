package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;


public class AttackedListener implements Listener {

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            double damageNumber = event.getFinalDamage();
            if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onattack") != null){
                new PlayerTrigger(player).onDamaged(player,damageNumber);
            }

        }else {
            return;
        }


    }




}
