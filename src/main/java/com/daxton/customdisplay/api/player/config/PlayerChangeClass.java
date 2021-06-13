package com.daxton.customdisplay.api.player.config;

import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerChangeClass {



    public PlayerChangeClass(){

    }

    /**轉職**/
    public static void changeClass(Player player, String className){

        String uuidString = player.getUniqueId().toString();

        //讀取玩家設定檔
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        //讀取Class設定檔
        FileConfiguration classConfig= LoadConfig.getClassConfig(className);

        //設定玩家名稱
        playerConfig.set(uuidString+".Name",player.getName());
        //設定職業名稱
        playerConfig.set(uuidString+".Class",className);
        //設定職業顯示名稱
        if(classConfig.contains(className+".Class_Name")){
            playerConfig.set(uuidString+".Class_Name", classConfig.getString(uuidString+".Class_Name"));
        }else {
            playerConfig.set(uuidString+".Class_Name", "null");
        }

        //設定動作列表
        List<String> actionList = classConfig.getStringList(className+".Action");
        playerConfig.set(uuidString+".Action", actionList);
        //設定人物屬性
        List<String> attrStatsList = classConfig.getStringList(className+".Attributes_Stats");
        playerConfig.set(uuidString+".Attributes_Stats", attrStatsList);
        //設定裝備屬性
        List<String> attrEqmList = classConfig.getStringList(className+".Equipment_Stats");
        playerConfig.set(uuidString+".Equipment_Stats", attrEqmList);

        String fileName = "Players_"+uuidString+".yml";
        ConfigMapManager.getFileConfigurationMap().put(fileName, playerConfig);
        ConfigMapManager.getFileConfigurationNameMap().put(fileName, fileName);

        PlayerManager.player_Data_Map.put(uuidString, new PlayerData2(player));


//        CustomDisplay cd = CustomDisplay.getCustomDisplay();
//        /**清除標記動作**/
//        PlayerData playerData = PlayerManager.getPlayerDataMap().get(player.getUniqueId());
//        playerData.taskIDList.forEach((s, s2) -> {
//            new ClearAction().taskID(s2);
//        });
//
//        /**讀取玩家設定檔**/
//        FileConfiguration playerConfig = new LoadConfig().getPlayerConfig(player);
//        /**讀取Class設定檔**/
//        FileConfiguration classConfig= new LoadConfig().getClassConfig(className);
//        String uuidString = player.getUniqueId().toString();
//        /**設定玩家名稱**/
//        playerConfig.set(uuidString+".Name",player.getName());
//        /**設定職業名稱**/
//        playerConfig.set(uuidString+".Class",className);
//
//        /**設定動作列表**/
//        List<String> actionList = classConfig.getStringList(className+".Action");
//        playerConfig.set(uuidString+".Action", actionList);
//
//        /**設定顯示用職業名稱**/
//        playerConfig.set(uuidString+".Class_Name", classConfig.getString(className+".Class_Name"));
//
//        /**設定等級設定**/
//        List<String> classLevelList = classConfig.getStringList(className+".Level");
//        if(classLevelList.size() > 0){
//            for(String key : classLevelList){
//                File levelFile = new File(cd.getDataFolder(),"Class/Level/"+key+".yml");
//                FileConfiguration levelConfig = YamlConfiguration.loadConfiguration(levelFile);
//                ConfigurationSection levelSec = levelConfig.getConfigurationSection("Exp-Amount");
//                List<String> levelList = null;
//                String maxLevelString = "";
//                int mineLevel = 0;
//                int maxLevel = 0;
//                try {
//                    levelList = new ArrayList<>(levelSec.getKeys(false));
//                    maxLevelString = levelList.get(levelList.size()-1);
//                    maxLevel = Integer.valueOf(maxLevelString);
//                    String minLevelString = levelList.get(0);
//                    mineLevel = Integer.valueOf(minLevelString);
//                }catch (NullPointerException exception){
//
//                }
//                int maxExp = levelConfig.getInt("Exp-Amount.1");
//                if(!(playerConfig.contains(uuidString+".Level."+key+"_level_now"))){
//                    playerConfig.set(uuidString+".Level."+key+"_level_now",mineLevel);
//                }
//                playerConfig.set(uuidString+".Level."+key+"_level_max",maxLevel);
//                if(!(playerConfig.contains(uuidString+".Level."+key+"_exp_now"))){
//                    playerConfig.set(uuidString+".Level."+key+"_exp_now",0);
//                }
//                playerConfig.set(uuidString+".Level."+key+"_exp_max",maxExp);
//
//            }
//        }
//
//
//        /**設定點數設定**/
//
//        List<String> classPointList = classConfig.getStringList(className+".Point");
//        if(classPointList.size() > 0){
//            for(String point : classPointList){
//                if(!(playerConfig.contains(uuidString+".Point."+point+"_last"))){
//                    playerConfig.set(uuidString+".Point."+point+"_last",0);
//                }
//                if(!(playerConfig.contains(uuidString+".Point."+point+"_max"))){
//                    playerConfig.set(uuidString+".Point."+point+"_max",0);
//                }
//
//            }
//        }
//
//
//        /**設定屬性點數**/
//
//        for(String attr : classConfig.getStringList(className+".Attributes_Point")){
//            FileConfiguration attrPointConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Point_"+attr+".yml");
//            if(attrPointConfig != null){
//                ConfigurationSection attrPointList = attrPointConfig.getConfigurationSection(attr);
//                for(String sttrPoint : attrPointList.getKeys(false)){
//                    int base = attrPointConfig.getInt(attr+"."+sttrPoint+".base");
//                    if(!(playerConfig.contains(uuidString+".Attributes_Point."+sttrPoint))){
//                        playerConfig.set(uuidString+".Attributes_Point."+sttrPoint,base);
//                    }
//                }
//            }
//        }
//
//        /**設定人物屬性**/
//        List<String> attrStatsList = classConfig.getStringList(className+".Attributes_Stats");
//        playerConfig.set(uuidString+".Attributes_Stats", attrStatsList);
//
//
//        /**設定裝備屬性**/
//        List<String> equipmentStatsList = classConfig.getStringList(className+".Equipment_Stats");
//        playerConfig.set(uuidString+".Equipment_Stats", equipmentStatsList);
//
//
//        /**設定技能**/
//
//        List<String> skillPatchList = classConfig.getStringList(className+".Skills");
//        skillPatchList.forEach((fileName)->{
//            File skillFile = new File(cd.getDataFolder(),"Class/Skill/"+fileName+".yml");
//            FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
//            ConfigurationSection skillSec = skillConfig.getConfigurationSection(fileName);
//            skillSec.getKeys(false).forEach((key)->{
//                if(!(playerConfig.contains(uuidString+".Skills."+key+".level"))){
//                    playerConfig.set(uuidString+".Skills."+key+".level", 0);
//                }
//                if(!(playerConfig.contains(uuidString+".Skills."+key+".use"))){
//                    playerConfig.set(uuidString+".Skills."+key+".use", 0);
//                }
//            });
//
//        });
//
//
//        /**設定綁定技能**/
//        if(!(playerConfig.contains(uuidString+".Binds"))){
//            for(int i = 1 ; i < 9 ; i++){
//                playerConfig.set(uuidString+".Binds."+i+".SkillName", "null");
//                playerConfig.set(uuidString+".Binds."+i+".UseLevel", 0);
//            }
//        }
//
//        new SaveConfig().savePlayerConfig(player,playerConfig);
//
//        PlayerReload.reloadPlayerData(player);



    }


}
