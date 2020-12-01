package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.action.JudgmentAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private Player player;

    private UUID playerUUID;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        PlayerData playerDate = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerDate == null){
            PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));
        }

        /**觸發OnTime**/
        onAtcion();

    }

    /**觸發OnTime**/
    public void onAtcion(){

        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            for(String string : playerData.getPlayerActionList()){
                if(string.toLowerCase().contains("~onjoin")){
                    new JudgmentAction().execute(player,string,String.valueOf((int)(Math.random()*100000)));
                }
            }
        }
    }


}
