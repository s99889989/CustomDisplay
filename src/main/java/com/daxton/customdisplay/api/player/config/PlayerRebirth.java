package com.daxton.customdisplay.api.player.config;

import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerRebirth {



    public PlayerRebirth(){

    }


    /**轉生**/
    public static void rebirth(Player player, String className){
        String uuidString = player.getUniqueId().toString();

        //讀取玩家設定檔
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        //清空原本設定
        for(String key : playerConfig.getKeys(false)){
            playerConfig.set(key,null);
        }
        //設定玩家名稱
        playerConfig.set(uuidString+".Name",player.getName());
        //設定職業名稱
        playerConfig.set(uuidString+".Class",className);

        String fileName = "Players_"+uuidString+".yml";
        ConfigMapManager.getFileConfigurationMap().put(fileName, playerConfig);
        ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);

        PlayerManager.player_Data_Map.put(uuidString, new PlayerData2(player));

    }

}
