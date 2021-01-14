package com.daxton.customdisplay.api.character.placeholder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

public class PlaceholderClass {

    public PlaceholderClass(){

    }



    public String valueOf(LivingEntity entity, String inputString){
        String outputString = "";






        return outputString;
    }

    /**裝備屬性**/
    public String setEqmStats(FileConfiguration eqmConfig, String key, String uuidString){
        String playerAnser = "0";
        String key2 = key.replace("eqm_stats_","");
        if(eqmConfig.getString(uuidString+".Equipment_Stats."+key2) != null){
            playerAnser = eqmConfig.getString(uuidString+".Equipment_Stats."+key2);
        }
        return playerAnser;
    }
    /**素質屬性**/
    public String setAttrStats(FileConfiguration attrConfig,String key,String uuidString){
        String playerAnser = "0";
        String key2 = key.replace("attr_stats_","");
        if(attrConfig.getString(uuidString+".Attributes_Stats."+key2) != null){
            playerAnser = attrConfig.getString(uuidString+".Attributes_Stats."+key2);
        }
        return playerAnser;
    }
    /****/
    public String setClass(FileConfiguration playerConfig,String key,String uuidString){
        String playerAnser = "0";
        if(key.toLowerCase().contains("name")){
            if(playerConfig.getString(uuidString+".Class_Name") != null){
                playerAnser = playerConfig.getString(uuidString+".Class_Name");
            }
        }
        if(key.toLowerCase().contains("level_now_")){
            String key2 = key.replace("level_now_","");
            int level_now = playerConfig.getInt(uuidString+".Level."+key2+"_now_level");
            playerAnser = String.valueOf(level_now);
        }
        if(key.toLowerCase().contains("level_max_")){
            String key2 = key.replace("level_max_","");
            int level_max = playerConfig.getInt(uuidString+".Level."+key2+"_max_level");
            playerAnser = String.valueOf(level_max);
        }
        if(key.toLowerCase().contains("exp_now_")){
            String key2 = key.replace("exp_now_","");
            int exp_now = playerConfig.getInt(uuidString+".Level."+key2+"_now_exp");
            playerAnser = String.valueOf(exp_now);
        }
        if(key.toLowerCase().contains("exp_max_")){
            String key2 = key.replace("exp_max_","");
            int exp_max = playerConfig.getInt(uuidString+".Level."+key2+"_max_exp");
            playerAnser = String.valueOf(exp_max);
        }
        if(key.toLowerCase().contains("point_last_")){
            String key2 = key.replace("point_last_","");
            int point_last = playerConfig.getInt(uuidString+".Point."+key2+"_last");
            playerAnser = String.valueOf(point_last);
        }
        if(key.toLowerCase().contains("point_max_")){
            String key2 = key.replace("point_max_","");
            int point_max = playerConfig.getInt(uuidString+".Point."+key2+"_max");
            playerAnser = String.valueOf(point_max);
        }
        if(key.toLowerCase().contains("attr_point_")){
            String key2 = key.replace("attr_point_","");
            int attr_point = playerConfig.getInt(uuidString+".Attributes_Point."+key2);
            playerAnser = String.valueOf(attr_point);
        }
        if(key.toLowerCase().contains("skill_level_")){
            String key2 = key.replace("skill_level_","");
            int attr_point = playerConfig.getInt(uuidString+".Skills."+key2+".level");
            playerAnser = String.valueOf(attr_point);
        }
        if(key.toLowerCase().contains("skill_use_")){
            String key2 = key.replace("skill_use_","");
            int attr_point = playerConfig.getInt(uuidString+".Skills."+key2+".use");
            playerAnser = String.valueOf(attr_point);
        }
        return playerAnser;
    }

}
