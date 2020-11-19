package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.*;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

        /**一照讀取到的設定檔建立玩家資料**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData == null){
            PlayerDataMap.getPlayerDataMap().put(uuid,new PlayerData(player));
        }




        /**增加OnTimer**/
        PlayerData playerDataUse = PlayerDataMap.getPlayerDataMap().get(uuid);
        int i = 0;
        String playerName = player.getName();
        if(playerDataUse != null){
            for(String string : playerDataUse.getPlayerActionList()){
                i++;
                if(string.contains("onTimer:")){
                    OnTimer onTimer = TriggerManager.getOnTimerMap().get(uuid);
                    if(onTimer == null){
                        playerName = playerName + i;
                        TriggerManager.getOnTimerMap().put(playerName,new OnTimer(player,string));
                    }
                }
            }
        }



        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(cd.getConfigManager().config_entity_top_display && cd.getConfigManager().player_top_display_enable && playerHD == null){
            HDMapManager.getPlayerHDMap().put(uuid,new PlayerHD(player));
        }

    }

    /**玩家被攻擊的判斷**/
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        UUID uuid = ((Player) event.getEntity()).getPlayer().getUniqueId();
        Player player = ((Player) event.getEntity()).getPlayer();



    }




    @EventHandler
    public void onQuiz(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        Player player = e.getPlayer();
        String playerName = player.getName();
        /**移除OnTimer**/
        PlayerData playerDataUse = PlayerDataMap.getPlayerDataMap().get(uuid);
        int i = 0;
        if(playerDataUse != null){
            for(String string : playerDataUse.getPlayerActionList()) {
                i++;
                playerName = playerName + i;
                OnTimer onTimer = TriggerManager.getOnTimerMap().get(playerName);
                if (onTimer != null) {
                    onTimer.getBukkitRunnable().cancel();
                    TriggerManager.getOnTimerMap().remove(playerName);
                }
            }
        }


        /**移除玩家資料**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            PlayerDataMap.getPlayerDataMap().remove(uuid);
        }

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
