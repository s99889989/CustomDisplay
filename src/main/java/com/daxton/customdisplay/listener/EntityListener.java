package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.list.SendBossBar;
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

        if(TriggerManager.getHolographicTaskMap().get(uuid.toString()) != null){
            TriggerManager.getHolographicTaskMap().get(uuid.toString()).deleteHD();
        }

        UUID targetUUID = BBDMapManager.getTargetAttackBossBarMap().get(uuid);
        if (!(uuid.equals(targetUUID))) {
        if (targetUUID != null) {
            SendBossBar sendBossBar = BBDMapManager.getAttackBossBarMap().get(targetUUID);
            Player player = sendBossBar.getPlayer();
            sendBossBar.getBossBar().removePlayer(player);
            sendBossBar.getBukkitRunnable().cancel();
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
