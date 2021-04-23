package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<FileConfiguration> getTypeConfigList(String typeName){
        List<FileConfiguration> configTypeList = new ArrayList<>();
        ConfigMapManager.getFileConfigurationMap().forEach((s, fileConfiguration) -> {
            if(s.contains(typeName)){
                //CustomDisplay.getCustomDisplay().getLogger().info(s);
                configTypeList.add(fileConfiguration);
            }
        });

        return configTypeList;
    }

    public static Map<String, FileConfiguration> getTypeConfigMap(String typeName){
        Map<String, FileConfiguration> class_Action_Map = new HashMap<>();

        ConfigMapManager.getFileConfigurationMap().forEach((s, fileConfiguration) -> {
            if(s.startsWith(typeName)){
                class_Action_Map.put(s, fileConfiguration);
            }
        });

        return class_Action_Map;
    }


}
