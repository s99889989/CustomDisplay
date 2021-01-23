package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerReload {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerReload() {

    }

    public void reloadPlayerData(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            /**玩家資料**/
            String attackCore = cd.getConfigManager().config.getString("AttackCore");
            if(attackCore.toLowerCase().contains("customcore")){
                playerData.getBukkitRunnable().cancel();
            }
            PlayerDataMap.getPlayerDataMap().remove(playerUUID);
        }

        if(PlayerDataMap.getPlayerDataMap().get(playerUUID) == null){
            /**玩家資料**/
            PlayerDataMap.getPlayerDataMap().put(playerUUID,new PlayerData(player));
        }
    }

}
