package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.*;
import com.daxton.customdisplay.manager.player.PlayerActionMap;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.actionbardisplay.PlayerActionBar;
import com.daxton.customdisplay.task.bossbardisplay.AttackBossBar;
import com.daxton.customdisplay.task.holographicdisplays.PlayerHD;
import com.daxton.customdisplay.task.player.ActionDisplay;
import com.daxton.customdisplay.task.player.OnTimer;
import com.daxton.customdisplay.task.titledisply.JoinTitle;
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
        for(String string : playerDataUse.getPlayerActionList()){
            if(string.contains("onTimer:")){
                OnTimer onTimer = TriggerManager.getOnTimerMap().get(uuid);
                if(onTimer == null){
                    TriggerManager.getOnTimerMap().put(uuid,new OnTimer(player,string));
                }
            }
        }

        //new ActionControl(player);


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

        /**移除OnTimer**/
        OnTimer onTimer = TriggerManager.getOnTimerMap().get(uuid);
        if(onTimer != null){
            onTimer.getBukkitRunnable().cancel();
            TriggerManager.getOnTimerMap().remove(uuid);
        }

        /**移除玩家資料**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            PlayerDataMap.getPlayerDataMap().remove(uuid);
        }

        ActionDisplay actionDisplay = PlayerActionMap.getActionDisplayMap().get(uuid);
        if(actionDisplay != null){
            PlayerActionMap.getActionDisplayMap().remove(uuid);
        }

        AttackBossBar attackBossBar = BBDMapManager.getAttackBossBarMap().get(uuid);
        if(attackBossBar != null){
            BBDMapManager.getAttackBossBarMap().remove(uuid);
        }

        JoinTitle joinTitle = TDMapManager.getJoinTitleMap().get(uuid);
        if(joinTitle != null){
            joinTitle.getBukkitRunnable().cancel();
            TDMapManager.getJoinTitleMap().remove(uuid);
        }
        PlayerHD playerHD = HDMapManager.getPlayerHDMap().get(uuid);
        if(playerHD != null){
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
