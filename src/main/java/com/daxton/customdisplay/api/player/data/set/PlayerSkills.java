package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerSkills {



    public PlayerSkills(){

    }

    /**把技能存在Map**/
    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();
        try {
            List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Skills").getKeys(false));
            if(!(attrStatsList.isEmpty()) && attrStatsList.size() > 0){
                for(String attrName : attrStatsList){
                    int level = playerConfig.getInt(uuidString+".Skills."+attrName+".level");
                    int use = playerConfig.getInt(uuidString+".Skills."+attrName+".use");
                    point_Map.put(attrName+"_level",String.valueOf(level));
                    point_Map.put(attrName+"_use",String.valueOf(use));

                }
            }
        }catch (Exception exception){

        }

    }

    /**設定單個技能等級**/
    public void setOneMap(Player player, String skillName, int amount){

        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(uuid);
        if(playerData != null){
            Map<String,String> skills_Map = playerData.skills_Map;
            Map<String,List<CustomLineConfig>> action_Trigger_Map2 = playerData.getAction_Trigger_Map2();
            if(!(skills_Map.isEmpty()) && skills_Map.size() > 0){
                try {
                    int nowValue = Integer.valueOf(skills_Map.get(skillName));
                    int newValue = nowValue + amount;
                    if(newValue >= 0){
                        skills_Map.put(skillName,String.valueOf(newValue));
                        setAction(player, action_Trigger_Map2, skillName, newValue);
                    }
                }catch (NumberFormatException exception){

                }


            }

        }


    }

    public void setAction(Player player, Map<String,List<CustomLineConfig>> action_Trigger_Map2, String skillName, int skillLevel){
        if(skillName.contains("_level")){
            String skillKey = skillName.replace("_level","");
            FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillKey+".yml");
            boolean passiveSkill = skillConfig.getBoolean(skillKey+".PassiveSkill");

            if(passiveSkill && skillLevel > 0){
                //CustomDisplay.getCustomDisplay().getLogger().info("技能: "+skillName+" : "+skillLevel+" : "+passiveSkill);
                List<String> skillList = skillConfig.getStringList(skillKey+".Action");
                new PlayerAction2(action_Trigger_Map2).setPlayerAction(skillList);
                skillList.forEach(s1 -> {
                    if(s1.toLowerCase().contains("~onjoin")){
                        new PlayerTrigger(player).onSkill2(player,null,new CustomLineConfig(s1));
                    }
                    //CustomDisplay.getCustomDisplay().getLogger().info(s1);
                });
            }

        }
    }



}
