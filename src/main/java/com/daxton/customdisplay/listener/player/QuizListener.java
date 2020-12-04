package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.manager.ActionManager;
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
                if(string.toLowerCase().contains("~onquiz")){
                    new JudgmentAction().execute(player,string,String.valueOf((int)(Math.random()*100000)));
                }
            }
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);
            if(ActionManager.getJudgment_Holographic_Map().get(playerUUID.toString()) != null){
                ActionManager.getJudgment_Holographic_Map().get(playerUUID.toString()).deleteHD();
            }
        }


    }

}
