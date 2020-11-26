package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
        ConfigMapManager.getFileConfigurationMap().put(fileMap, fileConfiguration);
        ConfigMapManager.getFileConfigurationNameMap().put(fileMap,fileMap);
        return fileConfiguration;
    }

    public void actionConfig(){
        File file = new File(cd.getDataFolder(),"Actions");
        String[] strings = file.list();
        for(String s : strings){
            if(s.contains(".yml") && !(s.contains("ExampleAction.yml"))){
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
            if(s.contains(".yml") && !(s.contains("ExampleCharacter.yml"))){
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
            cd.getLogger().info("playersConfig"+s);
            if(s.contains(".yml") && !(s.contains("Default.yml")) && !(s.contains("s99889989.yml"))){
                File finalConfigFile = new File(cd.getDataFolder(), "Players/"+s);
                FileConfiguration config = YamlConfiguration.loadConfiguration(finalConfigFile);
                ConfigMapManager.getFileConfigurationMap().put("Players_"+s, config);
                ConfigMapManager.getFileConfigurationNameMap().put("Players_"+s,"Players_"+s);
            }
        }
    }


}
