package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import com.daxton.customdisplay.task.player.OnAttack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;
import java.util.List;
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




        if(cd.getConfigManager().config_entity_top_display){
            HDShow(targetUUID,target);
        }

        if(cd.getConfigManager().config_boss_bar_display && cd.getConfigManager().boss_bar_enable){
            BossBar();
        }

        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(configName.contains("Character")){
                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(configName);
                ConfigurationSection configSection = fileConfiguration.getConfigurationSection("");
                for(String key : configSection.getKeys(false)){
                    player.sendMessage(key);
                }
            }
        }


        action();


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


    public void BossBar(){

        AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(playerUUID);
        if(attackBossBar != null){
            attackBossBar.getBossBar().removePlayer(player);
            attackBossBar.getBukkitRunnable().cancel();
            BBDMapManager.getAttackBossBarMap().remove(playerUUID);
            BBDMapManager.getAttackBossBarMap().put(playerUUID,new AttackBossBar(player,target));
        }else{
            BBDMapManager.getAttackBossBarMap().put(playerUUID,new AttackBossBar(player,target));
        }

    }

    public void HDShow(UUID targetUUID,LivingEntity target){

        if(cd.getConfigManager().player_top_display_enable && target instanceof Player){
            PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(targetUUID);
            if(playerHD == null){
                HDMapManager.getPlayerHDMap().put(targetUUID,new PlayerHD((Player)target));
            }
        }

        if(cd.getConfigManager().monster_top_display_enable && target instanceof Monster){
            MonsterHD monsterHD = HDMapManager.getMonsterHDMap().get(targetUUID);
            if(monsterHD == null){
                HDMapManager.getMonsterHDMap().put(targetUUID,new MonsterHD(target));
            }
        }

        if(cd.getConfigManager().animal_top_display_enable && target instanceof Animals){
            AnimalHD animalHD = HDMapManager.getAnimalHDMap().get(targetUUID);
            if(animalHD == null){
                HDMapManager.getAnimalHDMap().put(targetUUID,new AnimalHD(target));
            }
        }
    }


}
