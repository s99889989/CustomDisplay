package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerReload {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerReload() {

    }

    public void reloadPlayerData(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            /**玩家資料**/
            String attackCore = cd.getConfigManager().config.getString("AttackCore");
            if(attackCore.toLowerCase().contains("customcore")){
                playerData.getBukkitRunnable().cancel();
            }
            PlayerManager.getPlayerDataMap().remove(playerUUID);
        }

        if(PlayerManager.getPlayerDataMap().get(playerUUID) == null){
            /**玩家資料**/
            PlayerManager.getPlayerDataMap().put(playerUUID,new PlayerData(player));
        }
    }

}
