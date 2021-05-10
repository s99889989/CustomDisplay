package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.AutoConfig;
import com.daxton.customdisplay.api.config.AutoConfig2;
import com.daxton.customdisplay.api.item.gui.MenuSet;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;

    public FileConfiguration language;


    public ConfigManager(CustomDisplay plugin){
        cd = plugin;

//        MythicLineConfig mythicLineConfig = null;
//        mythicLineConfig.getString(new String[]{"auranme"},null, new String[0]);


        config = new AutoConfig("resource/config.yml","config.yml").get();

        //language = new AutoConfig("resource/Language/"+config.getString("Language")+".yml","Language/"+config.getString("Language")+".yml").get();

        File players_file = new File(cd.getDataFolder(),"Players");
        if(!players_file.exists()){
            players_file.mkdir();
        }
        File mobs_file = new File(cd.getDataFolder(),"Mobs");
        if(!mobs_file.exists()){
            mobs_file.mkdir();
        }
        File png_file = new File(cd.getDataFolder(),"Png");
        if(!png_file.exists()){
            png_file.mkdir();
        }
        new AutoConfig2();

        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String[] strings = MenuSet.getItemMenuButtomNameArray();
        if(strings != null){
            for(String name : strings){
                String patchString = "Items/item/"+ name +".yml";
                String patchString2 = patchString.replace("/","_");
                File itemPatch = new File(CustomDisplay.getCustomDisplay().getDataFolder(), patchString);
                if(!itemPatch.exists()){
                    try {
                        itemPatch.createNewFile();
                    }catch (Exception exception){

                    }
                }
                FileConfiguration saveFileConfig3 = YamlConfiguration.loadConfiguration(itemPatch);
                ConfigMapManager.getFileConfigurationMap().put(patchString2, saveFileConfig3);
                ConfigMapManager.getFileConfigurationNameMap().put(patchString2,patchString2);
            }
        }


        //ConfigMapManager.getFileConfigurationNameMap().forEach((s, s2) -> cd.getLogger().info(s));

    }



}
