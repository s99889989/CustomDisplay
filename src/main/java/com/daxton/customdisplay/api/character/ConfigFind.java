package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigFind {

    private List<String> configFirstKey = new ArrayList<>();

    private FileConfiguration configFile;

    public ConfigFind(){

    }

    /**丟入資料夾名稱 和 第一排關鍵字    返回關鍵字底下的字串陣列**/
    public List<String> getCharacterMessageList(String folderName, String searchKey){
        List<String> stringList = new ArrayList<>();
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(configName.contains(folderName)){

                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(configName);
                if(fileConfiguration.getKeys(false).contains(searchKey)){
                    stringList = fileConfiguration.getStringList(searchKey+".message");
                }

            }
        }
        return stringList;
    }

}
