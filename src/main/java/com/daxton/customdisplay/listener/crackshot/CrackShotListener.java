package com.daxton.customdisplay.listener.crackshot;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class CrackShotListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onWeaponDamage(WeaponDamageEntityEvent event){
        if(event.isCancelled()){
            return;
        }
        if(!(event.getVictim() instanceof LivingEntity)){
            return;
        }
        Player player = event.getPlayer();
        double damageNumber = event.getDamage();
        LivingEntity target = (LivingEntity) event.getVictim();
        if(player != null && damageNumber > 0){
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);
        }



    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onAttack(EntityDamageByEntityEvent event){
        if (event.isCancelled()) {
            return;
        }
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        double damageNumber = event.getFinalDamage();
        LivingEntity target = (LivingEntity) event.getEntity();
        Player player = EntityFind.crackShotPlayer(event.getDamager());
        if(player != null && damageNumber > 0){
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);
        }

    }


}
