package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public Config(){

    }

    public static String getUseLanguage(){
        FileConfiguration defalutConfig = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        String nowLanguage = defalutConfig.getString("Language");
        if(nowLanguage == null){
            nowLanguage = "English";
        }
        return nowLanguage;
    }

}
