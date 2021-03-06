package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
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
    //獲取三層內檔案名稱，去除.yml
    public static List<String> getFileNameList(String startPatch){
        List<String> stringList = new ArrayList<>();

        File file = new File(CustomDisplay.getCustomDisplay().getDataFolder(),startPatch);
        for(String s : file.list()){
            if(s.contains(".yml")){
                stringList.add(s.replace(".yml",""));
            }else if(!s.contains(".")){
                File file2 = new File(CustomDisplay.getCustomDisplay().getDataFolder(),startPatch+"/"+s);
                for(String s2 : file2.list()){
                    if(s2.contains(".yml")){
                        stringList.add(s2.replace(".yml",""));
                    }else if(!s2.contains(".")){
                        File file3 = new File(CustomDisplay.getCustomDisplay().getDataFolder(),startPatch+"/"+s+"/"+s2);
                        for(String s3 : file3.list()){
                            if(s3.contains(".yml")){
                                stringList.add(s3.replace(".yml",""));
                            }
                        }
                    }
                }
            }
        }

        return stringList;
    }

    //獲取文件的第一排列表
    public static List<String> configHeadKey(String typeName){
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(typeName);
        List<String> headList = new ArrayList<>(fileConfiguration.getConfigurationSection("").getKeys(false));
        return headList;
    }

    //以開頭獲取檔案列表
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

    //移除類別
    public static Map<String, FileConfiguration> getTypeConfigMap2(String typeName){
        Map<String, FileConfiguration> class_Action_Map = new HashMap<>();

        ConfigMapManager.getFileConfigurationMap().forEach((s, fileConfiguration) -> {
            if(s.startsWith(typeName)){
                class_Action_Map.put(s.replace(typeName, "").replace(".yml",""), fileConfiguration);
            }
        });

        return class_Action_Map;
    }


}
