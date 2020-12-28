package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerConfig {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    FileConfiguration playerConfig;
    FileConfiguration classConfig;

    private Player player;
    private String uuidString;

    public PlayerConfig(Player player){
        this.uuidString = player.getUniqueId().toString();
        this.player = player;

    }


    public void setDefaultValue(){
        if(playerConfig.contains(uuidString+".Class")){
            String className = playerConfig.getString(uuidString+".Class");
            loadNowConfig(className);
        }else {
            loadDefaultConfig();
        }

        playerConfig.set(uuidString+".Name",player.getName());

        if(!(playerConfig.contains(uuidString+".Class"))){
            playerConfig.set(uuidString+".Class","Default");
        }

        if(!(playerConfig.contains(uuidString+".Action"))){
            List<String> actionList = classConfig.getStringList("Default.Action");
            playerConfig.set(uuidString+".Action", actionList);

        }

        if(!(playerConfig.contains(uuidString+".ClassName"))){
            playerConfig.set(uuidString+".ClassName", classConfig.getString("Default.ClassName"));
        }
        String levelString = playerConfig.getString(uuidString+".Class");
        for(String key : classConfig.getStringList(levelString+".Level")){
            File levelFile = new File(cd.getDataFolder(),"Class/Level/"+key+".yml");
            FileConfiguration levelConfig = YamlConfiguration.loadConfiguration(levelFile);
            ConfigurationSection levelSec = levelConfig.getConfigurationSection("Exp-Amount");
            List<String> levelList = null;
            String maxLevelString = "";
            int maxLevel = 0;
            try {
                levelList = new ArrayList<>(levelSec.getKeys(false));
                maxLevelString = levelList.get(levelList.size()-1);
                maxLevel = Integer.valueOf(maxLevelString);
            }catch (NullPointerException exception){

            }

            int maxExp = levelConfig.getInt("Exp-Amount.1");
            if(!(playerConfig.contains(uuidString+".Level."+key+"_now_level"))){
                playerConfig.set(uuidString+".Level."+key+"_now_level",1);
            }
            if(!(playerConfig.contains(uuidString+".Level."+key+"_max_level"))){
                playerConfig.set(uuidString+".Level."+key+"_max_level",maxLevel);
            }
            if(!(playerConfig.contains(uuidString+".Level."+key+"_now_exp"))){
                playerConfig.set(uuidString+".Level."+key+"_now_exp",0);
            }
            if(!(playerConfig.contains(uuidString+".Level."+key+"_max_exp"))){
                playerConfig.set(uuidString+".Level."+key+"_max_exp",maxExp);
            }
        }


        String pointString = playerConfig.getString(uuidString+".Class");
        for(String key : classConfig.getStringList(pointString+".Point")){
            if(!(playerConfig.contains(uuidString+".Point."+key+"_max"))){
                playerConfig.set(uuidString+".Point."+key+"_max",0);
            }
            if(!(playerConfig.contains(uuidString+".Point."+key+"_last"))){
                playerConfig.set(uuidString+".Point."+key+"_last",0);
            }
        }
        if(!(playerConfig.contains(uuidString+".Attributes"))){
            String className = playerConfig.getString(uuidString+".Class");
            for(String attr : classConfig.getStringList(className+".Attributes")){
                playerConfig.set(uuidString+".Attributes."+attr,0);
            }
        }




    }

    public void loadDefaultConfig(){
        File defaultFilePatch = new File(cd.getDataFolder(),"Class/Main/Default.yml");
        classConfig = YamlConfiguration.loadConfiguration(defaultFilePatch);
    }

    public void loadNowConfig(String MainName){
        File defaultFilePatch = new File(cd.getDataFolder(),"Class/Main/"+MainName+".yml");
        classConfig = YamlConfiguration.loadConfiguration(defaultFilePatch);
    }

    public void createFile(){
        File dir_file = new File(cd.getDataFolder(),"Players");
        if(!dir_file.exists()){
            dir_file.mkdir();
        }
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            if(!playerFilePatch.exists()){
                playerFilePatch.createNewFile();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        setDefaultValue();
        saveFile();
    }
    /**存檔**/
    public void saveFile(){
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");

        //playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        try {
            playerConfig.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void createFile(Player player){

        String level = uuidString+".Level.base";
        int socore;
        if(playerConfig.contains(level)){
            socore = playerConfig.getInt(level);

        }else {
            socore = 0;
            playerConfig.set(level,socore);
        }

        player.sendMessage("你的等級是: "+socore);

    }

}
