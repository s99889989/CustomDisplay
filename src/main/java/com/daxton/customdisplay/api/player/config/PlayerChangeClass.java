package com.daxton.customdisplay.api.player.config;

import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerChangeClass {



    public PlayerChangeClass(){

    }

    /**轉職**/
    public static void changeClass(Player player, String className){

        String uuidString = player.getUniqueId().toString();

        //讀取玩家設定檔
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        //讀取Class設定檔
        FileConfiguration classConfig= LoadConfig.getClassConfig(className);

        //設定玩家名稱
        playerConfig.set(uuidString+".Name",player.getName());
        //設定職業名稱
        playerConfig.set(uuidString+".Class",className);
        //設定職業顯示名稱
        if(classConfig.contains(className+".Class_Name")){
            playerConfig.set(uuidString+".Class_Name", classConfig.getString(uuidString+".Class_Name"));
        }else {
            playerConfig.set(uuidString+".Class_Name", "null");
        }

        //設定動作列表
        List<String> actionList = classConfig.getStringList(className+".Action");
        playerConfig.set(uuidString+".Action", actionList);
        //設定人物屬性
        List<String> attrStatsList = classConfig.getStringList(className+".Attributes_Stats");
        playerConfig.set(uuidString+".Attributes_Stats", attrStatsList);
        //設定裝備屬性
        List<String> attrEqmList = classConfig.getStringList(className+".Equipment_Stats");
        playerConfig.set(uuidString+".Equipment_Stats", attrEqmList);

        String fileName = "Players_"+uuidString+".yml";
        ConfigMapManager.getFileConfigurationMap().put(fileName, playerConfig);
        ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);

        PlayerManager.player_Data_Map.put(uuidString, new PlayerData2(player));

    }


}
