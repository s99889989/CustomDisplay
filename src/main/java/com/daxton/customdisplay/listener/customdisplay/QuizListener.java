package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ListenerManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuizListener implements Listener {

    private Player player;

    private LivingEntity target = null;

    private UUID playerUUID;

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        /**刪除玩家資料物件  和   刪除OnTime物件**/
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            new PlayerTrigger(player).onQuit(player,target);
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);

        }
        if(ListenerManager.getCast_On_Stop().get(playerUUID) != null){
            ListenerManager.getCast_On_Stop().put(playerUUID,false);
        }

    }

}
