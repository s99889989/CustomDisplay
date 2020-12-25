package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlayerConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private File file;

    FileConfiguration fileConfiguration;
    FileConfiguration inputConfig;

    private Player player;
    private String uuidString;

    public PlayerConfig(Player player){
        this.uuidString = player.getUniqueId().toString();
        this.player = player;
        createFile();
        setValue();
        saveFile();
    }


    public void setValue(){
        loadConfig();
        fileConfiguration.set(uuidString+".Name",player.getName());
        if(!(fileConfiguration.contains(uuidString+".Class"))){
            fileConfiguration.set(uuidString+".Class","Default");
        }
        if(!(fileConfiguration.contains(uuidString+".Action"))){
            fileConfiguration.set(uuidString+".Action",inputConfig.getString("Default.Action"));
        }
        if(!(fileConfiguration.contains(uuidString+".ClassName"))){
            fileConfiguration.set(uuidString+".ClassName",inputConfig.getString("Default.ClassName"));
        }
        for(String key : inputConfig.getStringList("Default.Level")){
            if(!(fileConfiguration.contains(uuidString+".Level."+key+"_level"))){
                fileConfiguration.set(uuidString+".Level."+key+"_level",0);
            }
            if(!(fileConfiguration.contains(uuidString+".Level."+key+"_exp"))){
                fileConfiguration.set(uuidString+".Level."+key+"_exp",10);
            }
        }



    }

    public void loadConfig(){
        File inputFile = new File(cd.getDataFolder(),"Class/Main/Default.yml");
        inputConfig = YamlConfiguration.loadConfiguration(inputFile);
    }

    public void createFile(){
        File dir_file = new File(cd.getDataFolder(),"Players");
        if(!dir_file.exists()){
            dir_file.mkdir();
        }
        file = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
    public void saveFile(){
        try {
            fileConfiguration.save(file);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void createFile(Player player){

        String level = uuidString+".Level.base";
        int socore;
        if(fileConfiguration.contains(level)){
            socore = fileConfiguration.getInt(level);

        }else {
            socore = 0;
            fileConfiguration.set(level,socore);
        }

        player.sendMessage("你的等級是: "+socore);

    }

}
