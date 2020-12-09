package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.listener.SpellCast;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
        if(CastOnStop.get(playerUUID) == null){
            CastOnStop.put(playerUUID,true);
            if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
                playerData.runAction("~onskillcaststart",event);
            }
        }else {
            if(CastOnStop.get(playerUUID) == true){
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
                    playerData.runAction("~onskillcaststop",event);
                }
                CastOnStop.put(playerUUID,false);
            }else {
                if(PlayerDataMap.getPlayerDataMap().get(playerUUID) != null){
                    PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
                    playerData.runAction("~onskillcaststart",event);
                }
                CastOnStop.put(playerUUID,true);
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
