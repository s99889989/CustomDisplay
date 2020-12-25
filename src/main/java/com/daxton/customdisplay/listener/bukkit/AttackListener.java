package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static org.bukkit.entity.EntityType.*;

public class AttackListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;


    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGHEST
    )
    public void onAttack(EntityDamageByEntityEvent event){

        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        double damageNumber = event.getFinalDamage();
        String uuidString = event.getDamager().getUniqueId().toString();
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
        target = (LivingEntity) event.getEntity();
        Entity damager = event.getDamager();
        if(damager instanceof Player){
            player = (Player) event.getDamager();
            new PlayerTrigger(player).onAttack(player,target);
            return;
        }
        if(damager instanceof Arrow){
            if(((Arrow) event.getDamager()).getShooter() instanceof Player){
                player = (Player) ((Arrow) event.getDamager()).getShooter();
                uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
                return;
            }
        }
        if(damager instanceof Projectile){
            if(((Projectile) damager).getShooter() instanceof Player){
                player = (Player) ((Projectile) event.getDamager()).getShooter();
                new PlayerTrigger(player).onAttack(player,target);
                return;
            }
        }
        if(damager instanceof TNTPrimed){
            if(((TNTPrimed) damager).getSource() instanceof Player){
                player = (Player) ((TNTPrimed) event.getDamager()).getSource();
                uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
                return;
            }
        }
        if(damager instanceof ThrownPotion){
            if(((ThrownPotion) damager).getShooter() instanceof Player){
                player = (Player) ((ThrownPotion) damager).getShooter();
                uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
                return;
            }
        }

    }


}
