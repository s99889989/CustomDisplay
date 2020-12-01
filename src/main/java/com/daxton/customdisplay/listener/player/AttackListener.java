package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.JudgmentAction;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
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

    private double damageNumber;

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Player) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }

        target = (LivingEntity) event.getEntity();

        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();
        }

        if(event.getDamager() instanceof Projectile){
            if(((Projectile) event.getDamager()).getShooter() instanceof Animals == false && ((Projectile) event.getDamager()).getShooter() instanceof Monster == false){
                player = (Player) ((Projectile) event.getDamager()).getShooter();
            }
        }

        if(event.getDamager() instanceof TNTPrimed){
            player = (Player) ((TNTPrimed) event.getDamager()).getSource();
        }

        if(player == null){
            return;
        }

        if(player != null){
            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();
            damageNumber = event.getFinalDamage();
            newAction();
        }

    }

    public void newAction(){
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            for(String string : playerData.getPlayerActionList()){
                if(string.toLowerCase().contains("~onattack")){
                    new JudgmentAction().execute(player,target,string,damageNumber,String.valueOf((int)(Math.random()*100000)));
                }
            }
        }
    }


}
