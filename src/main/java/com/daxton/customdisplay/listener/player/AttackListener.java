package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.player.OnAttack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class AttackListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumber;

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity)){
            return;
        }

        target = (LivingEntity) event.getEntity();

        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();
        }

        if(event.getDamager() instanceof Projectile){
            player = (Player) ((Projectile) event.getDamager()).getShooter();
        }

        if(event.getDamager() instanceof TNTPrimed){
            player = (Player) ((TNTPrimed) event.getDamager()).getSource();
        }

        if(player == null){
            return;
        }

        playerUUID = player.getUniqueId();
        targetUUID = target.getUniqueId();
        damageNumber = event.getFinalDamage();

        //action();


    }

    public void action(){

        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            for(String string : playerData.getPlayerActionList()){
                if(string.toLowerCase().contains("~onattack")){
                    new OnAttack(player,target,string,damageNumber);
                }
            }
        }
    }

}
