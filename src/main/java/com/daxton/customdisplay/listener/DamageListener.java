package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.AttackHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class DamageListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        LivingEntity target = (LivingEntity) e.getEntity();
        UUID targetUUID = target.getUniqueId();
        Entity damager = e.getDamager();
        UUID uuid = damager.getUniqueId();
        EntityType entityType = e.getEntityType();
        double damageNumber = e.getFinalDamage();

        if(entityType != ARMOR_STAND &&target instanceof LivingEntity && damager instanceof Player || damager instanceof Projectile || damager instanceof TNT){
            Player p = (Player) damager;

            AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(uuid);
            if(cd.getConfigManager().config_boss_bar_display && cd.getConfigManager().boss_bar_enable){
                if(attackBossBar != null){
                    attackBossBar.getBossBar().removePlayer(p);
                    attackBossBar.getBukkitRunnable().cancel();
                    BBDMapManager.getAttackBossBarMap().remove(uuid);
                    BBDMapManager.getAttackBossBarMap().put(uuid,new AttackBossBar(p,target));
                }else{
                    BBDMapManager.getAttackBossBarMap().put(uuid,new AttackBossBar(p,target));
                }
            }

            if(cd.getConfigManager().config_attack_display && cd.getConfigManager().attack_display_player_enable){
                new AttackHD(target, damager, damageNumber);
            }
            if(cd.getConfigManager().config_entity_top_display){
                HDShow(targetUUID,target);
            }
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
