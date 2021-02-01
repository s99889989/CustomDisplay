package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class AttackedListener implements Listener {

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        Player player = EntityFind.convertPlayer(event.getEntity());
        if(player != null){
            LivingEntity target = (LivingEntity) event.getDamager();
            String uuidString = player.getUniqueId().toString();
            double damagedNumber = event.getFinalDamage();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>","Miss");
                new PlayerTrigger(player).onDamagedMiss(player,target);
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>",String.valueOf(damagedNumber));
                new PlayerTrigger(player).onDamaged(player,target);
            }



        }


    }




}
