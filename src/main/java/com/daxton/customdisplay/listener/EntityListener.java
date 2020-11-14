package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.AnimalHD;
import com.daxton.customdisplay.task.holographicdisplays.MonsterHD;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class EntityListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        UUID uuid = e.getEntity().getUniqueId();
        UUID targetUUID = BBDMapManager.getTargetAttackBossBarMap().get(uuid);
        if(targetUUID != null){
            AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(targetUUID);
            Player player = attackBossBar.getPlayer();
            if(attackBossBar != null){
                attackBossBar.getBossBar().removePlayer(player);
                attackBossBar.getBukkitRunnable().cancel();
                BBDMapManager.getAttackBossBarMap().remove(targetUUID);
                BBDMapManager.getTargetAttackBossBarMap().remove(uuid);
            }
        }
        MonsterHD monsterHD = HDMapManager.getMonsterHDMap().get(uuid);
        if(monsterHD != null){
            monsterHD.getHologram().delete();
            monsterHD.getHealthMap().clear();
            monsterHD.getLocationMap().clear();
            monsterHD.getBukkitRunnable().cancel();
            HDMapManager.getMonsterHDMap().remove(uuid);
        }
        AnimalHD animalHD = HDMapManager.getAnimalHDMap().get(uuid);
        if(animalHD != null){
            animalHD.getHologram().delete();
            animalHD.getHealthMap().clear();
            animalHD.getLocationMap().clear();
            animalHD.getBukkitRunnable().cancel();
            HDMapManager.getAnimalHDMap().remove(uuid);
        }
    }

}
