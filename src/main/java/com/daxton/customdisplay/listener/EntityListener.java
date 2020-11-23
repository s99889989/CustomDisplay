package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.UUID;

public class EntityListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        UUID uuid = e.getEntity().getUniqueId();


        UUID targetUUID = BBDMapManager.getTargetAttackBossBarMap().get(uuid);
        if (!(uuid.equals(targetUUID))) {
        if (targetUUID != null) {
            AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(targetUUID);
            Player player = attackBossBar.getPlayer();
            attackBossBar.getBossBar().removePlayer(player);
            attackBossBar.getBukkitRunnable().cancel();
            BBDMapManager.getAttackBossBarMap().remove(targetUUID);
            BBDMapManager.getTargetAttackBossBarMap().remove(uuid);

            }
        }


    }


    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event){
        final Entity entity = event.getEntity();


    }

}
