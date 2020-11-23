package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.*;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static Map<UUID, TriggerManager> triggerManagerMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        UUID uuid = e.getPlayer().getUniqueId();
        Player player = e.getPlayer();

        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(cd.getConfigManager().config_entity_top_display && cd.getConfigManager().player_top_display_enable && playerHD == null){
            HDMapManager.getPlayerHDMap().put(uuid,new PlayerHD(player));
        }

    }



    @EventHandler
    public void onQuiz(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        Player player = e.getPlayer();
        String playerName = player.getName();

        AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(uuid);
        if(attackBossBar != null){
            BBDMapManager.getAttackBossBarMap().remove(uuid);
        }

        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(playerHD != null){
            playerHD.getHologram().delete();
            playerHD.getBukkitRunnable().cancel();
            HDMapManager.getPlayerHDMap().remove(uuid);
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        Location location = e.getPlayer().getLocation();
        double height = e.getPlayer().getHeight();
        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(playerHD != null){
            playerHD.getHologram().teleport(location.add(0,height+ cd.getConfigManager().player_top_display_hight,0));
        }
    }




    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        UUID uuid = e.getEntity().getUniqueId();

        AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(uuid);
        if(attackBossBar != null){
            BBDMapManager.getAttackBossBarMap().remove(uuid);
        }

        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(playerHD != null){
            playerHD.getHologram().delete();
            playerHD.getBukkitRunnable().cancel();
            HDMapManager.getPlayerHDMap().remove(uuid);
        }
    }

}
