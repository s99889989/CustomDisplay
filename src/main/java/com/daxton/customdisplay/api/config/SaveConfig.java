package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.gui.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SaveConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SaveConfig(){

    }

    /**儲存玩家設定檔**/
    public static void savePlayerConfig(Player player){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String uuidString = player.getUniqueId().toString();
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            playerConfig.save(playerFile);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        String fileName = "Players_"+uuidString+".yml";
        ConfigMapManager.getFileConfigurationMap().put(fileName, playerConfig);
        ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);
    }
    //儲存物品資訊
    public static void saveItemFile(){
        String[] strings = MenuSet.getItemMenuButtomNameArray();
        if(strings != null){
            for(String name : strings){
                FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ name +".yml");
                File itemPatch = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Items/item/"+ name +".yml");
                try {
                    itemConfig.save(itemPatch);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }

    }
    //讀取物品設定
    public static void loadItemFile(){
        String[] strings = MenuSet.getItemMenuButtomNameArray();
        if(strings != null){
            for(String name : strings){
                File itemPatch = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Items/item/"+ name +".yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemPatch);
                String patchName = "Items_item_"+ name +".yml";
                ConfigMapManager.getFileConfigurationMap().put(patchName, itemConfig);
            }
        }
    }



}
