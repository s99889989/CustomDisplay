package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SaveConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SaveConfig(){

    }

    /**儲存玩家設定檔**/
    public void savePlayerConfig(Player player, FileConfiguration config){
        String uuidString = player.getUniqueId().toString();
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            config.save(playerFile);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

}
