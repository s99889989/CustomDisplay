package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.daxton.customdisplay.api.entity.Convert.convertLivingEntity;


public class AttackedListener implements Listener {

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent event){
        Player player = Convert.convertPlayer(event.getEntity());
        if(player != null){
            LivingEntity target = convertLivingEntity(event.getDamager());
            String uuidString = player.getUniqueId().toString();
            double damagedNumber = event.getFinalDamage();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>","Miss");
                new PlayerTrigger(player).onTwo(player, target, "~ondamagedmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_damaged_number>",String.valueOf(damagedNumber));

                new PlayerTrigger(player).onTwo(player, target, "~ondamaged");
            }



        }


    }




}
