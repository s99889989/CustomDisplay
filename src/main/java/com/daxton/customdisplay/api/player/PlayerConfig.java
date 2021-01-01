package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.Arithmetic;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    private String className = "Novice";

    public PlayerConfig(Player player){
        this.uuidString = player.getUniqueId().toString();
        this.player = player;

        //new PlayerEquipment(player);

    }


    public void setDefaultValue(){
        className = "Novice";
        if(playerConfig.contains(uuidString+".Class")){
            className = playerConfig.getString(uuidString+".Class");
            loadNowConfig(className);
        }else {
            className = "Novice";
            loadNowConfig(className);
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
        if(!(playerConfig.contains(uuidString+".Level"))){
            List<String> classLevelList = classConfig.getStringList(className+".Level");
            if(classLevelList.size() > 0){
                for(String key : classLevelList){
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


                    playerConfig.set(uuidString+".Level."+key+"_now_level",1);
                    playerConfig.set(uuidString+".Level."+key+"_max_level",maxLevel);
                    playerConfig.set(uuidString+".Level."+key+"_now_exp",0);
                    playerConfig.set(uuidString+".Level."+key+"_max_exp",maxExp);

                }
            }
        }

        if(!(playerConfig.contains(uuidString+".Point"))){
            List<String> classPointList = classConfig.getStringList(className+".Point");
            if(classPointList.size() > 0){
                for(String point : classPointList){
                    playerConfig.set(uuidString+".Point."+point+"_last",0);
                    playerConfig.set(uuidString+".Point."+point+"_max",0);
                }
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
        if(!(playerConfig.contains(uuidString+".AttributesStats"))){
            List<String> attrStatsList = classConfig.getStringList(className+".AttributesStats");
            playerConfig.set(uuidString+".AttributesStats", attrStatsList);
        }
        if(!(playerConfig.contains(uuidString+".EquipmentStats"))){
            List<String> attrStatsList = classConfig.getStringList(className+".EquipmentStats");
            playerConfig.set(uuidString+".EquipmentStats", attrStatsList);
        }

        if(!(playerConfig.contains(uuidString+".PhysicalAttackFormula"))){
            playerConfig.set(uuidString+".PhysicalAttackFormula", classConfig.getString(className+".PhysicalAttackFormula"));
        }

        saveCreateFile(player,playerConfig);

        File attrFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/attributes-stats.yml");
        try {
            if(!attrFilePatch.exists()){
                attrFilePatch.createNewFile();
                setNewAttrStatsConfig(attrFilePatch);
            }
        }catch (Exception exception){
            //exception.printStackTrace();
        }
        if(attrFilePatch.exists()){
            setAttrStats(player);
        }


        File eqmFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
        try {
            if(!eqmFilePatch.exists()){
                eqmFilePatch.createNewFile();
                setNewEqmStatsConfig(eqmFilePatch);
            }
        }catch (Exception exception){
            //exception.printStackTrace();
        }


    }

    public void setNewEqmStatsConfig(File patch){
        FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(patch);
        List<String> attrStatsNameList = playerConfig.getStringList(uuidString+".EquipmentStats");
        for(String attrStatsFileName : attrStatsNameList){
            FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Stats_"+attrStatsFileName+".yml");
            ConfigurationSection attrStatsSec = attrStatsConfig.getConfigurationSection(attrStatsFileName);
            if(attrStatsSec.getKeys(false).size() > 0){
                for(String attrStats : attrStatsSec.getKeys(false)){
                    eqmConfig.set(uuidString+".EquipmentStats."+attrStats,0);

                }
            }

        }
        try {
            eqmConfig.save(patch);
        }catch (Exception exception){
            //exception.printStackTrace();
        }
    }

    public void setNewAttrStatsConfig(File patch){
        FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(patch);
        List<String> attrStatsNameList = playerConfig.getStringList(uuidString+".AttributesStats");
        for(String attrStatsFileName : attrStatsNameList){
            FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Stats_"+attrStatsFileName+".yml");
            ConfigurationSection attrStatsSec = attrStatsConfig.getConfigurationSection(attrStatsFileName);
            if(attrStatsSec.getKeys(false).size() > 0){
                for(String attrStats : attrStatsSec.getKeys(false)){
                    attrConfig.set(uuidString+".AttributesStats."+attrStats,0);

                }
            }

        }
        try {
            attrConfig.save(patch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void setAttrStats(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        List<String> attrStatsNameList = playerConfig.getStringList(playerUUIDString+".AttributesStats");
        if(attrStatsNameList.size() > 0){
            for(String attrStatsFileName : attrStatsNameList){

                File attrFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/attributes-stats.yml");
                FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFilePatch);

                FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Stats_"+attrStatsFileName+".yml");

                ConfigurationSection attrStatsSec = attrStatsConfig.getConfigurationSection(attrStatsFileName);
                if(attrStatsSec.getKeys(false).size() > 0){
                    for(String attrStats : attrStatsSec.getKeys(false)){
                        String statsNumberString = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".formula");

                        if(statsNumberString != null){
                            statsNumberString = new StringConversion2(player,null,statsNumberString,"Character").valueConv();
                        }else {
                            statsNumberString = "0";
                        }


                        int statsNumber = 0;
                        try {
                            double number = Arithmetic.eval(statsNumberString);
                            String numberDec = new NumberUtil(number,"#").getDecimalString();
                            statsNumber = Integer.valueOf(numberDec);
                        }catch (Exception exception){
                            statsNumber = 0;
                        }
                        //cd.getLogger().info("加總: "+statsNumberString);

                        attrConfig.set(playerUUIDString+".AttributesStats."+attrStats,statsNumber);


                        String inherit = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".inherit");
                        String operation = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".operation");
                        if(inherit != null && operation !=null){
                            new PlayerAttribute().addAttribute(player,inherit,operation,statsNumber,attrStats);
                        }

                    }
                }
                try {
                    attrConfig.save(attrFilePatch);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }

    }



    public void loadNowConfig(String MainName){
        File defaultFilePatch = new File(cd.getDataFolder(),"Class/Main/"+MainName+".yml");
        classConfig = YamlConfiguration.loadConfiguration(defaultFilePatch);
    }

    public void createFile(){
        /****/
        File dir_file = new File(cd.getDataFolder(),"Players/"+uuidString);
        if(!dir_file.exists()){
            dir_file.mkdir();
        }


        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        try {
            if(!playerFilePatch.exists()){
                playerFilePatch.createNewFile();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        setDefaultValue();

    }
    /**存檔**/
    public void saveCreateFile(Player player,FileConfiguration config){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");

        try {
            config.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /**存檔**/
    public void saveFile(FileConfiguration fileConfiguration){
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");

        try {
            fileConfiguration.save(playerFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


}
