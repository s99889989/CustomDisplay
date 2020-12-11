package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PermissionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Locale;

public class AutoConfig {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private File file;

    private File saveFile;

    private FileConfiguration fileConfiguration;

    private String saveName;

    public AutoConfig(){

    }

    public AutoConfig(String fileName,String saveName){
        this.saveName = saveName;
        file = new File(cd.getDataFolder(),fileName);
        saveFile = new File(cd.getDataFolder(), saveName);
        if(!saveFile.exists()){
            cd.saveResource(fileName,saveName,false);
        }
    }

    public FileConfiguration get(){
        fileConfiguration = YamlConfiguration.loadConfiguration(saveFile);
        String fileMap = saveName.replace("/", "_");
        String permissionMap = saveName.toLowerCase().replace("/", ".").replace(".yml", "");
        ConfigMapManager.getFileConfigurationMap().put(fileMap, fileConfiguration);
        ConfigMapManager.getFileConfigurationNameMap().put(fileMap,fileMap);

        if(permissionMap.contains("permission")){
            PermissionManager.getPermission_String_Map().put("customdisplay."+permissionMap,"customdisplay."+permissionMap);
            PermissionManager.getPermission_FileConfiguration_Map().put("customdisplay."+permissionMap,fileConfiguration);
        }

        return fileConfiguration;
    }


    public void actionConfig(){
        File file = new File(cd.getDataFolder(),"Actions");
        String[] strings = file.list();
        for(String s : strings){
            if(s.contains(".yml")){
                File finalConfigFile = new File(cd.getDataFolder(), "Actions/"+s);
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                ConfigMapManager.getFileConfigurationMap().put("Actions_"+s, config);
                ConfigMapManager.getFileConfigurationNameMap().put("Actions_"+s,"Actions_"+s);
            }
        }
    }

    public void characterConfig(){

        File file = new File(cd.getDataFolder(),"Character");
        String[] strings = file.list();
        for(String s : strings){
            if(s.contains(".yml")){
                File finalConfigFile = new File(cd.getDataFolder(), "Character/"+s);
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                ConfigMapManager.getFileConfigurationMap().put("Character_"+s, config);
                ConfigMapManager.getFileConfigurationNameMap().put("Character_"+s,"Character_"+s);
            }
        }
    }

    public void playersConfig(){
        File file = new File(cd.getDataFolder(),"Players");
        String[] strings = file.list();
        for(String s : strings){
            if(s.contains(".yml")){
                File finalConfigFile = new File(cd.getDataFolder(), "Players/"+s);
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                ConfigMapManager.getFileConfigurationMap().put("Players_"+s, config);
                ConfigMapManager.getFileConfigurationNameMap().put("Players_"+s,"Players_"+s);
            }
        }
    }

    public void permissionConfig(){

        File file = new File(cd.getDataFolder(),"Permission");
        String[] strings = file.list();
        for(String s : strings){
            if(s.contains(".yml")){
                File finalConfigFile = new File(cd.getDataFolder(), "Permission/"+s);
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                String permissionMap = s.toLowerCase().replace(".yml","");
                PermissionManager.getPermission_String_Map().put("customdisplay.permission."+permissionMap,"customdisplay.permission."+permissionMap);
                PermissionManager.getPermission_FileConfiguration_Map().put("customdisplay.permission."+permissionMap,config);
            }
        }
    }

}
