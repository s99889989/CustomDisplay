package com.daxton.customdisplay.api.player.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerConfig2 {



    public PlayerConfig2(){

    }

    public PlayerConfig2(Player player){

        createFirstConfig(player);
        setPlayerConfig(player);

    }

    public static void setConfig(Player player){
        //如果沒有找到設定檔，就創一個空白新的
        createFirstConfig(player);
        setPlayerConfig(player);

    }


    /**設定玩家設定檔**/
    public static void setPlayerConfig(Player player){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String uuidString = player.getUniqueId().toString();
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        //讀取Class設定檔
        String className = "Default_Player";
        if(playerConfig.contains(uuidString+".Class")){
            className = playerConfig.getString(uuidString+".Class");
            //player.sendMessage("使用職業: "+className);
        }
        FileConfiguration classConfig= LoadConfig.getClassConfig(className);

        //設定玩家名稱
        playerConfig.set(uuidString+".Name",player.getName());
        //設定職業名稱
        if(!(playerConfig.contains(uuidString+".Class"))){
            playerConfig.set(uuidString+".Class",className);
        }
        //設定動作列表
        if(!(playerConfig.contains(uuidString+".Action"))){
            List<String> actionList = classConfig.getStringList(className+".Action");
            playerConfig.set(uuidString+".Action", actionList);
        }
        //設定顯示用職業名稱*
        if(!(playerConfig.contains(uuidString+".Class_Name"))){
            playerConfig.set(uuidString+".Class_Name", classConfig.getString(className+".Class_Name"));
        }
        //設定等級設定
        List<String> classLevelList = classConfig.getStringList(className+".Level");
        if(classLevelList.size() > 0){
            for(String key : classLevelList){
                //player.sendMessage(""+key);
                FileConfiguration levelConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Level_"+key+".yml");
                ConfigurationSection levelSec = levelConfig.getConfigurationSection("Exp-Amount");
                List<String> levelList = null;
                String maxLevelString = "";
                int mineLevel = 0;
                int maxLevel = 0;
                try {
                    levelList = new ArrayList<>(levelSec.getKeys(false));
                    maxLevelString = levelList.get(levelList.size()-1);
                    maxLevel = Integer.valueOf(maxLevelString);
                    String minLevelString = levelList.get(0);
                    mineLevel = Integer.valueOf(minLevelString);
                }catch (NullPointerException exception){

                }
                int maxExp = levelConfig.getInt("Exp-Amount.1");
                if(!playerConfig.contains(uuidString+".Level."+key+"_exp_max")){
                    playerConfig.set(uuidString+".Level."+key+"_level_now",mineLevel);
                }

                if(!playerConfig.contains(uuidString+".Level."+key+"_level_max")){
                    playerConfig.set(uuidString+".Level."+key+"_level_max",maxLevel);
                }

                if(!playerConfig.contains(uuidString+".Level."+key+"_exp_now")){
                    playerConfig.set(uuidString+".Level."+key+"_exp_now",0);
                }

                if(!playerConfig.contains(uuidString+".Level."+key+"_exp_max")){
                    playerConfig.set(uuidString+".Level."+key+"_exp_max",maxExp);
                }


            }
        }
        //設定點數設定
        List<String> classPointList = classConfig.getStringList(className+".Point");
        if(classPointList.size() > 0){
            for(String point : classPointList){
                if(!playerConfig.contains(uuidString+".Point."+point+"_last")){
                    playerConfig.set(uuidString+".Point."+point+"_last",0);
                }
                if(!playerConfig.contains(uuidString+".Point."+point+"_max")){
                    playerConfig.set(uuidString+".Point."+point+"_max",0);
                }
            }
        }
        //設定點數屬性
        for(String attr : classConfig.getStringList(className+".Attributes_Point")){
            FileConfiguration attrPointConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Point_"+attr+".yml");
            ConfigurationSection attrPointList = attrPointConfig.getConfigurationSection(attr);
            try {
                for(String sttrPoint : attrPointList.getKeys(false)){
                    int base = attrPointConfig.getInt(attr+"."+sttrPoint+".base");
                    if(!playerConfig.contains(uuidString+".Attributes_Point."+sttrPoint)){
                        playerConfig.set(uuidString+".Attributes_Point."+sttrPoint,base);
                    }
                }
            }catch (Exception exception){

            }

        }
        //設定人物屬性
        if(!(playerConfig.contains(uuidString+".Attributes_Stats"))){
            List<String> attrStatsList = classConfig.getStringList(className+".Attributes_Stats");
            playerConfig.set(uuidString+".Attributes_Stats", attrStatsList);
        }
        //設定裝備屬性
        if(!(playerConfig.contains(uuidString+".Equipment_Stats"))){
            List<String> attrStatsList = classConfig.getStringList(className+".Equipment_Stats");
            playerConfig.set(uuidString+".Equipment_Stats", attrStatsList);
        }
        //設定技能
        List<String> skillPatchList = classConfig.getStringList(className+".Skills");
        skillPatchList.forEach((fileName)->{
            FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+fileName+".yml");
            ConfigurationSection skillSec = skillConfig.getConfigurationSection(fileName);
            skillSec.getKeys(false).forEach((key)->{
                //player.sendMessage(key);
                if(!playerConfig.contains(uuidString+".Skills."+key+".level")){
                    playerConfig.set(uuidString+".Skills."+key+".level", 0);
                }
                if(!playerConfig.contains(uuidString+".Skills."+key+".use")){
                    playerConfig.set(uuidString+".Skills."+key+".use", 0);
                }
            });

        });
        //設定綁定技能
        if(!(playerConfig.contains(uuidString+".Binds"))){
            for(int i = 1 ; i < 9 ; i++){
                playerConfig.set(uuidString+".Binds."+i+".SkillName", "null");
                playerConfig.set(uuidString+".Binds."+i+".UseLevel", 0);
            }
        }



        //儲存玩家設定檔
        SaveConfig.savePlayerConfig(player);


    }


    //如果沒有找到設定檔，就創一個空白新的
    public static void createFirstConfig(Player player){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String uuidString = player.getUniqueId().toString();
        File playerFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        try {
            if(!playerFile.exists()){
                playerFile.createNewFile();

                String fileName = "Players_"+uuidString+".yml";
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(playerFile);
                ConfigMapManager.getFileConfigurationMap().put(fileName, fileConfiguration);
                ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);

            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
