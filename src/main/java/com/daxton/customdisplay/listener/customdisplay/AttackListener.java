package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
        double damageNumber = event.getFinalDamage();
        ActionManager.getDamage_Number_Map().put(event.getDamager().getUniqueId(),String.valueOf(damageNumber));
        target = (LivingEntity) event.getEntity();

        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();
            if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onattack") != null){
                new PlayerTrigger(player).onAttack(player,target,damageNumber);
            }


        }else if(event.getDamager() instanceof Projectile){
            if(((Projectile) event.getDamager()).getShooter() instanceof Animals == false && ((Projectile) event.getDamager()).getShooter() instanceof Monster == false){
                player = ((Player) event.getDamager()).getPlayer();
                if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onattack") != null){
                    new PlayerTrigger(player).onAttack(player,target,damageNumber);
                }
            }
        }else if(event.getDamager() instanceof TNTPrimed){
            player = ((Player) event.getDamager()).getPlayer();
            if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onattack") != null){
                new PlayerTrigger(player).onAttack(player,target,damageNumber);
            }
        }else {
            return;
        }

    }


}
