package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ABDMapManager;
import com.daxton.customdisplay.manager.BBDMapManager;
import com.daxton.customdisplay.manager.HDMapManager;
import com.daxton.customdisplay.manager.TDMapManager;
import com.daxton.customdisplay.task.actionbardisplay.PlayerActionBar;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import com.daxton.customdisplay.task.titledisply.JoinTitle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        UUID uuid = e.getPlayer().getUniqueId();
        Player player = e.getPlayer();



        JoinTitle joinTitle = TDMapManager.getJoinTitleMap().get(uuid);
        if(cd.getConfigManager().config_title_display && joinTitle == null) {
            TDMapManager.getJoinTitleMap().put(uuid,new JoinTitle(player));
        }

        PlayerActionBar playerActionBar = ABDMapManager.getPlayerActionBarHashMap().get(uuid);
        if(cd.getConfigManager().config_action_bar_display && playerActionBar == null){
            ABDMapManager.getPlayerActionBarHashMap().put(uuid,new PlayerActionBar(player));
        }

        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(cd.getConfigManager().config_entity_top_display && cd.getConfigManager().player_top_display_enable && playerHD == null){
            HDMapManager.getPlayerHDMap().put(uuid,new PlayerHD(player));
        }

    }

    @EventHandler
    public void onQuiz(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();

        AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(uuid);
        if(attackBossBar != null){
            BBDMapManager.getAttackBossBarMap().remove(uuid);
        }

        JoinTitle joinTitle = TDMapManager.getJoinTitleMap().get(uuid);
        if(joinTitle != null){
            joinTitle.getBukkitRunnable().cancel();
//            joinTitle.getBukkitRunnable1().cancel();
//            joinTitle.getBukkitRunnable2().cancel();
            TDMapManager.getJoinTitleMap().remove(uuid);
        }
        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(playerHD != null){
            cd.getLogger().info("玩家離開刪除HD");
            playerHD.getHologram().delete();
            playerHD.getBukkitRunnable().cancel();
            HDMapManager.getPlayerHDMap().remove(uuid);
        }
        PlayerActionBar playerActionBar = ABDMapManager.getPlayerActionBarHashMap().get(uuid);
        if(playerActionBar != null){
            playerActionBar.getBukkitRunnable().cancel();
            ABDMapManager.getPlayerActionBarHashMap().remove(uuid);
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
