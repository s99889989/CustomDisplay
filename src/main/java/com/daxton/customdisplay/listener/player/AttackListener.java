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

import java.io.File;
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
        if(!(event.getEntity() instanceof LivingEntity) && event.getEntity().getType() == ARMOR_STAND){
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

        File file = new File(cd.getDataFolder(),"Players.Pocaca.yml");

        for(String s : ConfigMapManager.getFileConfigurationMap().get("Players_Pocaca.yml").getStringList("Action")){
            player.sendMessage(s);
        }

        if(player != null){
            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();
            damageNumber = event.getFinalDamage();
            action();
        }

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
