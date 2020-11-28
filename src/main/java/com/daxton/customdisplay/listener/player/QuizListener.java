package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuizListener implements Listener {

    private Player player;

    private UUID playerUUID;

    @EventHandler
    public void onQuiz(PlayerQuitEvent event){
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        /**刪除玩家資料物件  和   刪除OnTime物件**/
        PlayerData playerDate = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerDate != null){
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);
//            for(String string : TriggerManager.getOnTimerNameMap().get(playerUUID)){
//                TriggerManager.getOnTimerMap().get(string).getBukkitRunnable().cancel();
//                TriggerManager.getOnTimerMap().remove(string);
//            }
            if(TriggerManager.getHolographicTaskMap().get(playerUUID.toString()) != null){
                TriggerManager.getHolographicTaskMap().get(playerUUID.toString()).deleteHD();
            }

        }






    }

}
