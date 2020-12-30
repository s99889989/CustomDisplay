package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.Arithmetic;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerConfig2 {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    FileConfiguration playerConfig;
    FileConfiguration classConfig;

    private Player player;
    private String uuidString;

    private String className = "Novice";

    public PlayerConfig2(Player player){
        this.uuidString = player.getUniqueId().toString();
        this.player = player;

        new PlayerEquipment(player);

    }


    public void setDefaultValue(){
        className = "Novice";
        if(playerConfig.contains(uuidString+".Class")){
            className = playerConfig.getString(uuidString+".Class");
            loadNowConfig(className);
        }else {
            className = "Novice";
            loadDefaultConfig();
        }

        playerConfig.set(uuidString+".Name",player.getName());

        if(!(playerConfig.contains(uuidString+".Class"))){
            playerConfig.set(uuidString+".Class",className);
        }

        if(!(playerConfig.contains(uuidString+".Action"))){
            List<String> actionList = classConfig.getStringList(className+".Action");
            playerConfig.set(uuidString+".Action", actionList);

        }

        if(!(playerConfig.contains(uuidString+".ClassName"))){
            playerConfig.set(uuidString+".ClassName", classConfig.getString(className+".ClassName"));
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


        for(String key : classConfig.getStringList(className+".Point")){
            if(!(playerConfig.contains(uuidString+".Point."+key+"_max"))){
                playerConfig.set(uuidString+".Point."+key+"_max",0);
            }
            if(!(playerConfig.contains(uuidString+".Point."+key+"_last"))){
                playerConfig.set(uuidString+".Point."+key+"_last",0);
            }
        }

        if(!(playerConfig.contains(uuidString+".AttributesPoint"))){
            for(String attr : classConfig.getStringList(className+".AttributesPoint")){
                FileConfiguration attrPointConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Point_"+attr+".yml");

                ConfigurationSection attrPointList = attrPointConfig.getConfigurationSection(attr);

                try {
                    for(String sttrPoint : attrPointList.getKeys(false)){
                        int base = attrPointConfig.getInt(attr+"."+sttrPoint+".base");
                        playerConfig.set(uuidString+".AttributesPoint."+sttrPoint,base);
                    }
                }catch (Exception exception){

                }

            }
        }
        for(String attr : classConfig.getStringList(className+".AttributesStats")){
            FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Stats_"+attr+".yml");
            ConfigurationSection attrStatsList = attrStatsConfig.getConfigurationSection(attr);
            try {
                for(String sttrStats : attrStatsList.getKeys(false)){
                    int base = attrStatsConfig.getInt(attr+"."+sttrStats+".base");
                    if(!(playerConfig.contains(uuidString+".AttributesStats."+sttrStats))){
                        playerConfig.set(uuidString+".AttributesStats."+sttrStats,base);
                    }

                }
            }catch (Exception exception){

            }

        }
        setAttrStats();
        for(String eqs : classConfig.getStringList(className+".EquipmentStats")){
            FileConfiguration eqStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Equipment_"+eqs+".yml");
            ConfigurationSection eqStatsList = eqStatsConfig.getConfigurationSection(eqs);
            if(eqStatsList.getKeys(false).size() > 0){
                for(String eqStats : eqStatsList.getKeys(false)){
                    int base = eqStatsConfig.getInt(eqs+"."+eqStats+".base");
                    if(!(playerConfig.contains(uuidString+".EquipmentStats."+eqStats))){
                        playerConfig.set(uuidString+".EquipmentStats."+eqStats,base);
                    }

                }


            }

        }

    }

    public void setAttrStats(){
        ConfigurationSection attrStatsList = playerConfig.getConfigurationSection(uuidString+".AttributesStats");
        FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Stats_"+className+".yml");
        if(attrStatsList.getKeys(false).size() > 0){
            for(String attrStats : attrStatsList.getKeys(false)){
                String formula = attrStatsConfig.getString(className+"."+attrStats+".formula");
                if(formula == null){
                    formula = "100";
                }
                String formula2 = new StringConversion2(player,null,formula,"Character").valueConv();
                int inumber = 0;
                try {
                    double number = Arithmetic.eval(formula2);
                    String numberDec = new NumberUtil(number,"#").getDecimalString();
                    inumber = Integer.valueOf(numberDec);
                }catch (Exception exception){
                    inumber = 0;
                }


                String inherit = attrStatsConfig.getString(className+"."+attrStats+".inherit");
                String operation = attrStatsConfig.getString(className+"."+attrStats+".operation");
                if(inherit != null && operation !=null){
                    new PlayerAttribute().addAttribute(player,inherit,operation,inumber,attrStats);
                }
                playerConfig.set(uuidString+".AttributesStats."+attrStats,inumber);


            }


        }


    }




    public void loadDefaultConfig(){
        File defaultFilePatch = new File(cd.getDataFolder(),"Class/Main/Novice.yml");
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
        saveCreateFile();
    }
    /**存檔**/
    public void saveCreateFile(){
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");

        //playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);

        try {
            playerConfig.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /**存檔**/
    public void saveFile(FileConfiguration fileConfiguration){
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");

        try {
            fileConfiguration.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


}
