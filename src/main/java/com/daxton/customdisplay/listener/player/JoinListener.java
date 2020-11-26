package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
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
        //onAtcion();

    }

    /**觸發OnTime**/
    public void onAtcion(){
        PlayerData playerDate = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerDate != null){
            List<String> stringList = new ArrayList<>();
            int i = 0;
            for(String string : playerDate.getPlayerActionList()){
                i++;
                if(string.contains("~onTimer=")){
                    String nameString = player.getName()+i;
                    stringList.add(nameString);
                    TriggerManager.getOnTimerMap().put(nameString,new OnTimer(player,string));

                }
            }
            TriggerManager.getOnTimerNameMap().put(playerUUID,stringList);
        }
    }


}
