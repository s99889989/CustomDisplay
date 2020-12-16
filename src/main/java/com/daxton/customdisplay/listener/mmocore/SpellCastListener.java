package com.daxton.customdisplay.listener.mmocore;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.ListenerManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import net.Indyuce.mmocore.listener.SpellCast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpellCastListener extends SpellCast {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private static Map<UUID,Boolean> CastOnStop = new HashMap<>();

    @EventHandler
    public void stopCasting(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(ListenerManager.getCast_On_Stop().get(playerUUID) == null){
            ListenerManager.getCast_On_Stop().put(playerUUID,true);
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onskillcaststart") != null){
                    new PlayerTrigger(player).onSkillCastStart(player);
                }

            }
        }else {
            if(ListenerManager.getCast_On_Stop().get(playerUUID) == true){
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onskillcaststop") != null){
                        new PlayerTrigger(player).onSkillCastStop(player);
                    }
                }
                ListenerManager.getCast_On_Stop().put(playerUUID,false);
            }else {
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    if(new PlayerTrigger(player).getAction_Trigger_Map().get("~onskillcaststart") != null){
                        new PlayerTrigger(player).onSkillCastStart(player);
                    }
                }
                ListenerManager.getCast_On_Stop().put(playerUUID,true);
            }
        }


//        PlayerData  playerData = new PlayerData(new MMOPlayerData(player));
//        if (playerData.isOnline()) {
//            if (event.getPlayer().equals(playerData.getPlayer()) && !player.isSneaking()) {
//
//            }
//
//        }
    }

}
