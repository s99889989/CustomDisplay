package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class DamagerNumberListener implements Listener {


    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPhysicalDamageListener(PhysicalDamageEvent event){

        if(!(event.getTarget() instanceof LivingEntity) || event.getTarget().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }

        double damageNumber = event.getDamage();
        LivingEntity target = event.getTarget();
        Player player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){
            String uuidString = player.getUniqueId().toString();

            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                new PlayerTrigger(player).onAtkMiss(player,target);
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
            }



        }

    }

}
