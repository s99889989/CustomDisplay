package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import static org.bukkit.entity.EntityType.ARMOR_STAND;
import static org.bukkit.entity.EntityType.CREEPER;

import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.AttackHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class DamageListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof LivingEntity) && e.getEntityType() == ARMOR_STAND){
            return;
        }
        LivingEntity target = (LivingEntity) e.getEntity();
        UUID targetUUID = target.getUniqueId();
        double damageNumber = e.getFinalDamage();
        if(e.getDamager() instanceof Player || e.getDamager() instanceof Projectile){ // || e.getDamager() instanceof TNTPrimed

            Entity damager = e.getDamager();



            if(cd.getConfigManager().config_attack_display && cd.getConfigManager().attack_display_player_enable){
                new AttackHD(target, damager, damageNumber);
            }
            if(cd.getConfigManager().config_entity_top_display){
                HDShow(targetUUID,target);
            }
            if(e.getDamager() instanceof Player){
                if(cd.getConfigManager().config_boss_bar_display && cd.getConfigManager().boss_bar_enable){
                    Player player = (Player) damager;
                    UUID playerUUID = player.getUniqueId();
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
                return;
            }

            if(e.getDamager() instanceof Projectile){
                if(cd.getConfigManager().config_boss_bar_display && cd.getConfigManager().boss_bar_enable && e.getEntityType() != CREEPER){
                    Player player = ((Player) ((Projectile) damager).getShooter()).getPlayer();
                    UUID playerUUID = player.getUniqueId();
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
                return;
            }


        }

        if(e.getDamager() instanceof TNTPrimed){
//            if(cd.getConfigManager().config_boss_bar_display && cd.getConfigManager().boss_bar_enable && e.getEntityType() != CREEPER){
//                cd.getLogger().info("TNTPrimed");
//                Player player = ((Player) ((TNTPrimed) damager).getSource()).getPlayer();
//                UUID playerUUID = player.getUniqueId();
//                AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(playerUUID);
//                if(attackBossBar != null) {
//                    attackBossBar.getBossBar().removePlayer(player);
//                    attackBossBar.getBukkitRunnable().cancel();
//                    BBDMapManager.getAttackBossBarMap().remove(playerUUID);
//                    BBDMapManager.getAttackBossBarMap().put(playerUUID, new AttackBossBar(player, target));
//                }else{
//                    BBDMapManager.getAttackBossBarMap().put(playerUUID,new AttackBossBar(player,target));
//                }
//            }
            return;
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
