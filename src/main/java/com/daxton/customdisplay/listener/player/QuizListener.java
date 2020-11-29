package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
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
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            for(String string : playerData.getPlayerActionList()){
                if(string.toLowerCase().contains("~onjoin")){
                    String uuidActionString = playerUUID.toString()+new StringFind().findActionName(string);
                    if(TriggerManager.getPlayerActionTaskMap().get(uuidActionString) != null){
                        TriggerManager.getJudgmentActionTaskLoopOneMap().get(uuidActionString).cancel();
                        TriggerManager.getJudgmentActionTaskLoopOneMap().remove(uuidActionString);
                        TriggerManager.getPlayerActionTaskMap().remove(uuidActionString);
                    }
                }
            }
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);
            if(TriggerManager.getHolographicTaskMap().get(playerUUID.toString()) != null){
                TriggerManager.getHolographicTaskMap().get(playerUUID.toString()).deleteHD();
            }
        }


    }

}
