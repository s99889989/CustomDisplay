package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    /**根據動作字串 返回動作列表**/
    public List<String> getActionList(String firstString){

        List<String> list = new ArrayList<>();
        List<String> actionList = new ArrayList<>();
        String key = "";
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while (stringTokenizer.hasMoreElements()){
            list.add(stringTokenizer.nextToken());
        }

        for(String string : list){
            if(string.toLowerCase().contains("a=")){
                String[] strings1 = string.split("=");
                key = strings1[1];
            }

        }

        for(String string1 : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(string1.contains("Actions_")){
                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(string1);
                if(fileConfiguration.getKeys(false).contains(key)){
                    actionList = fileConfiguration.getStringList(key+".Action");
                }
            }
        }

        return actionList;

    }

    /**根據動作名稱 返回動作列表**/
    public List<String> getActionKeyList(String firstString){
        List<String> actionList = new ArrayList<>();
        for(String string1 : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(string1.contains("Actions_")){
                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(string1);
                if(fileConfiguration.getKeys(false).contains(firstString)){
                    actionList = fileConfiguration.getStringList(firstString+".Action");
                }
            }
        }

        return actionList;

    }

}
