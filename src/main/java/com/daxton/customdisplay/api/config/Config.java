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

    //獲取目前使用語言
    public static String getUseLanguage(){
        FileConfiguration defalutConfig = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        String nowLanguage = defalutConfig.getString("Language");
        if(nowLanguage == null){
            nowLanguage = "English";
        }
        return nowLanguage;
    }

    //獲取資料夾內.yml檔案名稱清單
    public static List<String> getFileList(String patch){
        List<String> fileList = new ArrayList<>();
        ConfigMapManager.getFileConfigurationMap().forEach((s, fileConfiguration) -> {
            if(s.startsWith(patch) && s.contains(".yml")){
                String fileName = s.replace(patch,"").replace(".yml","");
                fileList.add(fileName);
            }
        });
        return fileList;
    }

    //獲取文件的第一排列表
    public static List<String> configHeadKey(String typeName){
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(typeName);
        List<String> headList = new ArrayList<>(fileConfiguration.getConfigurationSection("").getKeys(false));
        return headList;
    }

    public static List<FileConfiguration> getTypeConfigList(String typeName){
        List<FileConfiguration> configTypeList = new ArrayList<>();
        ConfigMapManager.getFileConfigurationMap().forEach((s, fileConfiguration) -> {
            if(s.startsWith(typeName)){
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
